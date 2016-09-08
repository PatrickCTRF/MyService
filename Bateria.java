package com.example.patrick.bateria_back_arquivo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * Created by patrick on 9/7/16.
 */
public class Bateria extends ContextWrapper{


    private TextView textBatteryLevel = null;
    private String batteryLevelInfo = "Battery Level", nulidade, info;
    private String eixoX = "", eixoY = "", eixoZ = "";
    private String giroX = "", giroY = "", giroZ = "";
    private SensorManager manager;
    private Sensor sensorA, sensorG;
    private LocationManager locationManager;
    private int qtde = 0;
    private List<Sensor> lista;
    static public Bundle savedInstanceState2;

    /* Criei classes anônimas separadas para o acelerometro e o giroscopio para poder usar dois registerListener() diferentes*/

    private SensorEventListener acelerometro = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {//Este método é chamado sempre que há mudança nos valores dos sensores do sistema.

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                eixoX = "" + event.values[0];//float relativo ao eixo x.
                eixoY = "" + event.values[1];//float relativo ao eixo y.
                eixoZ = "" + event.values[2];//float relativo ao eixo z.
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener giroscopio = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {//Este método é chamado sempre que há mudança nos valores dos sensores do sistema.

            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                giroX = "" + event.values[0];//float relativo ao eixo x.
                giroY = "" + event.values[1];//float relativo ao eixo y.
                giroZ = "" + event.values[2];//float relativo ao eixo z.
            } else {
                giroX = "erro aki";
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public Bateria(Context base) {
        super(base);
    }


    public void onCreate(Bundle savedInstanceState){

        //setContentView(R.layout.activity_main);//aki era so main

        //textBatteryLevel = (TextView) findViewById(R.id.txtBatteryInfo);//deve-se criar um recurso com o ID em vermelho!

        //registerBatteryLevelReceiver();

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//Aki eu tenho um gerenciador para o serviço de sensoreamento do dispositivo.

        sensorG = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);//Obtive o sensor específico para acelerômetro.
        manager.registerListener(giroscopio, sensorG, SensorManager.SENSOR_DELAY_FASTEST);//Registrei um "servico de escuta" para o sensorG q desejo acompanhar.this refere-se a MainActivity, que é um SensorEventListener.

        sensorA = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//Obtive o sensor específico para acelerômetro.
        manager.registerListener(acelerometro, sensorA, SensorManager.SENSOR_DELAY_FASTEST);//Registrei um "servico de escuta" para o sensorA q desejo acompanhar.this refere-se a MainActivity, que é um SensorEventListener.

        if(sensorG == null) nulidade = "Sensor Inexistente"; nulidade = "";

        lista = manager.getSensorList(Sensor.TYPE_ALL);

        qtde = 0;
        for(Sensor aux : lista){//O enhanced for percorrerá um numero de loops equivalente ao numero de sensores do celular.
            qtde++;//Assim incrementaremos qtde em funçao da qtde de sensores disponiveis.
        }

        info = "Battery Level: " + "%\n";
        info += ("Technology: " + "\n");
        info += ("Plugged: " + "\n");
        info += ("Health: " + "\n");
        info += ("Status: " + "\n");
        info += ("Voltage: " + "\n");
        info += ("Temperature: " + "\n");
        info += ("\nAcelerômetro:\nEixo x: " + eixoX + "\n" + "Eixo y: " + eixoY + "\n" + "Eixo z: " + eixoZ + "\n");//Esteas duas linhas imprimem os sensores de acelerômetro e giroscópio.
        info += ("\nGiroscópio:" + nulidade + "\nGiro x: " + giroX + "\n" + "Giro y: " + giroY + "\n" + "Giro z: " + giroZ + "\n");
        info += ("\nNúmero de Sensores Disponíveis: " + qtde + "\n");
    }

    public void onDestroy() {

        //unregisterReceiver(battery_receiver);
        manager.unregisterListener(acelerometro);
    }


    public String getInfo() {
        onCreate(savedInstanceState2);
        return info;
    }
}
