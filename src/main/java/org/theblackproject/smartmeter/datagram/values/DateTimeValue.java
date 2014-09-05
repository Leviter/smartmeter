package org.theblackproject.smartmeter.datagram.values;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeValue extends Value<DateTime> {
	@Override
	public void setValue(String value) {
		DateTimeZone.setDefault(DateTimeZone.UTC);

		DateTimeFormatter formatter = DateTimeFormat.forPattern("YYMMddHHmmss");
		this.value = DateTime.parse(value, formatter);
	}
}
