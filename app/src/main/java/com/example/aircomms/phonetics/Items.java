package com.example.aircomms.phonetics;

public class Items {
     String character, telephony, phonic;
    int audioResource;

    public Items (String character, String telephony, String phonic, int audioResource){

        this.character = character;
        this.telephony = telephony;
        this.phonic = phonic;
        this.audioResource = audioResource;
    }

    public int getAudioResource() {
        return audioResource;
    }

    public String getCharacter(){
        return character;
    }

    public String getTelephony(){
        return telephony;
    }

    public String getPhonic () {
       return phonic;
   }

}
