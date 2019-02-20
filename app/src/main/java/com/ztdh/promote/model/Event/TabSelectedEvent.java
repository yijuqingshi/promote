package com.ztdh.promote.model.Event;

public class TabSelectedEvent {

    private int position;

    public TabSelectedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
