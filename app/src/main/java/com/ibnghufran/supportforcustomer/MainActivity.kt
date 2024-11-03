package com.ibnghufran.supportforcustomer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.ibnghufran.supportforcustomer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val images = intArrayOf(
        R.drawable.image1, // Add your image resources here
        R.drawable.image2,
        R.drawable.image3
        // Add more images as needed
    )

    private companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewPager for the image slider
        imageSliderAdapter = ImageSliderAdapter(this, images)
        binding.imageSlider.adapter = imageSliderAdapter

        // Request SMS permissions
        requestSmsPermission()

        // Load the first fragment initially
        if (savedInstanceState == null) {
            loadFragment(Step1Fragment())
        }
    }

    private fun requestSmsPermission() {
        val permissionReceiveSms = Manifest.permission.RECEIVE_SMS
        val permissionReadSms = Manifest.permission.READ_SMS

        if (ContextCompat.checkSelfPermission(this, permissionReceiveSms) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, permissionReadSms) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(permissionReceiveSms, permissionReadSms),
                SMS_PERMISSION_REQUEST_CODE
            )
        }
    }

    fun goToNextFragment(step: Int) {
        val fragment = when (step) {
            1 -> Step2Fragment() // Replace with your Step2Fragment class
            2 -> Step3Fragment() // Replace with your Step3Fragment class
            // Add more cases for additional steps
            else -> null
        }

        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, it)
                .addToBackStack(null) // Enables back navigation
                .commit()
        }
    }

    fun goToPreviousFragment() {
        // Pop the back stack to return to the previous fragment
        supportFragmentManager.popBackStack()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        // If Step1Fragment is displayed, exit the app; otherwise, handle back navigation normally
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) is Step1Fragment) {
            finish() // Exit app
        } else {
            super.onBackPressed()
        }
    }
}
