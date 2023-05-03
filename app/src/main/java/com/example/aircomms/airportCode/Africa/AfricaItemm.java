package com.example.aircomms.airportCode.Africa;

public class AfricaItemm {
        String _airport;
        String _icao;
        String _location;

        public AfricaItemm( String _airport, String _icao, String _location){

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
