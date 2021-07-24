package com.xslczx.editundo;

import android.support.annotation.Nullable;

public class Action {
    public final String key;
    public final Object value;

    public Action(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Action &&
                ((Action) obj).key.equals(key) &&
                ((Action) obj).value.equals(value);
    }

    @Override
    public String toString() {
        return "Action{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}

