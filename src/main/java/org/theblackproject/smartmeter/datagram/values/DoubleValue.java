package org.theblackproject.smartmeter.datagram.values;

public class DoubleValue extends Value<Double> {
	@Override
	public void setValue(String value) {
		this.value = Double.parseDouble(value);
	}
}
