package com.example.kotlintemplate.ui.activity

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseActivity
import com.example.kotlintemplate.common.OnSingleClickListener
import com.example.kotlintemplate.databinding.ActivityRoomBinding
import com.example.kotlintemplate.room.database.UserDatabase
import com.example.kotlintemplate.room.entity.User
import com.example.kotlintemplate.util.GLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomActivity : BaseActivity<ActivityRoomBinding>(R.layout.activity_room) {
    private val mClickListener = OnSingleClickListener {
        when (it) {
            binding.btnInsert -> {
                GLog.i("call :: btnTest1")

                val newUser1 = User("김고승", "20", "010-1234-5678")
//                UserDatabase.getInstance(mActivity).userDao().insert(newUser1)
                CoroutineScope(Dispatchers.IO).launch {
                    UserDatabase.getInstance(mActivity).userDao().insert(newUser1)
                }
            }

            binding.btnGetAll -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val users = UserDatabase.getInstance(mActivity).userDao().getAll()
                    GLog.d("users : ${GLog.toJson(users)}")
                }
            }
        }
    }

    override fun init() {
        binding.clickL = mClickListener
    }
}