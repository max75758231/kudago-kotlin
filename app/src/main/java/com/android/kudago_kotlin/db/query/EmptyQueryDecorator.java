package com.android.kudago_kotlin.db.query;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public class EmptyQueryDecorator<T extends RealmModel> implements QueryDecorator<T> {
    @Override
    public RealmQuery<T> decorateQuery(RealmQuery<T> query) {
        return query;
    }
}
