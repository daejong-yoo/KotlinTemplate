package com.example.kotlintemplate.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlintemplate.room.dao.UserDao
import com.example.kotlintemplate.room.entity.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(context, UserDatabase::class.java, "user-database").build()
                }
            }

            return instance!!
        }
//        fun getInstance(context: Context) : UserDatabase{
//            return instance ?: synchronized(this) {
//                instance ?: UserDatabase().also { instance = it }
//            }
//        }
    }
}