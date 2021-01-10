package com.haqwat.share;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.haqwat.language.Language;


public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase,"ar"));
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/Cairo-Regular.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/Cairo-Regular.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/Cairo-Regular.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/Cairo-Regular.ttf");

    }
}

