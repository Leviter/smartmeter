package org.theblackproject.smartmeter.datagram.values;

public class DoubleUnitValue extends UnitValue<Double> {
	@Override
	public void setValue(String value) {
		this.value = Double.parseDouble(value);
	}
}
