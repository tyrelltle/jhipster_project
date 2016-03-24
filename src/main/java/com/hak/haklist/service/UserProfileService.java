package com.hak.haklist.service;

import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
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
     *  delete the  userProfile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
    }
}
