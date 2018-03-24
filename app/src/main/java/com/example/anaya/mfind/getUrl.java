package com.example.anaya.mfind;

/**
 * Created by Anaya on 12-03-2018.
 */

public class getUrl {
    String url = null;
    public String setUrl(String directory)
    {
        this.url = "http://192.168.0.10/M-Find/"+directory;
        return  this.url;
    }
}
