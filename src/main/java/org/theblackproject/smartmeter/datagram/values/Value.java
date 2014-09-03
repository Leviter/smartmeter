package org.theblackproject.smartmeter.datagram.values;

import lombok.Getter;

public abstract class Value<T> {

	@Getter
	protected T value;

	public abstract void setValue(String value);

	public String toString() {
		return "value=" + value;
	}
}
