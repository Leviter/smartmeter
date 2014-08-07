INPUT
=====

/KMP5 ZABF001587315111KMP                           lijkt de identificatie te zijn van Kamstrup
0-0:96.1.1(205C4D246333034353537383234323121)       serienummer meter in hexadecimale ascii code
1-0:1.8.1(00185.000*kWh)                            Totaal verbruik tarief 1 (nacht)
1-0:1.8.2(00084.000*kWh)                            Totaal verbruik tarief 2 (dag)
1-0:2.8.1(00013.000*kWh)                            Totaal geleverd tarief 1 (nacht)
1-0:2.8.2(00019.000*kWh)                            Totaal geleverd tarief 2 (dag)
0-0:96.14.0(0001)                                   Actuele tarief (1)
1-0:1.7.0(0000.98*kW)                               huidig verbruik
1-0:2.7.0(0000.00*kW)                               huidige teruglevering
0-0:17.0.0(999*A)                                   maximum stroom per fase
0-0:96.3.10(1)                                      stand van de schakelaar
0-0:96.13.1()                                       bericht numeriek
0-0:96.13.0()                                       bericht tekst
0-1:24.1.0(3)                                       andere apparaten op de M-Bus
0-1:96.1.0(3238313031453631373038389930337131)      identificatie van de gasmeter
0-1:24.3.0(120517020000)(08)(60)(1)(0-1:24.2.1)(m3) tijd van de laatste gas meting (120517020000 = 17 mei 2012 2uur
(00124.477)                                         Verbruikte hoeveelheid gas
0-1:24.4.0(1)                                       stand gasklep?
!                                                   Afsluiter


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