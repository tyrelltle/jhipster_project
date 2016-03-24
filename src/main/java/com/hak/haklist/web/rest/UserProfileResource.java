package com.hak.haklist.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.service.UserProfileService;
import com.hak.haklist.web.rest.util.HeaderUtil;
import com.hak.haklist.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
}
