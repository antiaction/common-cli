package com.antiaction.common.cli;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.concurrent.Semaphore;

public class SynchronizedOutput {

	private RandomAccessFile raf = null;
	private RandomAccessFileOutputStream rafOut = null;
	private Semaphore semaphore = new Semaphore(1);

	public PrintStream out = null;

	public SynchronizedOutput(String fname, int buffersize) throws IOException {
		try {
			raf = new RandomAccessFile(fname, "rw");
			raf.seek(0);
			raf.setLength(0);
			RandomAccessFileOutputStream fout = new RandomAccessFileOutputStream(raf);
			out = new PrintStream(new BufferedOutputStream(fout, buffersize), false, "UTF-8");
		}
		catch (IOException e) {
			close();
			throw e;
		}
	}

	public SynchronizedOutput(File file, int buffersize) throws IOException {
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.seek(0);
			raf.setLength(0);
			RandomAccessFileOutputStream fout = new RandomAccessFileOutputStream(raf);
			out = new PrintStream(new BufferedOutputStream(fout, buffersize), false, "UTF-8");
		}
		catch (IOException e) {
			close();
			throw e;
		}
	}

	public void acquire() {
		semaphore.acquireUninterruptibly();
	}

	public void release() {
		semaphore.release();
	}

	public void close() {
		semaphore = null;
		if (out != null) {
			out.flush();
			out.close();
			out = null;
		}
		if (rafOut != null) {
			try {
				rafOut.close();
			}
			catch (IOException e) {
			}
			rafOut = null;
		}
		if (raf != null) {
			try {
				raf.close();
			}
			catch (IOException e) {
			}
			raf = null;
		}
	}

}
