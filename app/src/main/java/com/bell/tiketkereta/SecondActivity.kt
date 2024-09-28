package com.bell.tiketkereta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var destinationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)  // Ganti dengan layout file Anda

        nameTextView = findViewById(R.id.name_02)
        timeTextView = findViewById(R.id.time_2)
        dateTextView = findViewById(R.id.date_2)
        destinationTextView = findViewById(R.id.destination_2)

        // Mendapatkan data dari Intent
        val intent = intent
        val name = intent.getStringExtra("name") ?: "Nama tidak tersedia"
        val time = intent.getStringExtra("time") ?: "Jam tidak tersedia"
        val date = intent.getStringExtra("date") ?: "Tanggal tidak tersedia"
        val destination = intent.getStringExtra("destination") ?: "Tujuan tidak tersedia"

        // Mengatur data ke TextView
        nameTextView.text = name
        timeTextView.text = time
        dateTextView.text = date
        destinationTextView.text = destination
    }
}
