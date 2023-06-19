package ru.startandroid.develop.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var tvText: TextView? = null
    private var sensorManager: SensorManager? = null
    private var sensors: List<Sensor>? = null
    private var sensorLight: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvText = findViewById<View>(R.id.tvText) as TextView
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensors = sensorManager!!.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun onClickSensList(v: View?) {
        sensorManager!!.unregisterListener(listenerLight, sensorLight)
        val sb = StringBuilder()
        for (sensor in sensors!!) {
            sb.append("name = ").append(sensor.name)
                .append(", type = ").append(sensor.type)
                .append("\nvendor = ").append(sensor.vendor)
                .append(" ,version = ").append(sensor.version)
                .append("\nmax = ").append(sensor.maximumRange)
                .append(", resolution = ").append(sensor.resolution)
                .append("\n--------------------------------------\n")
        }
        tvText!!.text = sb
    }

    fun onClickSensLight(v: View?) {
        sensorManager!!.registerListener(listenerLight, sensorLight,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(listenerLight, sensorLight)
    }

    private var listenerLight: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            tvText!!.text = event.values[0].toString()
        }
    }
}