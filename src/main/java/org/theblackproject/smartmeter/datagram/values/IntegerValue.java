package org.theblackproject.smartmeter.datagram.values;

public class IntegerValue extends Value<Integer> {
	@Override
	public void setValue(String value) {
		if (!"".equals(value)) {
			this.value = Integer.parseInt(value, 10);
		}
	}
}
