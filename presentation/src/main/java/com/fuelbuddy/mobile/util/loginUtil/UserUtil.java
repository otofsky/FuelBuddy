package com.fuelbuddy.mobile.util.loginUtil;

import com.fuelbuddy.mobile.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;


public class UserUtil {

    public UserModel populateGoogleUser(GoogleSignInAccount account){
        //Create a new google populateGoogleUser
        UserModel googleUser = new UserModel();
        googleUser.setUserId("1");
        googleUser.setProfileName("james dean");
        googleUser.setEmail("j@gmail.com");
        //populate the populateGoogleUser
    /*    googleUser.setUserId(account.getIdToken());
        googleUser.setProfileName(account.getDisplayName());
        googleUser.setEmail(account.getEmail());*/
        //return the populated google populateGoogleUser
        return googleUser;
    }


    public UserModel populateFacebookUser(JSONObject object){
        //Create a new google populateGoogleUser
        UserModel googleUser = new UserModel();
        googleUser.setUserId("1");
        googleUser.setProfileName("james dean");
        googleUser.setEmail("j@gmail.com");
        //populate the populateGoogleUser
    /*    googleUser.setUserId(account.getIdToken());
        googleUser.setProfileName(account.getDisplayName());
        googleUser.setEmail(account.getEmail());*/
        //return the populated google populateGoogleUser
        return googleUser;
    }



}
