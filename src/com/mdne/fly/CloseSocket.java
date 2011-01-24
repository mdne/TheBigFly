package com.mdne.fly;

public class CloseSocket extends FlySensor implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(this){
			connect.setConnected(true);
		}
	}
}
