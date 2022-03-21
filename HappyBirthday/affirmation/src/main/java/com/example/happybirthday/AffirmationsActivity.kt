package com.example.happybirthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happybirthday.databinding.ActivityAffirmationsBinding

class AffirmationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAffirmationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffirmationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSet = DataSource().loadAffirmations()
        val recyclerView = binding.affirmationsRecyclerView
        recyclerView.adapter = ItemAdapter(this, dataSet)
        recyclerView.setHasFixedSize(true)
    }
}
