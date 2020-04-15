package com.android.kudago_kotlin.db

import com.android.kudago_kotlin.db.query.EmptyQueryDecorator
import com.android.kudago_kotlin.db.query.QueryDecorator
import io.realm.RealmModel

interface DatabaseService {

    fun <E : RealmModel> getItemList(
        classObj: Class<E>,
        decorator: QueryDecorator<E> = EmptyQueryDecorator()
    ): List<E>

    fun <E : RealmModel> saveItemList(items: List<E>): Boolean

    fun <E : RealmModel> deleteItemList(
        classObj: Class<E>,
        decorator: QueryDecorator<E> = EmptyQueryDecorator()
    ): Boolean
}