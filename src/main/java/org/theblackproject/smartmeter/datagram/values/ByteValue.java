package org.theblackproject.smartmeter.datagram.values;

public class ByteValue extends Value<Byte> {
	@Override
	public void setValue(String value) {
		this.value = Byte.parseByte(value, 10);
	}
}
