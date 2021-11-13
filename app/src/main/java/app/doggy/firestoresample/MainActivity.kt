package app.doggy.firestoresample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import app.doggy.firestoresample.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val db = Firebase.firestore

        val taskAdapter = TaskAdapter()
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        db.collection("tasks")
            .get()
            .addOnSuccessListener { value ->
                val tasks = ArrayList<Task>()
                for (doc in value) {
                    val task = doc.toObject<Task>()
                    tasks.add(Task(
                        id = task.id,
                        title = task.title,
                        date = task.date,
                    ))
                }
                Log.d(READ_TAG, tasks.toString())
                taskAdapter.submitList(tasks)
            }
            .addOnFailureListener { exception ->
                Log.d(READ_TAG, "Error getting documents: ", exception)
            }

        val docRef = db.collection("tasks")
        docRef.addSnapshotListener { value, e ->
            val tasks = ArrayList<Task>()

            if (e != null) {
                Log.w(READ_TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (value != null) {
                for (doc in value) {
                    val task = doc.toObject<Task>()
                    tasks.add(Task(
                        id = task.id,
                        title = task.title,
                        date = task.date,
                    ))
                }
                Log.d(READ_TAG, tasks.toString())
                taskAdapter.submitList(tasks)
            } else {
                Log.d(READ_TAG, "Current data: null")
            }
        }

        binding.button.setOnClickListener {
            // Create a new task with a first and last name
            val task = Task(
                title = binding.titleEditText.text.toString(),
                date = binding.dateEditText.text.toString(),
            )

            // Add a new document with a generated ID
            db.collection("tasks")
                .add(task)
                .addOnSuccessListener { documentReference ->
                    Log.d(ADD_TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(ADD_TAG, "Error adding document", e)
                }
        }
    }

    companion object {
        private const val ADD_TAG = "add_task"
        private const val READ_TAG = "read_task"
    }
}