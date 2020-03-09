package service;

import android.app.Activity;

public interface PrintService {
    void print(String propertyNumber ,Activity activity);
    void terminate();
}
