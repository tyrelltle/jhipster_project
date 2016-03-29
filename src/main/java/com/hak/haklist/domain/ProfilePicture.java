package com.hak.haklist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "profile_picture")
public class ProfilePicture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "size")
    private double size;

    @Column(name = "image_type")
    private String image_type;

    @Column(name = "image_data")
    private byte[] image_data;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profilePicture", fetch = FetchType.LAZY)
    @JsonIgnore
    private UserProfile userProfile;

    public ProfilePicture(double size, String image_type, byte[] image_data, UserProfile userProfile) {
        this.size = size;
        this.image_type = image_type;
        this.image_data = image_data;
        this.userProfile = userProfile;
    }

    public ProfilePicture(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


}
