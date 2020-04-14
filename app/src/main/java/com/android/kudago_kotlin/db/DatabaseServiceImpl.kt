package com.android.kudago_kotlin.db

import com.android.kudago_kotlin.db.query.QueryDecorator
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject

class DatabaseServiceImpl : DatabaseService {

    override fun <E : RealmModel> deleteItems(classObj: Class<E>, decorator: QueryDecorator<E>) =
        deleteItemList(classObj, decorator)

    override fun <E : RealmModel> getItemList(
        classObj: Class<E>,
        decorator: QueryDecorator<E>
    ): List<E> {
        return try {
            Realm.getDefaultInstance().use { realm ->
                val realmList = decorator.decorateQuery(realm.where(classObj)).findAll()
                realm.copyFromRealm(realmList)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun <E : RealmModel> saveItemList(items: List<E>): Boolean {
        if (items.isEmpty()) {
            return true
        }
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            return try {
                realm.copyToRealmOrUpdate(items)
                realm.commitTransaction()
                true
            } catch (e: Exception) {
                realm.cancelTransaction()
                false
            }
        }
    }

    override fun <E : RealmModel> deleteItemList(
        classObj: Class<E>,
        decorator: QueryDecorator<E>
    ): Boolean {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            return try {
                val items = decorator.decorateQuery(realm.where(classObj))
                    .findAll()
                for (index in items.indices.reversed()) {
                    RealmObject.deleteFromRealm(items.get(index)!!)
                }
                realm.commitTransaction()
                true
            } catch (e: Exception) {
                realm.cancelTransaction()
                false
            }
        }
    }
}