package com.example.aircomms.airlineCode.Asia;

public class AsiaItem {
    String asia_callsign;
    int asia_logo;
    String asia_icao;

    public AsiaItem (String asia_callsign, String asia_icao, int asia_logo){

        this.asia_callsign =asia_callsign;
        this.asia_icao = asia_icao;
        this.asia_logo = asia_logo;
    }


    public String getAsia_callsign() {
        return asia_callsign;
    }


    public int getAsia_logo() {
        return asia_logo;
    }

    public String getAsia_icao() {
        return asia_icao;
    }

    public void setAsia_callsign(String asia_callsign) {
        this.asia_callsign = asia_callsign;
    }

    public void setAsia_icao(String asia_icao) {
        this.asia_icao = asia_icao;
    }

    public void setAsia_logo(int asia_logo) {
        this.asia_logo = asia_logo;
    }
}
