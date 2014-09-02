package org.theblackproject.smartmeter.datagram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.theblackproject.smartmeter.datagram.values.Value;

@AllArgsConstructor
@Getter
public class DatagramValueMapper {

	private Class<? extends Value> value;
	private String setter;
}
