
package com.fuelbuddy.mobile.mapper;



import com.fuelbuddy.data.User;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.model.UserModel;

import javax.inject.Inject;


@PerActivity
public class UserModelDataMapper {

  @Inject
  public UserModelDataMapper() {}

  /**
   * Transform a {@link User} into an {@link UserModel}.
   *
   * @param user Object to be transformed.
   * @return {@link UserModel}.
   */
  public UserModel transformToUserModel(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Cannot transformToUser a null value");
    }
    UserModel userModel = new UserModel();
    userModel.setUserId(user.getUserID());
    userModel.setProfileName(user.getProfileName());
    userModel.setEmail(user.getEmail());


    return userModel;
  }

  public User transformToUser(UserModel userModel) {
    if (userModel == null) {
      throw new IllegalArgumentException("Cannot transformToUser a null value");
    }
    User user = new User();
    user.setUserID(userModel.getUserId());
    user.setProfileName(userModel.getProfileName());
    user.setEmail(userModel.getEmail());
    return user;
  }



}
