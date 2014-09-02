package org.theblackproject.smartmeter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Datagram {

	private String id;

	private Electricity electricity = new Electricity();
}
