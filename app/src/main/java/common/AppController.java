package common;

import android.app.Application;
import android.graphics.Typeface;

import com.google.gson.Gson;

import org.json.JSONObject;

import model.LoginModel;
import model.SettingsModel;
import model.StoreModel;
import network.WebApiCall;
import utils.Validation;

/**
 * Created by Ashish.Kumar on 17-05-2018.
 */

public class AppController extends Application {
    PrefManager prefManager;
    Typeface detailsFont,headingFont;
    WebApiCall apicall;
    Validation validation;
    LoginModel loginmodel=null;
    boolean isUserLoggedIn=false;
    StoreModel  selectedStore=null;
    String rememberId="";
    String rememberPassword="";
    SettingsModel settingsModel;
    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(this);
        detailsFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "detailsfont.otf");
        headingFont= Typeface.createFromAsset(getApplicationContext().getAssets(), "font.ttf");
        validation=new Validation(this);
        apicall=new WebApiCall(this);
        rememberId=prefManager.getRememberId();
        rememberPassword=prefManager.getRememberPassword();
        isUserLoggedIn=prefManager.isUserLoggedIn();
        if (isUserLoggedIn) {
            String userProfile = prefManager.getUserProfile();

            String settings=prefManager.getSettings();
            if (userProfile.length() > 0) {
                Gson gson = new Gson();
                loginmodel = gson.fromJson(userProfile, LoginModel.class);
            }
            String selectedstore=prefManager.getSelectedStore(Integer.toString(loginmodel.getUserId()));
            if(selectedstore.length()>0)
            {
                Gson gson = new Gson();
                selectedStore = gson.fromJson(selectedstore, StoreModel.class);
            }
            if(settings.length()>0)
            {
                Gson gson = new Gson();
                settingsModel= gson.fromJson(settings, SettingsModel.class);
            }else{
                settingsModel=new SettingsModel();
                updateSettingModel(settingsModel);
            }
        }else{
            settingsModel=new SettingsModel();
            updateSettingModel(settingsModel);
        }
    }

    public SettingsModel getSettingsModel() {
        return settingsModel;
    }

    public Typeface getHeadingFont() {
        return headingFont;
    }

    public PrefManager getPrefManager() {
        return prefManager;
    }

    public WebApiCall getApicall() {
        return apicall;
    }

    public Validation getValidation() {
        return validation;
    }
    //    public Typeface getDetailsFont() {
//        return detailsFont;
//    }
    public void setLogin(boolean login) {
        prefManager.setUserLoggedIn(login);
        isUserLoggedIn = login;
    }

    public void updateSettingModel(SettingsModel model) {
        this.settingsModel = model;
        Gson gson = new Gson();
        String settings = gson.toJson(model);
        prefManager.setSettings(settings);
    }

    public void setSelectedStore(StoreModel model,String userId) {
        this.selectedStore = model;
        Gson gson = new Gson();
        String selectedStore = gson.toJson(model);
        prefManager.setSelectedStore(selectedStore,userId);
    }

    public StoreModel  getSelectedStore() {
        String selectedstore=prefManager.getSelectedStore(Integer.toString(loginmodel.getUserId()));
        if(selectedstore.length()>0)
        {
            Gson gson = new Gson();
            selectedStore = gson.fromJson(selectedstore, StoreModel.class);
        }
        return selectedStore;
    }

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public LoginModel getLoginmodel() {
        return loginmodel;
    }
    public void setLoginmodel(LoginModel loginmodel) {
        this.loginmodel = loginmodel;
        Gson gson = new Gson();
        String loginDetailsString = gson.toJson(loginmodel);
        prefManager.setUserProfile(loginDetailsString);
    }

    public void logout() {
        setLogin(false);
        updateSettingModel(new SettingsModel());
    }

}
