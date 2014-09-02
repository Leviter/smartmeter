package org.theblackproject.smartmeter.datagram;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.theblackproject.smartmeter.datagram.values.DoubleValue;
import org.theblackproject.smartmeter.datagram.values.StringValue;
import org.theblackproject.smartmeter.datagram.values.Value;
import org.theblackproject.smartmeter.model.Datagram;
import org.theblackproject.smartmeter.model.Electricity;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DatagramDecoder {

	private static final String REGEX_ID = "/(.*)";
	private static final String REGEX_ELECTRICITY_METER_ID = "0-0:96.1.1\\((.*)\\)";
	private static final String REGEX_ELECTRICITY_USAGE_LOW_TOTAL = "1-0:1.8.1\\((.*)\\*kWh\\)";

	private static Map<Pattern, DatagramValueMapper> patterns = new HashMap<>();

	static {
		patterns.put(Pattern.compile(REGEX_ID), new DatagramValueMapper(StringValue.class, "id"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_METER_ID), new DatagramValueMapper(StringValue.class, "electricity.meterId"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_USAGE_LOW_TOTAL), new DatagramValueMapper(DoubleValue.class, "electricity.totalUsageLow"));
	}

	private Datagram datagram;
	private Electricity electricity;

	public void parse(List<String> datagram, Datagram output) {
		for (String lineInDatagram : datagram) {
			for(Map.Entry entry : patterns.entrySet()) {
				Pattern pattern = (Pattern) entry.getKey();
				DatagramValueMapper valueMapper = (DatagramValueMapper) entry.getValue();

				Matcher matcher = pattern.matcher(lineInDatagram);
				if (matcher.find()) {
					log.debug("We have a match! ==> " + matcher.group(1));
					try {
						Class<? extends Value> valueClass = valueMapper.getValue();
						String setter = valueMapper.getSetter();

						Value value = valueClass.newInstance();
						value.setValue(matcher.group(1));

						log.debug("Created an object and this is it... " + value);
						log.debug("Its value is... " + value.getValue());

						try {
							PropertyUtils.setNestedProperty(output, setter, value.getValue());
						} catch (InvocationTargetException | NoSuchMethodException e) {
							e.printStackTrace();
						}

					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		log.debug("Datagram = " + output);
	}

	public static void main(String[] args) {
		final String message = "/KMP5 ZABF001587315111KMP\n" +
				"0-0:96.1.1(205C4D246333034353537383234323121)\n" +
				"1-0:1.8.1(00185.000*kWh)\n" +
				"1-0:1.8.2(00084.000*kWh)\n" +
				"1-0:2.8.1(00013.000*kWh)\n" +
				"1-0:2.8.2(00019.000*kWh)\n" +
				"0-0:96.14.0(0001)\n" +
				"1-0:1.7.0(0000.98*kW)\n" +
				"1-0:2.7.0(0000.00*kW)\n" +
				"0-0:17.0.0(999*A)\n" +
				"0-0:96.3.10(1)\n" +
				"0-0:96.13.1()\n" +
				"0-0:96.13.0()\n" +
				"0-1:24.1.0(3)\n" +
				"0-1:96.1.0(3238313031453631373038389930337131)\n" +
				"0-1:24.3.0(120517020000)(08)(60)(1)(0-1:24.2.1)(m3)\n" +
				"(00124.477)\n" +
				"0-1:24.4.0(1)\n" +
				"!\n";

		DatagramDecoder decoder = new DatagramDecoder();
		Datagram datagram = new Datagram();
		decoder.parse(Arrays.asList(message.split("\n")), datagram);
	}
}