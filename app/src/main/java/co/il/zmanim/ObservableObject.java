package co.il.zmanim;

import android.content.Intent;

import java.util.Observable;



public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

    private ObservableObject() {
    }

    public void updateValue(Intent data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
