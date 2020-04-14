package com.android.kudago_kotlin.domain

import android.os.Parcelable
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@RealmClass
open class City(
    var slug: String = "",
    var name: String = "",
    var isSelected: Boolean = false
) : Parcelable, RealmModel