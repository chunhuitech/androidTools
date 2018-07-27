package cn.chunhuitech.www.androidtools;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hechengjin on 18-7-26.
 */

public class ItemStepActivity extends AppCompatActivity {
    private TextView stepDetectorText;
    private TextView stepCounterText;
    private TextView stepDetectorTimeText;
    private TextView isSupportStepDetector;
    private TextView isSupportStepCounter;
    private SensorManager sensorManager;
    private Sensor stepCounter;//步伐总数传感器
    private Sensor stepDetector;//单次步伐传感器
    private SensorEventListener stepCounterListener;//步伐总数传感器事件监听器
    private SensorEventListener stepDetectorListener;//单次步伐传感器事件监听器

    private SimpleDateFormat simpleDateFormat;//时间格式化

    private long sensorTimeReference = 0l;
    private long myTimeReference = 0l;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stepbystep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器系统服务
        stepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);//获取计步总数传感器
        stepDetector=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);//获取单次计步传感器


        stepDetectorText= (TextView) findViewById(R.id.StepDetector);
        stepCounterText= (TextView) findViewById(R.id.StepCounter);
        stepDetectorTimeText= (TextView) findViewById(R.id.StepDetectorTime);
        isSupportStepDetector= (TextView) findViewById(R.id.IsSupportStepDetector);
        isSupportStepCounter= (TextView) findViewById(R.id.IsSupportStepCounter);


        isSupportStepDetector.setText("是否支持StepDetector[单次步伐传感器]:"+
                String.valueOf(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)));

        isSupportStepCounter.setText("是否支持StepCounter[步伐总数传感器]:"+
                String.valueOf(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)));

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


        initListener();
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void initListener() {
        stepCounterListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.e("Counter-SensorChanged",event.values[0]+"---"+event.accuracy+"---"+event.timestamp);
                stepCounterText.setText("总步伐计数:"+event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.e("Counter-Accuracy",sensor.getName()+"---"+accuracy);

            }
        };

        stepDetectorListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.e("Detector-SensorChanged",event.values[0]+"---"+event.accuracy+"---"+event.timestamp);
                stepDetectorText.setText("当前步伐计数:"+event.values[0]);

                // set reference times
                if(sensorTimeReference == 0l && myTimeReference == 0l) {
                    sensorTimeReference = event.timestamp;
                    myTimeReference = System.currentTimeMillis();
                }
                // set event timestamp to current time in milliseconds
                event.timestamp = myTimeReference +
                        Math.round((event.timestamp - sensorTimeReference) / 1000000.0);
                stepDetectorTimeText.setText("当前步伐时间:"+simpleDateFormat.format(event.timestamp));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.e("Detector-Accuracy",sensor.getName()+"---"+accuracy);

            }
        };
    }

    private void registerSensor(){
        //注册传感器事件监听器
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)&&
                getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)){
            sensorManager.registerListener(stepDetectorListener,stepDetector,SensorManager.SENSOR_DELAY_FASTEST);
            sensorManager.registerListener(stepCounterListener,stepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private void unregisterSensor(){
        //解注册传感器事件监听器
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)&&
                getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)){
            sensorManager.unregisterListener(stepCounterListener);
            sensorManager.unregisterListener(stepDetectorListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensor();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerSensor();
    }
}
