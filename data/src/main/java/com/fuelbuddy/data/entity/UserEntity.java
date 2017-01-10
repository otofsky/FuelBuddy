package com.fuelbuddy.data.entity;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class UserEntity {



    private String userID;
    private String profileName;
    private String email;
    private String profileLink;
    private String tokens;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userID='" + userID + '\'' +
                ", profileName='" + profileName + '\'' +
                ", email='" + email + '\'' +
                ", profileLink='" + profileLink + '\'' +
                ", tokens='" + tokens + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userID != null ? !userID.equals(that.userID) : that.userID != null) return false;
        if (profileName != null ? !profileName.equals(that.profileName) : that.profileName != null)
            return false;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (profileLink != null ? !profileLink.equals(that.profileLink) : that.profileLink != null)
            return false;
        return tokens != null ? tokens.equals(that.tokens) : that.tokens == null;

    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (profileName != null ? profileName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (profileLink != null ? profileLink.hashCode() : 0);
        result = 31 * result + (tokens != null ? tokens.hashCode() : 0);
        return result;
    }
}
