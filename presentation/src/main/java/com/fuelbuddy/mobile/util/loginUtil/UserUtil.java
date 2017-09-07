package com.fuelbuddy.mobile.util.loginUtil;

import com.fuelbuddy.mobile.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;


public class UserUtil {

    private static String FACEBOOK_ID = "id";
    private static String FACEBOOK_NAME = "name";
    private static String FACEBOOK_EMAIL = "email";


    public UserModel populateGoogleUser(GoogleSignInAccount account){
        UserModel googleUser = new UserModel();
        if(account!=null) {
            googleUser.setUserId(account.getId());
            googleUser.setProfileName(account.getDisplayName());
            googleUser.setEmail(account.getEmail());
        }
        return googleUser;
    }


    public UserModel populateFacebookUser(JSONObject facebookJson){
        UserModel googleUser = new UserModel();
        try {
            googleUser.setUserId(facebookJson.getString(FACEBOOK_ID));
            googleUser.setProfileName(facebookJson.getString(FACEBOOK_NAME));
            googleUser.setEmail(facebookJson.getString(FACEBOOK_EMAIL));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googleUser;
    }


}



