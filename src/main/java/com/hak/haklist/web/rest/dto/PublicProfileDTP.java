package com.hak.haklist.web.rest.dto;

import com.hak.haklist.domain.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a user displayed in public user list page, thus it
 * contains limited information
 */
public class PublicProfileDTP extends UserProfileDTO{

    String firstName;
    String lastName;
    String email;
    String login;
    public PublicProfileDTP(UserProfile p){
        super(p);
        this.firstName=p.getUser().getFirstName();
        this.lastName=p.getUser().getLastName();
        this.email=p.getUser().getEmail();
        this.login=p.getUser().getLogin();
    }

    public static List<PublicProfileDTP> toList(List<UserProfile> userProfiles){
        return userProfiles.stream().map(p->new PublicProfileDTP(p)).collect(Collectors.toList());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "PublicProfileDTO{" +
            "country='" + getCountry() + '\'' +
            ", company='" + getCompany() + '\'' +
            ", linkedIn='" + getLinkedIn() + '\'' +
            ", gitHub='" + getGitHub() + '\'' +
            ", twitter='" + getTwitter() + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", login='" + login + '\'' +
            ", personalSite=" + getPersonalSite() +
            "}";
    }
}
