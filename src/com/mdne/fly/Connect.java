package com.mdne.fly;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class Connect implements Runnable {

	private String serverIp;
	private int serverPort;
	protected volatile boolean connected;
	protected OutputArray arr;

	public Connect() {
		setConnected(false);
		arr = new OutputArray();
		// arr.setArray();

	}

	public Connect(String ip, int port) {
		this();
		serverIp = ip;
		serverPort = port;

	}

	public String getIp() {
		return serverIp;
	}

	public void setConnected(boolean connect) {
		this.connected = connect;
	}

	public boolean isConnected() {
		return connected;
	}

	public void run() {
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIp);
			Socket socket = new Socket(serverAddr, serverPort);
			OutputStream dos = socket.getOutputStream();
//			 setConnected(true);
			while (!connected) {
				try {
					Thread.sleep(50);
					dos.write(arr.getByteArray());				
				} catch (Exception e) {
					Log.e("ClientActivity", "S: Error", e);
				}
			}
			dos.flush();
			dos.close();
			socket.close();
			setConnected(false);
			arr.setFlag(false);
		} catch (Exception e) {
			Log.e("ClientActivity", "C: Error", e);
		}
	}
}
