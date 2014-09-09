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
				{DatagramDecoder.REGEX_ID, "This is not correct. It should start with a slash!", false},
				{DatagramDecoder.REGEX_ID, "/Anything goes here...", true},
				{DatagramDecoder.REGEX_ID, "/", true}
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
