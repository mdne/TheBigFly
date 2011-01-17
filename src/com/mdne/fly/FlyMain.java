package com.mdne.fly;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mdne.fly.FlySensor;

public class FlyMain extends Activity implements OnClickListener{
	private TextView outView_out, outView_coord;
	private static final String TAG = "TheBigFly";
	private Connect connect;
	private Thread tconnect;
	private FlySensor datasensor;
	private static String output;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		outView_out = (TextView) findViewById(R.id.output);
		outView_coord = (TextView) findViewById(R.id.coord);
		connect = new Connect("192.168.0.7", 64445);
		Intent intent = new Intent();
        intent.setClass(datasensor, FlySensor.class);
        startActivity(intent);          
		outView_out.setText(datasensor.out);

		final Button cbutton = (Button) findViewById(R.id.connect);
		final Button dbutton = (Button) findViewById(R.id.disconnect);

		
        dbutton.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        Toast.makeText(this, "It works", Toast.LENGTH_SHORT).show();
    }		
//		dbutton.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//				tconnect.interrupt();
//				outView_out.setText("disconnected");
//			}
//		});

//		cbutton.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//				if(!connect.isConnected()){
//					tconnect = new Thread(connect);
//					tconnect.start();
//				}				
//				outView_out.setText("connected");
//			}
//		});
	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	}
//	public static void setOut(String out) {
//		output = out;
//	}

}
