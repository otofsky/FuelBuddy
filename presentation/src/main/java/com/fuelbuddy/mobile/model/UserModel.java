package com.fuelbuddy.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zjuroszek on 21.11.16.
 */
public class UserModel implements Parcelable {
    private String  userId;
    private String profileName;
    private String username;
    private String email;
    private String profileLink;

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        userId = in.readString();
        profileName = in.readString();
        username = in.readString();
        email = in.readString();
        profileLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(profileName);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(profileLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", profileName='" + profileName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profileLink='" + profileLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (userId != null ? !userId.equals(userModel.userId) : userModel.userId != null)
            return false;
        if (profileName != null ? !profileName.equals(userModel.profileName) : userModel.profileName != null)
            return false;
        if (username != null ? !username.equals(userModel.username) : userModel.username != null)
            return false;
        if (email != null ? !email.equals(userModel.email) : userModel.email != null) return false;
        return profileLink != null ? profileLink.equals(userModel.profileLink) : userModel.profileLink == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (profileName != null ? profileName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (profileLink != null ? profileLink.hashCode() : 0);
        return result;
    }
}
