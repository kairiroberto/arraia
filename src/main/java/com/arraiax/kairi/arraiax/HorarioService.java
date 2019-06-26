package com.arraiax.kairi.arraiax;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class HorarioService extends Service {

    private static final String TAG = "MyService";

    public HorarioService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "AQUI");
        return super.onStartCommand(intent, flags, startId);
    }
}
