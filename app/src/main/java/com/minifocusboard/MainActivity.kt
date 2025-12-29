package com.minifocusboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.minifocusboard.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

data class Task(
    val label: String,
    var done: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class QuoteResponse(
    val content: String,
    val author: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()
    private val gson = Gson()
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
    
    private var currentCall: okhttp3.Call? = null
    
    private val prefs by lazy {
        getSharedPreferences("MiniFocusBoard", MODE_PRIVATE)
    }
    
    companion object {
        private const val PREFS_KEY_TASKS = "tasks"
        private const val PREFS_KEY_QUOTE_TEXT = "quote_text"
        private const val PREFS_KEY_QUOTE_AUTHOR = "quote_author"
        private const val QUOTE_API_URL = "https://api.quotable.io/random"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadTasks()
        loadSavedQuote()
        setupListeners()
        
        // Fetch quote on startup
        fetchQuote()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            tasks,
            onToggleDone = { position ->
                // Toggle task done status
                tasks[position].done = !tasks[position].done
                saveTasks()
                taskAdapter.notifyItemChanged(position)
            },
            onDelete = { position ->
                // Delete task
                tasks.removeAt(position)
                saveTasks()
                taskAdapter.notifyItemRemoved(position)
                updateTaskCount()
            }
        )
        binding.rvTaskList.layoutManager = LinearLayoutManager(this)
        binding.rvTaskList.adapter = taskAdapter
    }

    private fun setupListeners() {
        // Add task button
        binding.btnAddTask.setOnClickListener {
            addTask()
        }

        // Enter key in input field
        binding.etTaskInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTask()
                true
            } else {
                false
            }
        }

        // Enable/disable add button based on input
        binding.etTaskInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.btnAddTask.isEnabled = !s.isNullOrBlank()
            }
        })

        // Refresh quote button
        binding.btnRefreshQuote.setOnClickListener {
            fetchQuote()
        }
    }

    private fun addTask() {
        val text = binding.etTaskInput.text?.toString()?.trim()
        if (text.isNullOrBlank()) {
            return
        }

        val newTask = Task(label = text)
        tasks.add(0, newTask)
        saveTasks()
        taskAdapter.notifyItemInserted(0)
        binding.rvTaskList.smoothScrollToPosition(0)
        binding.etTaskInput.text?.clear()
        updateTaskCount()
        
        // Hide keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(binding.etTaskInput.windowToken, 0)
    }

    private fun updateTaskCount() {
        binding.chipTaskCount.text = tasks.size.toString()
    }

    private fun loadTasks() {
        val tasksJson = prefs.getString(PREFS_KEY_TASKS, null)
        if (tasksJson != null) {
            try {
                val type = object : TypeToken<List<Task>>() {}.type
                val loadedTasks = gson.fromJson<List<Task>>(tasksJson, type)
                tasks.clear()
                tasks.addAll(loadedTasks)
                taskAdapter.notifyDataSetChanged()
                updateTaskCount()
            } catch (e: Exception) {
                // Handle error silently
            }
        }
    }

    private fun saveTasks() {
        val tasksJson = gson.toJson(tasks)
        prefs.edit().putString(PREFS_KEY_TASKS, tasksJson).apply()
    }

    private fun loadSavedQuote() {
        val savedText = prefs.getString(PREFS_KEY_QUOTE_TEXT, null)
        val savedAuthor = prefs.getString(PREFS_KEY_QUOTE_AUTHOR, null)
        if (savedText != null) {
            binding.tvQuoteText.text = savedText
            binding.tvQuoteAuthor.text = if (savedAuthor.isNullOrBlank()) "" else "— $savedAuthor"
        }
    }

    private fun saveQuote(text: String, author: String) {
        prefs.edit()
            .putString(PREFS_KEY_QUOTE_TEXT, text)
            .putString(PREFS_KEY_QUOTE_AUTHOR, author)
            .apply()
    }

    private fun fetchQuote() {
        binding.tvQuoteStatus.text = getString(R.string.quote_fetching)
        binding.btnRefreshQuote.isEnabled = false

        lifecycleScope.launch {
            try {
                val quote = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url(QUOTE_API_URL)
                        .get()
                        .build()

                    val call = okHttpClient.newCall(request)
                    currentCall = call
                    val response = call.execute()
                    currentCall = null
                    
                    if (!response.isSuccessful) {
                        throw IOException("HTTP ${response.code}")
                    }

                    val json = response.body?.string()
                    gson.fromJson(json, QuoteResponse::class.java)
                }

                // Only update UI if Activity is still active
                if (!isDestroyed && !isFinishing) {
                    binding.tvQuoteText.text = quote.content
                    binding.tvQuoteAuthor.text = if (quote.author.isBlank()) "" else "— ${quote.author}"
                    binding.tvQuoteStatus.text = getString(R.string.quote_updated)
                    saveQuote(quote.content, quote.author)
                }

            } catch (e: Exception) {
                if (!isDestroyed && !isFinishing) {
                    binding.tvQuoteStatus.text = getString(R.string.quote_error)
                    // Keep showing the last saved quote
                    loadSavedQuote()
                }
            } finally {
                if (!isDestroyed && !isFinishing) {
                    binding.btnRefreshQuote.isEnabled = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel ongoing network request to prevent memory leaks
        currentCall?.cancel()
        currentCall = null
    }
}

