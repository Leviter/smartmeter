package org.theblackproject.smartmeter.datagram.values;

public class IntegerUnitValue extends UnitValue<Integer> {
	@Override
	public void setValue(String value) {
		this.value = Integer.parseInt(value, 10);
	}
}
