package com.example.android.miwok;

/**
 * It contains default and miwok translations
 * Created by vaibhav on 03-11-2017.
 */

public class Word {
    private String mDefTrans,mMiwTrans;
    private  int  msoundId;
    private int mImgResourceId=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    public Word(String Def,String Miw,int soundId)
    {
        mDefTrans=Def;
        mMiwTrans=Miw;
        msoundId=soundId;
    }
    public Word(String Def,String Miw,int resId,int soundId)
    {
        mDefTrans=Def;
        mMiwTrans=Miw;
        mImgResourceId=resId;
        msoundId=soundId;
    }
    public String getDefaultTranslation()
    {
        return mDefTrans;
    }
    public String getMiwokTranslation(){
        return mMiwTrans;
    }
    public int getImageResourceId()
    {
        return mImgResourceId;
    }
    public boolean isImage()
    {
        return mImgResourceId!=NO_IMAGE_PROVIDED;
    }
    public int getsoundId(){return msoundId;}
}
