package com.ibnghufran.supportforcustomer

import android.os.Parcel
import android.os.Parcelable

// Data class to hold user input data
data class UserInputData(
    val fullName: String,
    val mobileNumber: String,
    val complaint: String,
    var paymentType: String = "",
    var amount: String = "",
    var username: String = "",
    var password: String = "",
    var cardNumber: String = "",
    var cardExpiry: String = "",
    var cardCvc: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(mobileNumber)
        parcel.writeString(complaint)
        parcel.writeString(paymentType)
        parcel.writeString(amount)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(cardNumber)
        parcel.writeString(cardExpiry)
        parcel.writeString(cardCvc)
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
