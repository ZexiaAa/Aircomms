package com.example.aircomms.airportCode.Australia;

public class AustraliaItemm {
    String _airport;
    String _icao;
    String _location;


    public AustraliaItemm( String _airport, String _icao, String _location){

        this._airport = _airport;
        this._icao = _icao;
        this._location = _location;
    }

    public String get_icao() {
        return _icao;}

    public String get_airport() {
        return _airport;}

    public String get_location() {
        return _location;}
}
