package com.app.compare_my_trade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Teams_and_conditions : AppCompatActivity() {

    lateinit var back:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_and_conditions)


        back = findViewById(R.id.back)

        back.setOnClickListener {
            super.onBackPressed()
        }

    }
}