package app.doggy.firestoresample

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val date: String = "2022/01/01",
)