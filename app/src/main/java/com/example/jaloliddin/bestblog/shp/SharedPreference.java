package com.example.jaloliddin.bestblog.shp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreferences PREF;
    private static SharedPreferences.Editor EDITOR;
    private static Context CONTEXT;

    private static final String SHARED_PREFERENCE = "SHARED_PREFERENCE";

    private static final String FIRST_TIME_LAUNCH = "FIRST_TIME_LAUNCH";
    private static final String LANG = "LANG";

    private static final String SERVER_REG = "SERVER_REG";

    //when fragment no need  to back exit application
    private static final String BACK_PRESSED = "BACK_PRESSED";

    private static SharedPreference INSTANCE;

    public static SharedPreference getInstance(Context nContext) {
        if (INSTANCE == null) INSTANCE = new SharedPreference(nContext);
        return INSTANCE;
    }

    public SharedPreference(Context nCONTEXT) {
        this.CONTEXT = nCONTEXT;
        PREF = CONTEXT.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        EDITOR = PREF.edit();
    }
    /*****
     *REGISTRATION
     */
    public void setServerReg(boolean isReg){
        EDITOR.putBoolean(SERVER_REG,isReg).commit();
    }
    public boolean isServerReg(){
        return PREF.getBoolean(SERVER_REG,false);
    }


    /*************
     * App Language
     * */
    public  void setLang(String sLang){
        EDITOR.putString(LANG,sLang).commit();
    }
     public String getLang(){
        return PREF.getString(LANG,"");
     }


     /*******
      *
      * //when fragment no need  to back exit application
      *
      ******/
    public void setBackPressed(boolean isBackPressed){
        EDITOR.putBoolean(BACK_PRESSED,isBackPressed).commit();
    }
    public boolean isBackPressed(){
        return PREF.getBoolean(BACK_PRESSED,true);
    }
}
