package com.example.kotlintemplate.room.dao

import androidx.room.*
import com.example.kotlintemplate.room.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

//    @Query("DELETE FROM User WHERE name = name")
//    fun deleteUserByName(name: String)

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}