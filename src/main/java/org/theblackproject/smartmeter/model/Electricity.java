package org.theblackproject.smartmeter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.theblackproject.smartmeter.datagram.values.ByteValue;
import org.theblackproject.smartmeter.datagram.values.DoubleUnitValue;
import org.theblackproject.smartmeter.datagram.values.IntegerUnitValue;
import org.theblackproject.smartmeter.datagram.values.IntegerValue;
import org.theblackproject.smartmeter.datagram.values.StringValue;

@Getter
@Setter
@ToString
public class Electricity {

	private StringValue meterId;

	private DoubleUnitValue totalUsedLow;
	private DoubleUnitValue totalUsedHigh;
	private DoubleUnitValue totalProducedLow;
	private DoubleUnitValue totalProducedHigh;

	private DoubleUnitValue currentUsage;
	private DoubleUnitValue currentProduction;

	private ByteValue tariff;
	private IntegerUnitValue maximumCurrent;
	private ByteValue switchPosition;

	private IntegerValue messageNumber;
	private StringValue messageText;
}
