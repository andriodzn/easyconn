package org.androidzn.easyconn.mock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SimpleService extends Service {

    private SimpleServiceAPI binder = new SimpleServiceAPI();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}
