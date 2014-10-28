package org.sqlite.jdbc4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.util.FileCopyUtils;
import org.sqlite.core.CoreStatement;
import org.sqlite.jdbc3.JDBC3ResultSet;

public class JDBC4ResultSet extends JDBC3ResultSet implements ResultSet, ResultSetMetaData {
	public JDBC4ResultSet(CoreStatement stmt) {
		super(stmt);
	}

	// JDBC 4
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {

	}

	@Override
	public int getHoldability() throws SQLException {
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public void updateNString(int columnIndex, String nString)
			throws SQLException {

	}

	@Override
	public void updateNString(String columnLabel, String nString)
			throws SQLException {

	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {

	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {

	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {

	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {

	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {

	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {

	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {

	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {

	}

	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		return null;
	}

	protected SQLException unused() {
		return new SQLException("not implemented by SQLite JDBC driver");
	}

	// ResultSet ////////////////////////////////////////////////////

	@Override
	public Array getArray(int i)
			throws SQLException {
		throw unused();
	}

	@Override
	public Array getArray(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public InputStream getAsciiStream(int col)
			throws SQLException {
		throw unused();
	}

	@Override
	public InputStream getAsciiStream(String col)
			throws SQLException {
		throw unused();
	}

//    public BigDecimal getBigDecimal(int col)
//        throws SQLException { throw unused(); }
	@Override
	public BigDecimal getBigDecimal(int col, int s)
			throws SQLException {
		throw unused();
	}

//    public BigDecimal getBigDecimal(String col)
//        throws SQLException { throw unused(); }
	@Override
	public BigDecimal getBigDecimal(String col, int s)
			throws SQLException {
		throw unused();
	}

	@Override
	public Blob getBlob(int col)
			throws SQLException {
		throw unused();
	}

	@Override
	public Blob getBlob(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public Clob getClob(int col) throws SQLException {
		return new PassThroughClob(db.column_text(stmt.pointer, markCol(col)));
	}

	@Override
	public Clob getClob(String col) throws SQLException {
		return new PassThroughClob(getString(findColumn(col)));
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getObject(int col, Map map)
			throws SQLException {
		throw unused();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getObject(String col, Map map)
			throws SQLException {
		throw unused();
	}

	@Override
	public Ref getRef(int i)
			throws SQLException {
		throw unused();
	}

	@Override
	public Ref getRef(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public InputStream getUnicodeStream(int col)
			throws SQLException {
		throw unused();
	}

	@Override
	public InputStream getUnicodeStream(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public URL getURL(int col)
			throws SQLException {
		throw unused();
	}

	@Override
	public URL getURL(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public boolean last() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public boolean previous() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public void afterLast() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public void beforeFirst() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public boolean first() throws SQLException {
		throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
	}

	@Override
	public void cancelRowUpdates()
			throws SQLException {
		throw unused();
	}

	@Override
	public void deleteRow()
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateArray(int col, Array x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateArray(String col, Array x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateAsciiStream(int col, InputStream x, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateAsciiStream(String col, InputStream x, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBigDecimal(int col, BigDecimal x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBigDecimal(String col, BigDecimal x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBinaryStream(int c, InputStream x, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBinaryStream(String c, InputStream x, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBlob(int col, Blob x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBlob(String col, Blob x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBoolean(int col, boolean x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBoolean(String col, boolean x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateByte(int col, byte x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateByte(String col, byte x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBytes(int col, byte[] x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateBytes(String col, byte[] x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateCharacterStream(int c, Reader x, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateCharacterStream(String c, Reader r, int l)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateClob(int col, Clob x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateClob(String col, Clob x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateDate(int col, Date x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateDate(String col, Date x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateDouble(int col, double x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateDouble(String col, double x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateFloat(int col, float x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateFloat(String col, float x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateInt(int col, int x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateInt(String col, int x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateLong(int col, long x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateLong(String col, long x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateNull(int col)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateNull(String col)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateObject(int c, Object x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateObject(int c, Object x, int s)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateObject(String col, Object x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateObject(String c, Object x, int s)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateRef(int col, Ref x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateRef(String c, Ref x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateRow()
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateShort(int c, short x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateShort(String c, short x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateString(int c, String x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateString(String c, String x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateTime(int c, Time x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateTime(String c, Time x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateTimestamp(int c, Timestamp x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void updateTimestamp(String c, Timestamp x)
			throws SQLException {
		throw unused();
	}

	@Override
	public void refreshRow()
			throws SQLException {
		throw unused();
	}

	static class PassThroughClob implements Clob {

		private String content;

		private Reader characterStream;

		private InputStream asciiStream;

		private long contentLength;

		public PassThroughClob(String content) {
			this.content = content;
			this.contentLength = content.length();
		}

		public PassThroughClob(Reader characterStream, long contentLength) {
			this.characterStream = characterStream;
			this.contentLength = contentLength;
		}

		public PassThroughClob(InputStream asciiStream, long contentLength) {
			this.asciiStream = asciiStream;
			this.contentLength = contentLength;
		}

		@Override
		public long length() throws SQLException {
			return this.contentLength;
		}

		@Override
		public Reader getCharacterStream() throws SQLException {
			try {
				if (this.content != null) {
					return new StringReader(this.content);
				}
				else if (this.characterStream != null) {
					return this.characterStream;
				}
				else {
					return new InputStreamReader(this.asciiStream, "US-ASCII");
				}
			} catch (UnsupportedEncodingException ex) {
				throw new SQLException("US-ASCII encoding not supported: " + ex);
			}
		}

		@Override
		public InputStream getAsciiStream() throws SQLException {
			try {
				if (this.content != null) {
					return new ByteArrayInputStream(this.content.getBytes("US-ASCII"));
				}
				else if (this.characterStream != null) {
					String tempContent = FileCopyUtils.copyToString(this.characterStream);
					return new ByteArrayInputStream(tempContent.getBytes("US-ASCII"));
				}
				else {
					return this.asciiStream;
				}
			} catch (UnsupportedEncodingException ex) {
				throw new SQLException("US-ASCII encoding not supported: " + ex);
			} catch (IOException ex) {
				throw new SQLException("Failed to read stream content: " + ex);
			}
		}

		@Override
		public Reader getCharacterStream(long pos, long length) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Writer setCharacterStream(long pos) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public OutputStream setAsciiStream(long pos) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getSubString(long pos, int length) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public int setString(long pos, String str) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public int setString(long pos, String str, int offset, int len) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public long position(String searchstr, long start) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public long position(Clob searchstr, long start) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void truncate(long len) throws SQLException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void free() throws SQLException {
			// no-op
		}

	}
}