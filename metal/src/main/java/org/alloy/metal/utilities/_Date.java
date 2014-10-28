package org.alloy.metal.utilities;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class _Date {
	public static Instant MIN = new Date(Long.MIN_VALUE).toInstant();

	public static Date toDate(Instant instant) {
		BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(BigInteger.valueOf(1000));
		milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(BigInteger.valueOf(1_000_000)));
		return new Date(milis.longValue());
	}

	public static Date toDate(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return toDate(instant);
	}

	public static Date toDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return toDate(instant);
	}

	public static Date toDate(LocalTime localTime) {
		Instant instant = localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
		return toDate(instant);
	}
}