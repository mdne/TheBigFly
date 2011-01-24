package com.mdne.fly;

public class OutputArray {
	protected byte[] outBuffer;
	// private byte[] out;
	private float[] arrayAfterConv;
	private boolean flag;
	private String s;

	public OutputArray() {
		outBuffer = new byte[13];
		arrayAfterConv = new float[3];
		flag = false;
		s = "10";
		// arr = new float[] { (float) 2, (float) 3, (float) 5 };
	}
	
	public void setArray(float[] string) {
		outBuffer = convertArray(string);
	}

	public byte[] getByteArray() {
		return outBuffer;
	}

	public float[] convToDegrees(float[] array) {
		float[] arr = new float[3];
		for (int i = 0; i < 3; i++) {
			arr[i] = (float) Math.toDegrees(array[i]);
		}
		return arr;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean f) {
		this.flag = f;
	}

	public byte[] convertArray(float[] arr) {
		arrayAfterConv = convToDegrees(arr);
		byte floatBytes[] = new byte[12];
		for (int i = 0; i < arrayAfterConv.length; i++) {
			int n = i * 4;
			int floatBits = Float.floatToIntBits(arrayAfterConv[i]);
			floatBytes[n] = (byte) (floatBits >> 24);
			floatBytes[n + 1] = (byte) (floatBits >> 16);
			floatBytes[n + 2] = (byte) (floatBits >> 8);
			floatBytes[n + 3] = (byte) (floatBits);
		}
		if(flag){
			s = "11";
		}
		else {
			s = "10";
		}
		 int len = s.length();
		 byte[] tmp = new byte[len / 2];
		 for (int i = 0; i < len; i += 2) {
		 tmp[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) +
		 Character
		 .digit(s.charAt(i + 1), 16));
		 }
		 byte data[] = new byte[13];
		 for(int i =0; i<floatBytes.length; i++){
		 data[i] = floatBytes[i];
		 }
		 for(int i =0; i<1; i++){
		 data[12] = tmp[i];
		 }
		 data[12] = tmp[0];
		 return data;
//		return floatBytes;
	}
}
