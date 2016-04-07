package com.hak.haklist.web.rest.dto;

import com.hak.haklist.domain.UserProfile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserProfileDTO {
    @NotNull
    @Size(min = 2, max = 2)
    private String country;

    @Size(max = 200)
    private String company;

    private String linkedIn;
    private String gitHub;
    private String twitter;
    private String personalSite;
    private Boolean contest_reg;
    private List<String> tags;

    public UserProfileDTO() {
    }

    public UserProfileDTO(UserProfile userProfile) {
        if (userProfile != null) {
            this.country = userProfile.getCountry();
            this.company = userProfile.getCompany();
            this.linkedIn = userProfile.getLinkedin_path();
            this.gitHub = userProfile.getGithub_path();
            this.twitter = userProfile.getTwitter_path();
            this.personalSite = userProfile.getWebsite();
            this.contest_reg=userProfile.isContest_reg();
            this.tags=userProfile.getTags()==null?null:userProfile.getTags()
                                                                    .stream()
                                                                    .map(tag->tag.getName())
                                                                    .collect(Collectors.toList());
        }
    }

    public UserProfileDTO(String country, String company, String linkedIn, String gitHub,
                          String twitter, String personalSite,boolean contest_reg) {

        this.country = country;
        this.company = company;
        this.linkedIn = linkedIn;
        this.gitHub = gitHub;
        this.twitter = twitter;
        this.personalSite = personalSite;
        this.contest_reg=contest_reg;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getPersonalSite() {
        return personalSite;
    }

    public void setPersonalSite(String personalSite) {
        this.personalSite = personalSite;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean isContest_reg() {
        return contest_reg;
    }

    public void setContest_reg(Boolean contest_reg) {

        this.contest_reg = contest_reg==null?false:contest_reg;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
            "country='" + country + '\'' +
            ", company='" + company + '\'' +
            ", linkedIn='" + linkedIn + '\'' +
            ", gitHub='" + gitHub + '\'' +
            ", twitter='" + twitter + '\'' +
            ", personalSite=" + personalSite +
            ", contest_reg=" + contest_reg +
            "}";
    }
}
