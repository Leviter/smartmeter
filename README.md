INPUT
=====

	/KMP5 ZABF001587315111KMP                           Meter identification
	0-0:96.1.1(205C4D246333034353537383234323121)       Serial number of the electricity meter
	1-0:1.8.1(00185.000*kWh)                            Total used power (low)
	1-0:1.8.2(00084.000*kWh)                            Total used power (high)
	1-0:2.8.1(00013.000*kWh)                            Total produced power (low)
	1-0:2.8.2(00019.000*kWh)                            Total produced power (high)
	0-0:96.14.0(0001)                                   Current tariff (low / high)
	1-0:1.7.0(0000.98*kW)                               Current electricity use
	1-0:2.7.0(0000.00*kW)                               Current electricity production
	0-0:17.0.0(999*A)                                   Maximum current
	0-0:96.3.10(1)                                      Position of the switch
	0-0:96.13.1()                                       Message number
	0-0:96.13.0()                                       Message text
	0-1:24.1.0(3)                                       Other devices on the M-Bus
	0-1:96.1.0(3238313031453631373038389930337131)      Serial number of the gas meter
	0-1:24.3.0(120517020000)(08)(60)(1)(0-1:24.2.1)(m3) Timestamp of the last gas measurement (120517020000 = May 17 2012 at 2 o'clock
	(00124.477)                                         Used m3 gas
	0-1:24.4.0(1)                                       Position of the gas valve
	!                                                   End of message


OUTPUT
======

	{
		"meter" : "KMP5 ZABF001587315111KMP",
		"electricity" : {
			"meter" : "205C4D246333034353537383234323121",
			"totalUsageLow" : {
				"value" : 185.000,
				"unit" : "kWh"
			},
			...
		},
		"gas" : {
			...
		}
	}