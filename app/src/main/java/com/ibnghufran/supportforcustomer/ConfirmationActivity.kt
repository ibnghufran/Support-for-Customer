package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.ibnghufran.supportforcustomer.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding
    private lateinit var countDownTimer: CountDownTimer
    private var timeInMillis: Long = 5 * 60 * 60 * 1000 // 5 hours in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start the countdown timer
        startTimer()

        // Set click listener for the "Submit Another Query" button
        binding.submitAnotherQueryButton.setOnClickListener {
            // Step1Fragment par wapas jaane ke liye intent create karein
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("RESET_FORM", true) // Flag set karein to reset form details
            startActivity(intent)
            finish() // Current activity ko close karein
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / 1000) / 3600
                val minutes = ((millisUntilFinished / 1000) % 3600) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                // Update the timer text view
                binding.timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                binding.timerTextView.text = "00:00:00"
                // Optionally handle what happens when the timer finishes
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel() // Cancel the timer when the activity is destroyed
    }
}
