package com.example.programingapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Quote constructor(@PrimaryKey val id: String) {

    var author: String? = null
    var en: String? = null
    var rating: Double = 0.toDouble()
    var numberOfVotes: Int = 0

    constructor(author: String, en: String, rating: Double, id: String, numberOfVotes: Int) : this(id) {
        this.author = author
        this.en = en
        this.rating = rating
        this.numberOfVotes = numberOfVotes
    }
}
