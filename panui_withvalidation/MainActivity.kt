package com.example.panui_withvalidation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer



class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var etPanNumber: EditText
    private lateinit var etDay: EditText
    private lateinit var etMonth: EditText
    private lateinit var etYear: EditText
    private lateinit var btnNext: Button
    private lateinit var btnNoPan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPanNumber = findViewById(R.id.etPanNumber)
        etDay = findViewById(R.id.etDay)
        etMonth = findViewById(R.id.etMonth)
        etYear = findViewById(R.id.etYear)
        btnNext = findViewById(R.id.btnNext)
        btnNoPan = findViewById(R.id.btnNoPan)

        etPanNumber.addTextChangedListener {
            viewModel.validatePan(it.toString())
        }

        val birthDateWatcher = {
            viewModel.validateBirthDate(
                etDay.text.toString(),
                etMonth.text.toString(),
                etYear.text.toString()
            )
        }

        etDay.addTextChangedListener { birthDateWatcher() }
        etMonth.addTextChangedListener { birthDateWatcher() }
        etYear.addTextChangedListener { birthDateWatcher() }

        viewModel.isNextButtonEnabled.observe(this, Observer { isEnabled ->
            btnNext.isEnabled = isEnabled
        })

        btnNext.setOnClickListener {
            Toast.makeText(this, "Details submitted successfully", Toast.LENGTH_SHORT).show()
        }

        btnNoPan.setOnClickListener {
            finish()
        }
    }
}
