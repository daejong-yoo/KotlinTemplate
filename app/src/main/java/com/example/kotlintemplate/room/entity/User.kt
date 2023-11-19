package com.example.kotlintemplate.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var name: String,
    var age: String,
    var phone: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}