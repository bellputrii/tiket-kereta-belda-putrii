package com.bell.tiketkereta

import android.os.Bundle
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.icu.util.Calendar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var spinnerTujuan: Spinner
    private lateinit var btnPesan: Button
    private lateinit var editTextName: EditText

    private lateinit var selectedDate: String  // Menambahkan variabel untuk tanggal
    private lateinit var selectedTime: String  // Menambahkan variabel untuk waktu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi tombol dan spinner
        btnDate = findViewById(R.id.date_to_go)
        btnTime = findViewById(R.id.time_to_go)
        spinnerTujuan = findViewById(R.id.spinner_tujuan)
        btnPesan = findViewById(R.id.btn_pesan)
        editTextName = findViewById(R.id.txt_2)

        // DatePicker
        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear" // Simpan tanggal yang dipilih
                    btnDate.text = selectedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // TimePicker
        btnTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute) // Simpan waktu yang dipilih
                    btnTime.text = selectedTime
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }

        // Inisialisasi Spinner untuk tujuan
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.daftar_tujuan,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTujuan.adapter = adapter

        // Listener untuk Spinner
        spinnerTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val tujuan = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, "Tujuan: $tujuan", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnPesan.setOnClickListener {
            val selectedTujuan = spinnerTujuan.selectedItem.toString()
            val selectedName = editTextName.text.toString()

            // Misalnya harga tiket tergantung tujuan yang dipilih
            val hargaTiket = when (selectedTujuan) {
                "Jakarta" -> 150000
                "Bandung" -> 100000
                "Surabaya" -> 200000
                "Yogyakarta" -> 180000
                "Medan" -> 250000
                "Bali" -> 500000
                else -> 0
            }

            // Membuat dialog untuk menampilkan total harga tiket
            val dialogView = layoutInflater.inflate(R.layout.activity_dialog, null)
            val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
            dialogMessage.text = "Tarif Tiket\nRp $hargaTiket"

            // Buat AlertDialog
            val alertDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            // Tampilkan dialog
            alertDialog.show()

            dialogView.findViewById<Button>(R.id.button_beli).setOnClickListener {
                // Logika untuk melanjutkan pemesanan
                val intent = Intent(this, SecondActivity::class.java)

                // Mengirim data tambahan
                intent.putExtra("name", selectedName)
                intent.putExtra("date", selectedDate)  // Mengirim tanggal
                intent.putExtra("time", selectedTime)  // Mengirim waktu
                intent.putExtra("destination", selectedTujuan)  // Mengirim tujuan
                intent.putExtra("hargaTiket", hargaTiket) // Menyertakan harga tiket jika diperlukan

                startActivity(intent) // Memulai SecondActivity
                alertDialog.dismiss() // Menutup dialog
            }
            dialogView.findViewById<Button>(R.id.button_batal).setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
}