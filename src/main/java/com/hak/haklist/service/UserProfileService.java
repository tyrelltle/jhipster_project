package com.hak.haklist.service;

import com.hak.haklist.domain.ProfilePicture;
import com.hak.haklist.domain.Tag;
import com.hak.haklist.domain.User;
import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.repository.ProfilePictureRepository;
import com.hak.haklist.repository.TagRepository;
import com.hak.haklist.repository.UserProfileRepository;
import com.hak.haklist.repository.UserRepository;
import com.hak.haklist.security.SecurityUtils;
import com.hak.haklist.web.rest.dto.PublicProfileDTP;
import com.hak.haklist.web.rest.dto.UserExtDTO;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserProfile.
 */
@Service
@Transactional
public class UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    @Inject
    private UserProfileRepository userProfileRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TagRepository tagRepository;


    @Inject
    private ProfilePictureRepository profilePictureRepository;

    /**
     * Save a userProfile.
     * @return the persisted entity
     */
    public UserProfile save(UserProfile userProfile) {
        log.debug("Request to save UserProfile : {}", userProfile);
        UserProfile result = userProfileRepository.save(userProfile);
        return result;
    }

    /**
     *  get all the userProfiles.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserProfile> findAll(Pageable pageable) {
        log.debug("Request to get all UserProfiles");
        Page<UserProfile> result = userProfileRepository.findAll(pageable);
        return result;
    }

    /**
     *  get all the userProfiles with some User entity information.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PublicProfileDTP> findAllExt(Pageable pageable) {
        log.debug("Request to get all UserProfiles");
        Page<UserProfile> result = userProfileRepository.findAll(pageable);
        Page<PublicProfileDTP> resultext=new PageImpl<>(PublicProfileDTP.toList(result.getContent()),
                                                        pageable,
                                                        result.getTotalElements());
        return resultext;
    }

    /**
     *  get all the userProfiles where User is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserProfile> findAllWhereUserIsNull() {
        log.debug("Request to get all userProfiles where User is null");
        return StreamSupport
            .stream(userProfileRepository.findAll().spliterator(), false)
            .filter(userProfile -> userProfile.getUser() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one userProfile by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserProfile findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileRepository.findOne(id);
        return userProfile;
    }

    /**
     *  get one userProfile by user id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserProfile findOneByUserId(Long id) {
        UserProfile userProfile = userProfileRepository.findOneByUserId(id);
        return userProfile;
    }


    /**
     * sets user profile picture
     * @param id    user id associated with the userProfile
     * @param blob  byte array of the picture file
     * @param type  MIME type of the picture file
     */
    @Transactional
    public void setProfilePictureByUserId(Long id,byte[] blob,String type){
        if(blob==null || blob.length==0) {
            log.error("Requested to set empty picture data to profile for user id : {}", id);
            return;
        }

        /**
         * fetch lazy loaded profile picture
         */
        UserProfile userProfile=userProfileRepository.findOneByUserId(id);
        Hibernate.initialize(userProfile.getProfilePicture());

        /**
         * initialize profilePicture entity
         */
        ProfilePicture profilePicture=new ProfilePicture();
        profilePicture.setImage_data(blob);
        profilePicture.setImage_type(type);
        profilePicture.setSize(blob.length);

        /**
         * associal picture and profile with each other
         */
        profilePicture.setUserProfile(userProfile);
        userProfile.setProfilePicture(profilePicture);

        /**
         * persist
         */
        profilePictureRepository.save(profilePicture);
        userProfileRepository.save(userProfile);

    }


    /**
     * Update user and profile information
     * @param userExtDTO DTO wrapping both profile and user information
     */
    @Transactional
    public void updateUserInformation(UserExtDTO userExtDTO) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).ifPresent(u -> {
            u.setFirstName(userExtDTO.getFirstName());
            u.setLastName(userExtDTO.getLastName());
            u.setEmail(userExtDTO.getEmail());
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
            UserProfile userProfile=userProfileRepository.findOneByUserId(u.getId());
            userProfile.setCountry(userExtDTO.getUserProfile().getCountry());
            userProfile.setCompany(userExtDTO.getUserProfile().getCompany());
            userProfile.setGithub_path(userExtDTO.getUserProfile().getGitHub());
            userProfile.setLinkedin_path(userExtDTO.getUserProfile().getLinkedIn());
            userProfile.setTwitter_path(userExtDTO.getUserProfile().getTwitter());
            log.debug("Changed Information for UserProfile: {}", userProfile);
            addUserTag(userProfile,userExtDTO.getUserProfile().getTags(),false);
            userProfileRepository.save(userProfile);
        });
    }

    @Transactional
    public void addUserTag(UserProfile profile,List<String> tags,boolean persist){

        profile.setTags(new HashSet<>());
        if(tags!=null)
            tags.forEach(tagname->{
                Tag tag=tagRepository.findOneByName(tagname);
                Hibernate.initialize(tag.getUserProfiles());
                profile.getTags().add(tag);
                tag.getUserProfiles().add(profile);
            });
        if(persist)
            userProfileRepository.save(profile);
    }


    /**
     *  delete the  userProfile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
    }

    @Transactional
    public ProfilePicture getProfilePictureByUserLogin(String login) {
        User user=userRepository.findOneByLogin(login).get();
        Hibernate.initialize(user.getUserProfile());
        if(user.getUserProfile()==null)
            return null;
        Hibernate.initialize(user.getUserProfile().getProfilePicture());
        return user.getUserProfile().getProfilePicture();
    }
}
