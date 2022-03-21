package com.example.happybirthday

import android.app.Application
import com.example.happybirthday.database.AppDatabase

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
