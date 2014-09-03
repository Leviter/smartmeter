package org.theblackproject.smartmeter.datagram.values;

import lombok.Getter;
import lombok.Setter;

public abstract class UnitValue<T> extends Value<T> {

	@Getter
	@Setter
	private String unit;

	public String toString() {
		return "value=" + value + ", " + "unit=" + unit;
	}
}
