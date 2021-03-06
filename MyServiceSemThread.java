package com.example.patrick.bateria_back_arquivo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Thread.sleep;


public class MyServiceSemThread extends Service{

    private TextView textBatteryLevel = null;
    private String batteryLevelInfo = "Battery Level", nulidade, tempo = "";
    private String eixoX = "", eixoY = "", eixoZ = "";
    private String giroX = "", giroY = "", giroZ = "";
    private SensorManager manager;
    private Sensor sensorA, sensorG;
    private LocationManager locationManager;
    private int qtde = 0, i = 0;
    private List<Sensor> lista;
    private boolean pegouDado = false;
    private Calendar rightNow = Calendar.getInstance();

    final Handler handler = new Handler();
    final AquisicaoSensores info = new AquisicaoSensores(this);

    Runnable runnableCode;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", LENGTH_LONG).show();

        runnableCode = new Runnable() {

            private int contador = 0;
            @Override
            public void run() {

                Calendar calendario = Calendar.getInstance();

                File arquivo = new File(Environment.getExternalStorageDirectory().toString() + "/teste.txt");

                try {
                    FileWriter escritor = new FileWriter(arquivo, true);

                    escritor.write("\n\nTempo atual: " + calendario.get(Calendar.HOUR) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND) + "," + calendario.get(Calendar.MILLISECOND) + "\n" + info.getInfo());


                    escritor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(contador++<10) handler.postDelayed(this, 10000);
            }
        };

        handler.post(runnableCode);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        handler.removeCallbacks(runnableCode);
        Toast.makeText(this, "Service Destroyed", LENGTH_LONG).show();

    }

}