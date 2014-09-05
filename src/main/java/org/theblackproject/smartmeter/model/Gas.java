package org.theblackproject.smartmeter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.theblackproject.smartmeter.datagram.values.ByteValue;
import org.theblackproject.smartmeter.datagram.values.DateTimeValue;
import org.theblackproject.smartmeter.datagram.values.DoubleValue;
import org.theblackproject.smartmeter.datagram.values.StringValue;

@Getter
@Setter
@ToString
public class Gas {

	private StringValue meterId;

	private DateTimeValue timestamp;

	private DoubleValue totalUsage;

	private ByteValue valvePosition;
}
