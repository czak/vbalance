package pl.czak.virginbalance;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by czak on 09/02/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }
}
