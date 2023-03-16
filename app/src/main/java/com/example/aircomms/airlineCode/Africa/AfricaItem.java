package com.example.aircomms.airlineCode.Africa;

public class AfricaItem {
    String _callsign;
    int _logo;
    String _icao;

    public AfricaItem (String _callsign, String _icao, int _logo){

        this._callsign = _callsign;
        this._icao = _icao;
        this._logo =_logo;
    }

    public int get_logo() {
        return _logo;
    }


    public String get_callsign() {
        return _callsign;
    }


    public String get_icao() {
        return _icao;
    }
}
