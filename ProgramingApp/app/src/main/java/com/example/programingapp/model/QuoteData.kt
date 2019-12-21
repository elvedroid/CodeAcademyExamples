package com.example.programingapp.model

import androidx.room.PrimaryKey

data class QuoteData(@PrimaryKey val id: String, val quote: String)