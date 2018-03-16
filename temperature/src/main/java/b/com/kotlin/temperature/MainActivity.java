package b.com.kotlin.temperature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity   {

private SensorManager mSensorManager=null;
    private Sensor mSensor=null;
    private TextView textView1=null;
    private Button button1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=(TextView)findViewById(R.id.thermo_c);
        /*获取系统服务（SENSOR_SERVICE）返回一个SensorManager对象*/
        mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        /*通过SensorManager获取相应的（温度传感器）Sensor类型对象*/
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        /*注册相应的SensorService*/
        button1=(Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mSensorManager.registerListener(mSensorEventListener, mSensor
                        , SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(getApplication(),"正在测试温度...", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*声明一个SensorEventListener对象用于侦听Sensor事件，并重载onSensorChanged方法*/
    private final SensorEventListener mSensorEventListener=new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_TEMPERATURE){
                /*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
                float temperature=event.values[0];
                textView1.setText(String.valueOf(temperature)+"°C");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };


}
