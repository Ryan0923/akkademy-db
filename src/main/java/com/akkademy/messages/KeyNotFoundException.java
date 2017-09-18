package com.akkademy.messages;

    import java.io.Serializable;

public class KeyNotFoundException extends Throwable implements Serializable {
    public final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }
}
