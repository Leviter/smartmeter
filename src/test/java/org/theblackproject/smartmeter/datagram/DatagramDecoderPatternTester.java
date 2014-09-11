package org.theblackproject.smartmeter.datagram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;


@RunWith(Parameterized.class)
public class DatagramDecoderPatternTester {

	@Parameterized.Parameters(name = "{index}: \"{1}\" - {2}")
	public static Iterable data() {
		return Arrays.asList(new Object[][]{
				{DatagramDecoder.REGEX_ID, "", false},
				{DatagramDecoder.REGEX_ID, "This is not correct. It should start with a slash!", false},
				{DatagramDecoder.REGEX_ID, "/Anything goes here...", true},
				{DatagramDecoder.REGEX_ID, "/", true},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "", false},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "nope", false},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "0-1:24.1.0(3)", true},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "0-1:24.1.0(9218734)", true},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "0-1:24.1.0(nope)", false},
				{DatagramDecoder.REGEX_NUMBER_OF_DEVICES, "0-1:24.1.0()", true},
				{DatagramDecoder.REGEX_ELECTRICITY_METER_ID, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_METER_ID, "this is incorrect", false},
				{DatagramDecoder.REGEX_ELECTRICITY_METER_ID, "0-0:96.1.1(anything goes)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_METER_ID, "0-0:96.1.1(1234)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_METER_ID, "0-0:96.1.1()", true},

				{DatagramDecoder.REGEX_ELECTRICITY_USED_LOW_TOTAL, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_LOW_TOTAL, "1-0:1.8.1(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_LOW_TOTAL, "1-0:1.8.1(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_LOW_TOTAL, "1-0:1.8.1(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_HIGH_TOTAL, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_HIGH_TOTAL, "1-0:1.8.2(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_HIGH_TOTAL, "1-0:1.8.2(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_USED_HIGH_TOTAL, "1-0:1.8.2(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL, "1-0:2.8.1(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL, "1-0:2.8.1(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_LOW_TOTAL, "1-0:2.8.1(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL, "1-0:2.8.2(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL, "1-0:2.8.2(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_PRODUCED_HIGH_TOTAL, "1-0:2.8.2(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_USAGE, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_USAGE, "1-0:1.7.0(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_USAGE, "1-0:1.7.0(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_USAGE, "1-0:1.7.0(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_PRODUCTION, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_PRODUCTION, "1-0:2.7.0(00123.456*kWh)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_PRODUCTION, "1-0:2.7.0(0.0*mph)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_CURRENT_PRODUCTION, "1-0:2.7.0(00000.000*mph)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_TARIFF, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_TARIFF, "0-0:96.14.0(1)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_TARIFF, "0-0:96.14.0(a)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_TARIFF, "0-0:96.14.0()", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MAXIMUM_CURRENT, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MAXIMUM_CURRENT, "0-0:17.0.0(999*A)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_MAXIMUM_CURRENT, "0-0:17.0.0(999*)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_SWITCH_POSITION, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_SWITCH_POSITION, "0-0:96.3.10(1)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_SWITCH_POSITION, "0-0:96.3.10(a)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_SWITCH_POSITION, "0-0:96.3.10()", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_NUMBER, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_NUMBER, "0-0:96.13.1(10)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_NUMBER, "0-0:96.13.1()", true},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_NUMBER, "0-0:96.13.1(monkey)", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_TEXT, "", false},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_TEXT, "0-0:96.13.0(A text that could be a message!)", true},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_TEXT, "0-0:96.13.0()", true},
				{DatagramDecoder.REGEX_ELECTRICITY_MESSAGE_TEXT, "0-0:96.13.0(1274091827409127409187204)", true},
				{DatagramDecoder.REGEX_GAS_METER_ID, "", false},
				{DatagramDecoder.REGEX_GAS_METER_ID, "0-1:96.1.0(3238313031453631373038389930337131)", true},
				{DatagramDecoder.REGEX_GAS_METER_ID, "0-1:96.1.0(monkey testing)", true},
				{DatagramDecoder.REGEX_GAS_METER_ID, "0-1:96.1.0()", true},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "", false},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0(120517020000)(08)(60)(1)(0-1:24.2.1)(m3)", true},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0(1205170200)(08)(60)(1)(0-1:24.2.1)(m3)", false},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0(invalid value)(08)(60)(1)(0-1:24.2.1)(m3)", false},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0(120517020000)(08)(60)(1)(0-1:24.2.1)(mph)", true},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0(120517020000)(we do not care what is over here!)", true},
				{DatagramDecoder.REGEX_GAS_TIMESTAMP, "0-1:24.3.0()(08)(60)(1)(0-1:24.2.1)(m3)", false},
				{DatagramDecoder.REGEX_GAS_TOTAL_USED, "", false},
				{DatagramDecoder.REGEX_GAS_TOTAL_USED, "(00123.456)", true},
				{DatagramDecoder.REGEX_GAS_TOTAL_USED, "(0.0)", false},
				{DatagramDecoder.REGEX_GAS_TOTAL_USED, "(00000.000)", true},
				{DatagramDecoder.REGEX_GAS_VALVE, "", false},
				{DatagramDecoder.REGEX_GAS_VALVE, "0-1:24.4.0(1)", true},
				{DatagramDecoder.REGEX_GAS_VALVE, "0-1:24.4.0(a)", false},
				{DatagramDecoder.REGEX_GAS_VALVE, "0-1:24.4.0()", false}
		});
	}

	private String regex;
	private String input;
	private Boolean expectedToMatch;

	public DatagramDecoderPatternTester(String regex, String input, Boolean expectedToMatch) {
		this.regex = regex;
		this.input = input;
		this.expectedToMatch = expectedToMatch;
	}

	@Test
	public void theDirectionalPatternMatchingOnTheInputShouldReturnWhatIsExpected() {
		Matcher matcher = Pattern.compile(regex).matcher(input);
		assertThat(matcher.matches()).isEqualTo(expectedToMatch);
	}
}
