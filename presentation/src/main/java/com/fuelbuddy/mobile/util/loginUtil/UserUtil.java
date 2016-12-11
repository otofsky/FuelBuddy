package com.fuelbuddy.mobile.util.loginUtil;

import com.fuelbuddy.mobile.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;


public class UserUtil {

    public UserModel populateGoogleUser(GoogleSignInAccount account){
        //Create a new google populateGoogleUser
        UserModel googleUser = new UserModel();
        if(account!=null) {
            googleUser.setUserId("2132");
            googleUser.setProfileName(account.getDisplayName());
            googleUser.setEmail(account.getEmail());
        }
        return googleUser;
    }


    public UserModel populateFacebookUser(JSONObject object){
        UserModel googleUser = new UserModel();
        googleUser.setUserId("1");
        googleUser.setProfileName("james dean");
        googleUser.setEmail("j@gmail.com");
        return googleUser;
    }



}
