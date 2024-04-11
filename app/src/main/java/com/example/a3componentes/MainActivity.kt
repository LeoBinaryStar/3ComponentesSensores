package com.example.a3componentes
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var accelerometerText: TextView? = null
    private var proximityText: TextView? = null
    private var lightProgress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener una instancia del SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Obtener instancias de los sensores
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        proximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Referenciar textviews y barra de progreso
        accelerometerText = findViewById(R.id.accelerometer_text)
        proximityText = findViewById(R.id.proximity_text)
        lightProgress = findViewById(R.id.light_progress)
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del acelerómetro y del sensor de proximidad
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        // Detener la escucha de los sensores
        sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Actualizar los textviews y la barra de progreso cuando cambia un sensor
        if (event.sensor == accelerometer) {
            accelerometerText!!.text =
                "Acelerómetro: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]
        } else if (event.sensor == proximitySensor) {
            val distance = event.values[0]
            if (distance < proximitySensor!!.maximumRange) {
                proximityText!!.text = "Cerca"
                proximityText!!.setTextColor(Color.RED)
            } else {
                proximityText!!.text = "Lejos"
                proximityText!!.setTextColor(Color.GREEN)
            }
        } else if (event.sensor == lightSensor) {
            // Actualizar el valor de la luz y el progreso de la barra
            val lightLevel = event.values[0].toInt()
            lightProgress!!.progress = lightLevel
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Método requerido, pero no necesitamos implementarlo aquí
    }
}
