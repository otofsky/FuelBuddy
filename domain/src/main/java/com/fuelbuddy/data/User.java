package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class User  {

    private String userId;
    private String profileName;
    private String email;
    private String profileLink;
    private String tokens;

    public User() {
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
        return "User{" +
                "userId='" + userId + '\'' +
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

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (profileName != null ? !profileName.equals(user.profileName) : user.profileName != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (profileLink != null ? !profileLink.equals(user.profileLink) : user.profileLink != null)
            return false;
        return tokens != null ? tokens.equals(user.tokens) : user.tokens == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (profileName != null ? profileName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (profileLink != null ? profileLink.hashCode() : 0);
        result = 31 * result + (tokens != null ? tokens.hashCode() : 0);
        return result;
    }
}
