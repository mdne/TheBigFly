package com.mdne.fly;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Connect implements Runnable {

	private String serverIp;
	private int serverPort;
	private String output_line;
	private boolean connected;

	public Connect(String ip, int port) {
		serverIp = ip;
		serverPort = port;
		output_line = null;
		setConnected(false);
	}

	public String getIp() {
		return serverIp;
	}

	public void setParams(String s) {
		this.output_line = s;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isConnected() {
		return connected;
	}

	public void run() {
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIp);
			Socket socket = new Socket(serverAddr, serverPort);
			setConnected(true);
			while (!Thread.currentThread().isInterrupted()) {
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					out.println(output_line);
				} catch (Exception e) {
					Log.e("ClientActivity", "S: Error", e);
				}
			}
			socket.close();
			setConnected(false);
		} catch (Exception e) {
			Log.e("ClientActivity", "C: Error", e);
		}
	}

}
