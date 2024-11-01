package com.ibnghufran.supportforcustomer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request SMS permissions
        requestSmsPermission()
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

    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 101
    }
}
