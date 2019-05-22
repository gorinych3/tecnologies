package ru.egor.otherclasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyMessage {
    String message;
    int id;

    public MyMessage(String message) {
        this.message = message;
    }

    public MyMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }
}
