package com.hak.haklist.web.rest.dto;

import com.hak.haklist.domain.User;

/**
 * A DTO representing a user, with his profile.
 */
public class UserExtDTO extends UserDTO {
    UserProfileDTO userProfile;

    public UserExtDTO() {
    }

    public UserExtDTO(User user) {
        super(user);
        userProfile = new UserProfileDTO(user.getUserProfile());
    }



    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + getLogin() + '\'' +
            ", password='" + getPassword() + '\'' +
            ", firstName='" + getFirstName() + '\'' +
            ", lastName='" + getLastName() + '\'' +
            ", email='" + getEmail() + '\'' +
            ", activated=" + isActivated() +
            ", langKey='" + getLangKey() + '\'' +
            ", authorities=" + getAuthorities() +
            ", userProfileDto=" + (userProfile == null ? "" : userProfile.toString()) +
            "}";
    }
}
