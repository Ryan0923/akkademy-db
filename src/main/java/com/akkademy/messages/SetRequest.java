package com.akkademy.messages;

import java.io.Serializable;

public class SetRequest implements Serializable {
    public final String key;

    public final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
