package app.doggy.firestoresample

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val date: String = "2022/01/01",
)