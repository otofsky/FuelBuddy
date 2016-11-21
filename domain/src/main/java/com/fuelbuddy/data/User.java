package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class User  {

    private String userId;

    private String profileName;
    private String username;
    private String email;
    private String profileLink;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }


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
        return "User{" +
                "userId='" + userId + '\'' +
                ", profileName='" + profileName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profileLink='" + profileLink + '\'' +
                '}';
    }
}
