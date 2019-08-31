package com.example.a2_sen_sd;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TelaModoSeguranca extends AppCompatActivity{
    TextView ProximitySensor, textView4;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
    private static final String host = "192.168.0.102";
    private static final int port = 3030;
    Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_seguranca);

        ProximitySensor = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(proximitySensorEventListener,
            myProximitySensor,
            SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //TODO Auto-generated method stub
        }
        @Override
        public void onSensorChanged(SensorEvent event) {

            //TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    textView4.setText("Detectado");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                socket = new Socket(host,port);
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                                bw.write("Aproximacao detectada");
                                bw.newLine();
                                bw.flush();
                                socket.close();

                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else{
                    textView4.setText("");
                }
            }
        }

    };


}
