package com.android.kudago_kotlin.db.query;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public interface QueryDecorator<T extends RealmModel> {
    RealmQuery<T> decorateQuery(RealmQuery<T> query);
}