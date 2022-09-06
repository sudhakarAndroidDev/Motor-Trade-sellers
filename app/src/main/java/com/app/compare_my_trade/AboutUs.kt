package com.app.compare_my_trade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AboutUs : AppCompatActivity() {

    lateinit var back:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        back = findViewById(R.id.back)

        back.setOnClickListener {
            super.onBackPressed()
        }
    }
}