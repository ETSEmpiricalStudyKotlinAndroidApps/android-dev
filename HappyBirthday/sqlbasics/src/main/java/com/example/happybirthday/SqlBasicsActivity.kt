package com.example.happybirthday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SqlBasicsActivity : AppCompatActivity(R.layout.activity_sql_basics) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(applicationContext)
                .californiaParkDao().getAll()
        }
    }
}
