package org.alloy.persistence.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.Date;

import org.alloy.metal.utilities._Date;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

public class InstantUserType implements UserType, Serializable {
	private static final long serialVersionUID = -9142974216234283272L;
	private static final int[] SQL_TYPES = new int[] { Types.TIMESTAMP };

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return Instant.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		Instant dtx = (Instant) x;
		Instant dty = (Instant) y;
		return dtx.equals(dty);
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, names, session, owner);
		if (timestamp == null) {
			return null;
		}
		Date ts = (Date) timestamp;
		return ts.toInstant();
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index, session);
		} else {
			Instant instant = (Instant) value;
			Date timestamp = _Date.toDate(instant);
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, timestamp, index, session);
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