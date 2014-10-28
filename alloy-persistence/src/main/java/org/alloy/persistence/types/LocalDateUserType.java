package org.alloy.persistence.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.alloy.metal.utilities._Date;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

public class LocalDateUserType implements UserType, Serializable {
	private static final long serialVersionUID = -6281660793385826867L;
	private static final int[] SQL_TYPES = new int[] { Types.DATE };

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return LocalDate.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		LocalDate dtx = (LocalDate) x;
		LocalDate dty = (LocalDate) y;
		return dtx.equals(dty);
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object timestamp = StandardBasicTypes.DATE.nullSafeGet(resultSet, names, session, owner);
		if (timestamp == null) {
			return null;
		}
		Date date = (Date) timestamp;
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			StandardBasicTypes.DATE.nullSafeSet(preparedStatement, null, index, session);
		} else {
			LocalDate ld = ((LocalDate) value);
			Date time = _Date.toDate(ld);
			StandardBasicTypes.DATE.nullSafeSet(preparedStatement, time, index, session);
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