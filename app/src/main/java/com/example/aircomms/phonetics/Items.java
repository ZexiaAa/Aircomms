package com.example.aircomms.phonetics;

public class Items {
     String character, telephony, phonic;


    public Items (String character, String telephony, String phonic){

        this.character = character;
        this.telephony = telephony;
        this.phonic = phonic;
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
