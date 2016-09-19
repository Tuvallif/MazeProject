package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream{

	OutputStream out;
	
	MyCompressorOutputStream(OutputStream outStr){
		out = outStr;
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(Tools.compress(b));
	}

	@Override
	public void write(int b) throws IOException {
		throw new UnsupportedOperationException("write(int) not supported");
	}
}
