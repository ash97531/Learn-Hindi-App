package com.example.miwokapp;

public class word {

    //default translation for the word
    private String mdefaulttranslation;

    //miwok translation for the word
    private String mmiwoktranslation;

    //image of the word
    private int mimageid;

    //visibility
    private int visibility=0;

    //audio file
    private int maudioid;


    public word(String defaulttranslation, String miwoktranslation, int imageid, int audioid){
        mdefaulttranslation = defaulttranslation;
        mmiwoktranslation = miwoktranslation;
        mimageid = imageid;
        maudioid=audioid;
    }

    public word(String defaulttranslation, String miwoktranslation, int audioid){
        mdefaulttranslation = defaulttranslation;
        mmiwoktranslation = miwoktranslation;
        visibility=8;
        maudioid=audioid;
    }

    //get default translation
    public String getDefaultTranslation(){
        return mdefaulttranslation;
    }

    //get miwok translation
    public String getMiwokTranslation(){
        return mmiwoktranslation;
    }

    public int getImage(){return mimageid;}

    public int getVisibility(){return visibility;}

    public int getAudio(){return maudioid;}

    
}
