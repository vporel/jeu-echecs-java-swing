package model;

import java.util.EventObject;

public class BoxDataEvent extends EventObject {

    public BoxDataEvent(BoxData source) {
        super(source);
    }
}
