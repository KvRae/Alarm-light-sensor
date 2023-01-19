package devsec.app.lightsensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), SensorEventListener {

    //var
    lateinit var sensor: Sensor
    lateinit var sensorManager: SensorManager
    lateinit var alarmPlayer: MediaPlayer
    var isAlarmOn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.alarm)
        alarmPlayer = MediaPlayer.create(this,uri)

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    //sensors Logic
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                val value = event.values[0]
                if (value > 40 && !isAlarmOn) {
                    //light is on
                    isAlarmOn = true
                    alarmPlayer.start()


                } else {
                    //light is off
                    isAlarmOn = false
                    alarmPlayer.stop()




                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}