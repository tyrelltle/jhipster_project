package com.hak.haklist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "linkedin_path")
    private String linkedin_path;

    @Column(name = "github_path")
    private String github_path;

    @Column(name = "twitter_path")
    private String twitter_path;

    @Column(name = "website")
    private String website;

    @Column(name = "country")
    private String country;

    @Column(name = "company")
    private String company;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userProfile", fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @JoinTable(name = "user_tag",
        joinColumns = {
            @JoinColumn(name = "user_profiles_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "tags_id", referencedColumnName = "id")
        }
    )
    @ManyToMany
    private Collection<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkedin_path() {
        return linkedin_path;
    }

    public void setLinkedin_path(String linkedin_path) {
        this.linkedin_path = linkedin_path;
    }

    public String getGithub_path() {
        return github_path;
    }

    public void setGithub_path(String github_path) {
        this.github_path = github_path;
    }

    public String getTwitter_path() {
        return twitter_path;
    }

    public void setTwitter_path(String twitter_path) {
        this.twitter_path = twitter_path;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        if (userProfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + id +
            ", linkedin_path='" + linkedin_path + "'" +
            ", github_path='" + github_path + "'" +
            ", twitter_path='" + twitter_path + "'" +
            ", website='" + website + "'" +
            ", country='" + country + "'" +
            ", company='" + company + "'" +
            '}';
    }
}