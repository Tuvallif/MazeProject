package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

	InputStream in;

	MyDecompressorInputStream(InputStream inStr) {
		in = inStr;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] res) throws IOException {
		byte[] bytesRead = new byte[res.length];
		in.read(bytesRead);
		res = Tools.decompress(bytesRead);
		return res.length;
	}

	@Override
	// off is ignored
	public int read(byte[] res, int off, int size) throws IOException {
		byte[] bytesRead = new byte[size];
		in.read(bytesRead);
		res = Tools.decompress(bytesRead);
		return res.length;
	}
}
