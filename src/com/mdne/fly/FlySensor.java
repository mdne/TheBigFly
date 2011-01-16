/**
 * TODO
 * 1. проверка наличия соединения с инетом при старте программы и вывод диалога еси отсутствует или предложение подключить.
 * 2. вывести управление сенсором в отдельный поток
 * 3. добавить систему вывода системных сообщений в output на главной форме
 */

package com.mdne.fly;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FlySensor extends Activity implements SensorEventListener {
	private static final String TAG = "TheBigFly";
	private SensorManager sensorManager;
	private TextView outView_out, outView_coord;
	private Sensor sensor;
	private boolean sensorReady;

	private float[] mag_vals = new float[3];
	private float[] acc_vals = new float[3];
	private float[] actual_orientation = new float[3];
	private Connect connect;
	private Thread tconnect;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		outView_out = (TextView) findViewById(R.id.output);
		outView_coord = (TextView) findViewById(R.id.coord);
		connect = new Connect("192.168.0.7", 64445);

		final Button cbutton = (Button) findViewById(R.id.connect);
		final Button dbutton = (Button) findViewById(R.id.disconnect);

		dbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				tconnect.interrupt();
				outView_out.setText("disconnected");
			}
		});

		cbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(!connect.isConnected()){
					tconnect = new Thread(connect);
					tconnect.start();
				}				
				outView_out.setText("connected");
			}
		});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	/** Register for the updates when Activity is in foreground */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	/** Stop the updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		sensorManager.unregisterListener(this, sensor);
	}

	public void onAccuracyChanged(int sensor, int accuracy) {
		Log.d(TAG, String.format(
				"onAccuracyChanged  sensor: %d   accuraccy: %d", sensor,
				accuracy));
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_MAGNETIC_FIELD:
			this.mag_vals = event.values.clone();
			this.sensorReady = true;
			break;
		case Sensor.TYPE_ACCELEROMETER:
			this.acc_vals = event.values.clone();
			break;
		}
		if (this.mag_vals != null && this.acc_vals != null && this.sensorReady) {
			this.sensorReady = false;
			float[] R = new float[16];
			float[] I = new float[16];
			SensorManager.getRotationMatrix(R, I, this.acc_vals, this.mag_vals);
			SensorManager.getOrientation(R, this.actual_orientation);
			String out = String.format("%.1f " + "%.1f",
					Math.toDegrees(actual_orientation[1]),
					Math.toDegrees(actual_orientation[2]));
			Log.d(TAG, out);
			outView_coord.setText(out);
			connect.getParams(out);
		}
	}

	public void onDestroy() {
		super.onDestroy();
	}
}