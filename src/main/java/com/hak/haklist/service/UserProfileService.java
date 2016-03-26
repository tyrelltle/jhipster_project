package com.hak.haklist.service;

import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.repository.UserProfileRepository;
import com.hak.haklist.repository.UserRepository;
import com.hak.haklist.security.SecurityUtils;
import com.hak.haklist.web.rest.dto.UserExtDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
            userProfileRepository.save(userProfile);
        });

    }


    /**
     *  delete the  userProfile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
    }
}
