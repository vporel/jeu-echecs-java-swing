package model;


import java.util.EventListener;

public interface BoxesManagerListener extends EventListener {

    void boxStateUpdated(BoxDataEvent event);
}
