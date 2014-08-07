package org.theblackproject.smartmeter.datagram.values;

public class StringValue extends Value<String> {
	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
