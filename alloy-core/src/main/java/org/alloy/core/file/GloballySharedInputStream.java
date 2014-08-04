package org.alloy.core.file;

import java.io.IOException;
import java.io.InputStream;

/**
* This class serves as a marker interface to indicate that the resource comes from a shared area of the
* filesystem. It allows multi-site implementations to only generate one copy of assets that are being
* resized.
*
* @author bpolster
*
*/
public class GloballySharedInputStream extends InputStream {
	private InputStream parentInputStream;

	public GloballySharedInputStream(InputStream parentInputStream) {
		this.parentInputStream = parentInputStream;
	}

	@Override
	public int available() throws IOException {
		return parentInputStream.available();
	}

	@Override
	public void close() throws IOException {
		parentInputStream.close();
	}

	@Override
	public void mark(int arg0) {
		parentInputStream.mark(arg0);
	}

	@Override
	public boolean markSupported() {
		return parentInputStream.markSupported();
	}

	@Override
	public int read() throws IOException {
		return parentInputStream.read();
	}

	@Override
	public int read(byte[] arg0, int arg1, int arg2) throws IOException {
		return parentInputStream.read(arg0, arg1, arg2);
	}

	@Override
	public int read(byte[] arg0) throws IOException {
		return parentInputStream.read(arg0);
	}

	@Override
	public void reset() throws IOException {
		parentInputStream.reset();
	}

	@Override
	public long skip(long arg0) throws IOException {
		return parentInputStream.skip(arg0);
	}
}