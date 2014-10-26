@TypeDefs({
		@TypeDef(name = "localDateType",
				defaultForType = LocalDate.class,
				typeClass = LocalDateUserType.class),
		@TypeDef(name = "localDateTimeType",
				defaultForType = LocalDateTime.class,
				typeClass = LocalDateTimeUserType.class),
		@TypeDef(name = "localTimeType",
				defaultForType = LocalTime.class,
				typeClass = LocalTimeUserType.class),
		@TypeDef(name = "instantType",
				defaultForType = Instant.class,
				typeClass = InstantUserType.class)
})
package org.alloy.persistence.types;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

