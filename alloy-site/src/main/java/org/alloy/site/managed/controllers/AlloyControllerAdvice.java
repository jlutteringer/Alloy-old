package org.alloy.site.managed.controllers;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class AlloyControllerAdvice {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Instant.class, new InstantBinder());
	}

	public class InstantBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			this.setValue(DateTimeFormatter.ISO_INSTANT.parse(text, Instant::from));
		}

		@Override
		public String getAsText() {
			Instant instant = (Instant) this.getValue();
			return DateTimeFormatter.ISO_INSTANT.format(instant);
		}
	}
}