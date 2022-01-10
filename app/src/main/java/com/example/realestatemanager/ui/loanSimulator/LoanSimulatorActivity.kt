package com.example.realestatemanager.ui.loanSimulator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*

class LoanSimulatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_simulator)
        configureToolbar()
        //TODO: addEveryListener()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}