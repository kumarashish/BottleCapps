package common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ashish.Kumar on 16-01-2018.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BottleCapsAdmin";
 public static String Id="Id";
    public static String Password="Password";
    private static final String LoggedIn = "BottleCapsAdminUserLoggedIn";
    private static final String FcmToken = "FcmToken";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String SelectedStore = "SelectedStore";
    private static final String settings = "Settings";
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void clearPreferences() {
        String tokenFcm = getFcmToken();
        boolean isFirstTimeLaunch = isFirstTimeLaunch();
        editor.clear().commit();
        setFcmToken(tokenFcm);
        setFirstTimeLaunch(isFirstTimeLaunch);

    }

    public void setRememberId(String id, String password) {
        editor.putString(Id, id);
        editor.putString(Password, password);
        editor.commit();
    }

    public  String getRememberId() {
        return pref.getString(Id,"");
    }
    public  String getRememberPassword() {
        return pref.getString(Password,"");
    }
    public void setUserLoggedIn(boolean isloggedIn) {
        editor.putBoolean(LoggedIn , isloggedIn);
        editor.commit();
    }

    public void setSelectedStore(String selectedStore,String userId) {
        editor.putString(SelectedStore+""+userId, selectedStore);
        editor.commit();
    }
    public void setSettings(String settings) {
        editor.putString(settings, settings);
        editor.commit();
    }

    public String getSettings() {
        return pref.getString(settings, "");
    }

    public String getSelectedStore(String userId) {
        return pref.getString(SelectedStore+""+userId, "");
    }

    public void setFcmToken(String token) {
        editor.putString(FcmToken , token);
        editor.commit();
    }

    public  String getFcmToken() {
        return  pref.getString(FcmToken,"");
    }

    public boolean isUserLoggedIn()
 {
     return pref.getBoolean(LoggedIn , false);
 }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserProfile(String profile) {
        editor.putString("userProfile",profile);
        editor.commit();
    }
  public String  getUserProfile()
  {
      return  pref.getString("userProfile","");
  }

}
