package com.example.patrick.bateria_back_arquivo;

import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by patrick on 9/7/16.
 */
public class ThreadAuxiliar extends Thread{

    final Handler handler = new Handler();
    File arquivo = new File(Environment.getExternalStorageDirectory().toString() + "/teste.txt");

    Runnable runnableCode;

    @Override
    public void run(){
        FileWriter escritor = null;
        try {
            escritor = new FileWriter(arquivo, true);
        } catch (IOException e) {
            e.printStackTrace();
        }



        final FileWriter finalEscritor = escritor;
        runnableCode = new Runnable() {

            private int contador = 0;
            @Override
            public void run() {

                try {
                    finalEscritor.write("\n" + contador);

                    finalEscritor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(contador++<10) handler.postDelayed(this, 2000);
            }
        };

        handler.post(runnableCode);
    }
}
