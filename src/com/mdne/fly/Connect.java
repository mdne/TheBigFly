package com.mdne.fly;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class Connect implements Runnable {

	private String serverIp;
	private int serverPort;
	private boolean connected;
	protected OutputArray arr;
	private byte[] input;
	private String status;

	public Connect() {
		setConnected(false);
		arr = new OutputArray();
		input = null;
		status = "";
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

	public String status() {
		return status;
	}

	public void run() {
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIp);
			Socket socket = new Socket(serverAddr, serverPort);
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			arr.setFlag(false);
			// is.read(input);
			// if (Byte.valueOf("11", 16).equals(input[0])) {
			// status = "connected";
			// }
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(20);
					os.write(arr.getByteArray());
				} catch (Exception e) {
					Log.e("ClientActivity", "S: Error", e);
				}
			}
			os.flush();
			os.close();
			socket.close();
			setConnected(false);
			arr.setFlag(false);
			input[0] = (Byte) null;
		} catch (Exception e) {
			Log.e("ClientActivity", "C: Error", e);
		}
	}
}
