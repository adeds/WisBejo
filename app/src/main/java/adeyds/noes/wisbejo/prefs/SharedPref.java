package adeyds.noes.wisbejo.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    public static final String SP_APP = "SP_APP";
    public static final String SP_LOGED = "LOGIN";
    public static final String SP_PHOTO_URL = "PHOTO";
    public static final String SP_EMAIL= "EMAIL";


    public SharedPref(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public boolean getSpLoged(){
        return sp.getBoolean(SP_LOGED,false);
    }

    public String getSpPhotoUrl() {
        return sp.getString(SP_PHOTO_URL, SP_PHOTO_URL);
    }

    public String getSpEmail(){
        return sp.getString(SP_EMAIL, SP_EMAIL);
    }
}
