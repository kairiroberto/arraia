package com.arraiax.kairi.arraiax;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class HorarioService extends Service {

    private static final String TAG = "MyService";
    private static int contador = 0;
    static private Double latitude = 0.0;
    static private Double longitude = 0.0;
    private Double raioSala = 0.0002;
    private final int REQUEST_LOCATION = 200;
    HorarioSQlite qlite;
    private AlertDialog alertDialog;

    LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public HorarioService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Está dando certo: " + (contador++));
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(new Activity(), Manifest.permission.READ_PHONE_STATE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(new Activity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(new Activity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(new Activity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            }
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
        qlite = new HorarioSQlite(this);
        verificarPresenca();
        return super.onStartCommand(intent, flags, startId);
    }

    private void verificarPresenca() {
        Log.d(TAG, "" + qlite.findAll().size());
        if (qlite.findAll().size() > 0) {
            for (Horario h : qlite.findAll()) {
                String[] horaini = h.getHorarioini().split(":");
                String[] horafin = h.getHorariofin().split(":");
                Date date = new Date();
                Log.d(TAG, "Data " + date);
                Date dateini = new Date();
                dateini.setHours(Integer.parseInt(horaini[0]));
                dateini.setMinutes(Integer.parseInt(horaini[1]));
                Log.d(TAG, "Data ini " + dateini);
                Date datefin = new Date();
                datefin.setHours(Integer.parseInt(horafin[0]));
                datefin.setMinutes(Integer.parseInt(horafin[1]));
                Log.d(TAG, "Data fin " + datefin);
                Log.d(TAG, "Teste " + (date.after(dateini) && date.before(datefin)));
                if (date.after(dateini) && date.before(datefin)) {
                    Log.d(TAG, "Latitude now " + latitude);
                    Log.d(TAG, "Longitude now " + longitude);
                    Log.d(TAG, "Latitude bd " + h.getLatitude());
                    Log.d(TAG, "Latitude bd " + h.getLongitude());
                    double raio = Math.pow((latitude - h.getLatitude()), 2.0) + Math.pow((longitude - h.getLongitude()), 2.0);
                    raio = Math.sqrt(raio);
                    Log.d(TAG, "" + raio);
                    if (raio < raioSala) {
                    } else {
                    }
                } else {
                    Log.d(TAG, "Latitude now " + latitude);
                    Log.d(TAG, "Longitude now " + longitude);
                    Log.d(TAG, "Latitude bd " + h.getLatitude());
                    Log.d(TAG, "Latitude bd " + h.getLongitude());
                    double raio = Math.pow((latitude - h.getLatitude()), 2.0) + Math.pow((longitude - h.getLongitude()), 2.0);
                    raio = Math.sqrt(raio);
                    Log.d(TAG, "" + raio);
                    if (raio < raioSala) {
                        Toast.makeText(this, "Está na sala: " + raio, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Não está na sala: " + raio, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
