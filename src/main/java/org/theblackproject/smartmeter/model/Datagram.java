package org.theblackproject.smartmeter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.theblackproject.smartmeter.datagram.values.ByteValue;
import org.theblackproject.smartmeter.datagram.values.StringValue;

@Getter
@Setter
@ToString
public class Datagram {

	private StringValue id;
	private ByteValue numberOfDevices;

	private Electricity electricity = new Electricity();
	private Gas gas = new Gas();
}
