package com.example.aircomms.airportCode;

public class DisplayItem {

    String airportName, airportICAO, airportLocation;

    public DisplayItem(String airportName, String airportICAO, String airportLocation) {
        this.airportName = airportName;
        this.airportICAO = airportICAO;
        this.airportLocation = airportLocation;
    }

    public DisplayItem(String airportName, String airportICAO) {
        this.airportName = airportName;
        this.airportICAO = airportICAO;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportICAO() {
        return airportICAO;
    }

    public String getAirportLocation() {
        return airportLocation;
    }
}
