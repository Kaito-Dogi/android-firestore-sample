package app.doggy.firestoresample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.doggy.firestoresample.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val db = Firebase.firestore

        binding.button.setOnClickListener {
            // Create a new user with a first and last name
            val user = hashMapOf(
                "nickname" to binding.nicknameEditText.text.toString(),
                "course" to binding.courseEditText.text.toString(),
            )

            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(ADD_TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(ADD_TAG, "Error adding document", e)
                }

            val docRef = db.collection("users")
            docRef.addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(READ_TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val users = ArrayList<String>()
                    for (doc in value) {
                        doc.getString("nickname")?.let {
                            users.add(it)
                        }
                    }
                    binding.textView.text = users.toString()
                } else {
                    Log.d(READ_TAG, "Current data: null")
                }
            }
        }
    }

    companion object {
        private const val ADD_TAG = "add_user"
        private const val READ_TAG = "read_user"
    }
}