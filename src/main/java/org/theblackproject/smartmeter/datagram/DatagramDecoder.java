package org.theblackproject.smartmeter.datagram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;
import org.theblackproject.smartmeter.datagram.values.ByteValue;
import org.theblackproject.smartmeter.datagram.values.DateTimeValue;
import org.theblackproject.smartmeter.datagram.values.DoubleUnitValue;
import org.theblackproject.smartmeter.datagram.values.DoubleValue;
import org.theblackproject.smartmeter.datagram.values.IntegerUnitValue;
import org.theblackproject.smartmeter.datagram.values.IntegerValue;
import org.theblackproject.smartmeter.datagram.values.StringValue;
import org.theblackproject.smartmeter.datagram.values.UnitValue;
import org.theblackproject.smartmeter.datagram.values.Value;
import org.theblackproject.smartmeter.gson.DateTimeTypeConverter;
import org.theblackproject.smartmeter.model.Datagram;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DatagramDecoder {

	protected static final String REGEX_ID = "/(.*)";
	protected static final String REGEX_NUMBER_OF_DEVICES = "0-1:24.1.0\\((\\d*)\\)";

	protected static final String REGEX_ELECTRICITY_METER_ID = "0-0:96.1.1\\((.*)\\)";
	protected static final String REGEX_ELECTRICITY_USED_LOW_TOTAL = "1-0:1.8.1\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_USED_HIGH_TOTAL = "1-0:1.8.2\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL = "1-0:2.8.1\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL = "1-0:2.8.2\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_CURRENT_USAGE = "1-0:1.7.0\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_CURRENT_PRODUCTION = "1-0:2.7.0\\((\\d{5}.?\\d{3})\\*(.*)\\)";
	protected static final String REGEX_ELECTRICITY_TARIFF = "0-0:96.14.0\\((\\d)\\)";
	protected static final String REGEX_ELECTRICITY_MAXIMUM_CURRENT = "0-0:17.0.0\\((\\d*)\\*(.+)\\)";
	protected static final String REGEX_ELECTRICITY_SWITCH_POSITION = "0-0:96.3.10\\((\\d)\\)";
	protected static final String REGEX_ELECTRICITY_MESSAGE_NUMBER = "0-0:96.13.1\\((\\d*)\\)";
	protected static final String REGEX_ELECTRICITY_MESSAGE_TEXT = "0-0:96.13.0\\((.*)\\)";

	protected static final String REGEX_GAS_METER_ID = "0-1:96.1.0\\((.*)\\)";
	protected static final String REGEX_GAS_TIMESTAMP = "0-1:24.3.0\\((\\d{12})\\)\\(.*\\)";
	protected static final String REGEX_GAS_TOTAL_USED = "^\\((\\d{5}.?\\d{3})\\)";
	protected static final String REGEX_GAS_VALVE = "0-1:24.4.0\\((\\d)\\)";

	private static Map<Pattern, DatagramValueMapper> patterns = new HashMap<>();

	static {
		patterns.put(Pattern.compile(REGEX_ID), new DatagramValueMapper(StringValue.class, "id"));
		patterns.put(Pattern.compile(REGEX_NUMBER_OF_DEVICES), new DatagramValueMapper(ByteValue.class, "numberOfDevices"));

		patterns.put(Pattern.compile(REGEX_ELECTRICITY_METER_ID), new DatagramValueMapper(StringValue.class, "electricity.meterId"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_USED_LOW_TOTAL), new DatagramValueMapper(DoubleUnitValue.class, "electricity.totalUsedLow"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_USED_HIGH_TOTAL), new DatagramValueMapper(DoubleUnitValue.class, "electricity.totalUsedHigh"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL), new DatagramValueMapper(DoubleUnitValue.class, "electricity.totalProducedLow"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL), new DatagramValueMapper(DoubleUnitValue.class, "electricity.totalProducedHigh"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_CURRENT_USAGE), new DatagramValueMapper(DoubleUnitValue.class, "electricity.currentUsage"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_CURRENT_PRODUCTION), new DatagramValueMapper(DoubleUnitValue.class, "electricity.currentProduction"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_TARIFF), new DatagramValueMapper(ByteValue.class, "electricity.tariff"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_MAXIMUM_CURRENT), new DatagramValueMapper(IntegerUnitValue.class, "electricity.maximumCurrent"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_SWITCH_POSITION), new DatagramValueMapper(ByteValue.class, "electricity.switchPosition"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_MESSAGE_NUMBER), new DatagramValueMapper(IntegerValue.class, "electricity.messageNumber"));
		patterns.put(Pattern.compile(REGEX_ELECTRICITY_MESSAGE_TEXT), new DatagramValueMapper(StringValue.class, "electricity.messageText"));

		patterns.put(Pattern.compile(REGEX_GAS_METER_ID), new DatagramValueMapper(StringValue.class, "gas.meterId"));
		patterns.put(Pattern.compile(REGEX_GAS_TIMESTAMP), new DatagramValueMapper(DateTimeValue.class, "gas.timestamp"));
		patterns.put(Pattern.compile(REGEX_GAS_TOTAL_USED), new DatagramValueMapper(DoubleValue.class, "gas.totalUsage"));
		patterns.put(Pattern.compile(REGEX_GAS_VALVE), new DatagramValueMapper(ByteValue.class, "gas.valvePosition"));
	}

	public void parse(List<String> datagram, Datagram output) {
		for (String lineInDatagram : datagram) {
			for(Map.Entry entry : patterns.entrySet()) {
				Pattern pattern = (Pattern) entry.getKey();
				DatagramValueMapper valueMapper = (DatagramValueMapper) entry.getValue();

				Matcher matcher = pattern.matcher(lineInDatagram);
				if (matcher.find()) {
					try {
						Class<? extends Value> valueClass = valueMapper.getValue();
						String setter = valueMapper.getSetter();

						Value value = valueClass.newInstance();
						value.setValue(matcher.group(1));

						if (value instanceof UnitValue) {
							((UnitValue) value).setUnit(matcher.group(2));
						}

						try {
							PropertyUtils.setNestedProperty(output, setter, value);
						} catch (InvocationTargetException | NoSuchMethodException e) {
							log.error("Could not call method [" + setter + "]");
						}

					} catch (InstantiationException | IllegalAccessException e) {
						log.error("Could not create object for class " + valueMapper.getValue());
					}
				}
			}
		}
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

		Gson gson = new GsonBuilder().
				registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).
				setPrettyPrinting().
				create();
		String json = gson.toJson(datagram);

		log.info("\n=========================================================\n" +
				new Gson().toJson(json) +
				"\n=========================================================");

		System.out.println(json);
	}
}