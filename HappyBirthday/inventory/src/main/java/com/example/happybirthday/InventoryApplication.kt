package com.example.happybirthday

import android.app.Application
import com.example.happybirthday.data.ItemRoomDatabase

class InventoryApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}
