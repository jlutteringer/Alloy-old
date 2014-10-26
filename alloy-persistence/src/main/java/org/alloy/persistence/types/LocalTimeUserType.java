package org.alloy.persistence.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

public class LocalTimeUserType implements UserType, Serializable {
	private static final long serialVersionUID = 9175969299899825153L;
	private static final int[] SQL_TYPES = new int[] { Types.TIME };
	private static final int A_YEAR = 2000;
	private static final int A_MONTH = 1;
	private static final int A_DAY = 1;

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return LocalTime.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		LocalTime dtx = (LocalTime) x;
		LocalTime dty = (LocalTime) y;
		return dtx.equals(dty);
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object timestamp = StandardBasicTypes.TIME.nullSafeGet(resultSet, names, session, owner);
		if (timestamp == null) {
			return null;
		}
		Date time = (Date) timestamp;
		Instant instant = Instant.ofEpochMilli(time.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			StandardBasicTypes.TIME.nullSafeSet(preparedStatement, null, index, session);
		} else {
			LocalTime lt = ((LocalTime) value);
			Instant instant = lt.atDate(LocalDate.of(A_YEAR, A_MONTH, A_DAY)).
					atZone(ZoneId.systemDefault()).toInstant();
			Date time = Date.from(instant);
			StandardBasicTypes.TIME.nullSafeSet(preparedStatement, time, index, session);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object value) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}