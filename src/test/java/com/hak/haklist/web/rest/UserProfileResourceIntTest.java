package com.hak.haklist.web.rest;

import com.hak.haklist.Application;
import com.hak.haklist.domain.UserProfile;
import com.hak.haklist.repository.UserProfileRepository;
import com.hak.haklist.service.UserProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UserProfileResource REST controller.
 *
 * @see UserProfileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserProfileResourceIntTest {

    private static final String DEFAULT_LINKEDIN_PATH = "AAAAA";
    private static final String UPDATED_LINKEDIN_PATH = "BBBBB";
    private static final String DEFAULT_GITHUB_PATH = "AAAAA";
    private static final String UPDATED_GITHUB_PATH = "BBBBB";
    private static final String DEFAULT_TWITTER_PATH = "AAAAA";
    private static final String UPDATED_TWITTER_PATH = "BBBBB";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_COMPANY = "AAAAA";
    private static final String UPDATED_COMPANY = "BBBBB";

    @Inject
    private UserProfileRepository userProfileRepository;

    @Inject
    private UserProfileService userProfileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserProfileResource userProfileResource = new UserProfileResource();
        ReflectionTestUtils.setField(userProfileResource, "userProfileService", userProfileService);
        this.restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userProfile = new UserProfile();
        userProfile.setLinkedin_path(DEFAULT_LINKEDIN_PATH);
        userProfile.setGithub_path(DEFAULT_GITHUB_PATH);
        userProfile.setTwitter_path(DEFAULT_TWITTER_PATH);
        userProfile.setWebsite(DEFAULT_WEBSITE);
        userProfile.setCountry(DEFAULT_COUNTRY);
        userProfile.setCompany(DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile

        restUserProfileMockMvc.perform(post("/api/userProfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userProfile)))
                .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfiles.get(userProfiles.size() - 1);
        assertThat(testUserProfile.getLinkedin_path()).isEqualTo(DEFAULT_LINKEDIN_PATH);
        assertThat(testUserProfile.getGithub_path()).isEqualTo(DEFAULT_GITHUB_PATH);
        assertThat(testUserProfile.getTwitter_path()).isEqualTo(DEFAULT_TWITTER_PATH);
        assertThat(testUserProfile.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testUserProfile.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testUserProfile.getCompany()).isEqualTo(DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfiles
        restUserProfileMockMvc.perform(get("/api/userProfiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
                .andExpect(jsonPath("$.[*].linkedin_path").value(hasItem(DEFAULT_LINKEDIN_PATH.toString())))
                .andExpect(jsonPath("$.[*].github_path").value(hasItem(DEFAULT_GITHUB_PATH.toString())))
                .andExpect(jsonPath("$.[*].twitter_path").value(hasItem(DEFAULT_TWITTER_PATH.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())));
    }

    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/userProfiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.linkedin_path").value(DEFAULT_LINKEDIN_PATH.toString()))
            .andExpect(jsonPath("$.github_path").value(DEFAULT_GITHUB_PATH.toString()))
            .andExpect(jsonPath("$.twitter_path").value(DEFAULT_TWITTER_PATH.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/userProfiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

		int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        userProfile.setLinkedin_path(UPDATED_LINKEDIN_PATH);
        userProfile.setGithub_path(UPDATED_GITHUB_PATH);
        userProfile.setTwitter_path(UPDATED_TWITTER_PATH);
        userProfile.setWebsite(UPDATED_WEBSITE);
        userProfile.setCountry(UPDATED_COUNTRY);
        userProfile.setCompany(UPDATED_COMPANY);

        restUserProfileMockMvc.perform(put("/api/userProfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userProfile)))
                .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfiles.get(userProfiles.size() - 1);
        assertThat(testUserProfile.getLinkedin_path()).isEqualTo(UPDATED_LINKEDIN_PATH);
        assertThat(testUserProfile.getGithub_path()).isEqualTo(UPDATED_GITHUB_PATH);
        assertThat(testUserProfile.getTwitter_path()).isEqualTo(UPDATED_TWITTER_PATH);
        assertThat(testUserProfile.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testUserProfile.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUserProfile.getCompany()).isEqualTo(UPDATED_COMPANY);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

		int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Get the userProfile
        restUserProfileMockMvc.perform(delete("/api/userProfiles/{id}", userProfile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
