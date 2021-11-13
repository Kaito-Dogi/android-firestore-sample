package app.doggy.firestoresample

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Task(
    @DocumentId
    val id: String = "",
    val title: String = "",
    var createdAt: Date = Date(System.currentTimeMillis()),
)