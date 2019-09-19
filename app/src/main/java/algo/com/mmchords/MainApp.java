package algo.com.mmchords;

import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class MainApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
