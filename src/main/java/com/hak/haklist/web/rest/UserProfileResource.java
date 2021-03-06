package com.hak.haklist.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hak.haklist.domain.User;
import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.repository.UserRepository;
import com.hak.haklist.security.SecurityUtils;
import com.hak.haklist.service.UserProfileService;
import com.hak.haklist.web.rest.dto.PublicProfileDTP;
import com.hak.haklist.web.rest.dto.UserExtDTO;
import com.hak.haklist.web.rest.util.HeaderUtil;
import com.hak.haklist.web.rest.util.PaginationUtil;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserProfile.
 */
@RestController
@RequestMapping("/api")
public class UserProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileResource.class);

    @Inject
    private UserProfileService userProfileService;

    @Inject
    private UserRepository userRepository;
    /**
     * POST  /userProfiles -> Create a new userProfile.
     */
    @RequestMapping(value = "/userProfiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) throws URISyntaxException {
        log.debug("REST request to save UserProfile : {}", userProfile);
        if (userProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userProfile", "idexists", "A new userProfile cannot already have an ID")).body(null);
        }
        UserProfile result = userProfileService.save(userProfile);
        return ResponseEntity.created(new URI("/api/userProfiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userProfile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userProfiles -> Updates an existing userProfile.
     */
    @RequestMapping(value = "/userProfiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UserProfile userProfile) throws URISyntaxException {
        log.debug("REST request to update UserProfile : {}", userProfile);
        if (userProfile.getId() == null) {
            return createUserProfile(userProfile);
        }
        UserProfile result = userProfileService.save(userProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userProfile", userProfile.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /userProfiles/ext -> Updates an existing userProfile as well as fields in User.
     */
    @RequestMapping(value = "/userProfiles/ext",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateUserProfileExt(@RequestBody UserExtDTO userExtDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfile as well as User: {}", userExtDTO);
        userProfileService.updateUserInformation(userExtDTO);
        return ResponseEntity.ok().build();
    }




    /**
     * GET  /userProfiles -> get all the userProfiles.
     */
    @RequestMapping(value = "/userProfiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserProfile>> getAllUserProfiles(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("user-is-null".equals(filter)) {
            log.debug("REST request to get all UserProfiles where user is null");
            return new ResponseEntity<>(userProfileService.findAllWhereUserIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserProfiles");
        Page<UserProfile> page = userProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userProfiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userProfiles/public -> get all the userProfiles with user information for public.
     */
    @RequestMapping(value = "/userProfiles/public",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PublicProfileDTP>> getAllUserProfilesExt(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {

        Page<PublicProfileDTP> page = userProfileService.findAllExt(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userProfiles/ext");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userProfiles/:id -> get the "id" userProfile.
     */
    @RequestMapping(value = "/userProfiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long id) {
        log.debug("REST request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileService.findOne(id);
        return Optional.ofNullable(userProfile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /userProfiles/current -> get current user profile
     */
    @RequestMapping(value = "/userProfiles/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<UserExtDTO> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalUser =
            (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        User user= userRepository.findOneByLogin(principalUser.getUsername()).get();
        Hibernate.initialize(user.getUserProfile());
        UserExtDTO userExtDTO=new UserExtDTO(user);
        return new ResponseEntity(userExtDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /userProfiles/:id -> delete the "id" userProfile.
     */
    @RequestMapping(value = "/userProfiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        log.debug("REST request to delete UserProfile : {}", id);
        userProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userProfile", id.toString())).build();
    }


    /**
     * POST  /userProfiles/contest_reg -> register currently logged on userProfile for contest by userid.
     */
    @RequestMapping(value = "/userProfiles/contest_reg",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> contestreg() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        log.debug("REST request to register UserProfile for contest with username: {}", currentUserLogin);
        userProfileService.registerForContest(currentUserLogin);
        return ResponseEntity.ok().build();
    }



}
