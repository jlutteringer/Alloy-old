package org.alloy.metal.logging;

import java.io.OutputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LogOutputStream extends OutputStream {
	/** The logger where to log the written bytes. */
	private Logger logger;

	/** The level. */
	private Level level;

	/** The internal memory for the written bytes. */
	private String mem;

	/**
	* Creates a new log output stream which logs bytes to the specified logger with the specified
	* level.
	*
	* @param logger the logger where to log the written bytes
	* @param level the level
	*/
	public LogOutputStream(Logger logger, Level level) {
		setLogger(logger);
		setLevel(level);
		mem = "";
	}

	/**
	* Sets the logger where to log the bytes.
	*
	* @param logger the logger
	*/
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	* Returns the logger.
	*
	* @return DOCUMENT ME!
	*/
	public Logger getLogger() {
		return logger;
	}

	/**
	* Sets the logging level.
	*
	* @param level DOCUMENT ME!
	*/
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	* Returns the logging level.
	*
	* @return DOCUMENT ME!
	*/
	public Level getLevel() {
		return level;
	}

	/**
	* Writes a byte to the output stream. This method flushes automatically at the end of a line.
	*
	* @param b DOCUMENT ME!
	*/
	@Override
	public void write(int b) {
		byte[] bytes = new byte[1];
		bytes[0] = (byte) (b & 0xff);
		mem = mem + new String(bytes);

		if (mem.endsWith("\n")) {
			mem = mem.substring(0, mem.length() - 1);
			flush();
		}
	}

	/**
	* Flushes the output stream.
	*/
	@Override
	public void flush() {
		logger.log(level, mem);
		mem = "";
	}
}