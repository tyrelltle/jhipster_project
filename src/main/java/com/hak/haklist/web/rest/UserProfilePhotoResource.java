package com.hak.haklist.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hak.haklist.domain.ProfilePicture;
import com.hak.haklist.domain.User;
import com.hak.haklist.repository.UserRepository;
import com.hak.haklist.security.SecurityUtils;
import com.hak.haklist.service.UserProfileService;
import com.hak.haklist.service.util.ResourceLoader;
import com.hak.haklist.web.rest.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * REST controller for managing UserProfile.
 */
@RestController
@RequestMapping("/api")
public class UserProfilePhotoResource {
    private static final int[] IMAGE_DIMENSION={180,180};
    private final Logger log = LoggerFactory.getLogger(UserProfilePhotoResource.class);

    @Inject
    private ResourceLoader resourceLoader;

    @Inject
    private UserProfileService userProfileService;

    @Inject
    private UserRepository userRepository;
    /**
     * GET /userProfiles/photo -> get a user's profile photo
     */
    @RequestMapping(value="/userProfiles/login/{login}/photo", method = RequestMethod.GET)
    public HttpEntity<byte[]> getPhoto(@PathVariable String login) throws IOException {
        ProfilePicture picture = userProfileService.getProfilePictureByUserLogin(login);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(picture.getImage_type()));
        byte[] ret;
        if(picture==null){
            ret= null;
        }else{
            headers.setContentLength((long) picture.getSize());
            ret=picture.getImage_data();
        }
        return new HttpEntity<>(ret, headers);
    }


    /**
     * POST  /userProfiles/photo -> Create a new userProfile.
     */
    @RequestMapping(value = "/userProfiles/photo",
        method = RequestMethod.POST,consumes = "multipart/form-data")
    @Timed
    public ResponseEntity<Void> uploadImage(@RequestParam("afile") MultipartFile file) throws URISyntaxException, IOException {

        log.debug("======wihtin upload controller");
        User currentUser=userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();





        String mimeType = file.getContentType();
        byte[] bytes = file.getBytes();


        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));
        BufferedImage resizedImage = ImageUtil.scaleImage(originalImage, IMAGE_DIMENSION[0], IMAGE_DIMENSION[1]);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(!ImageUtil.validImageType(mimeType)){
            log.error("POST /userProfiles/photo got incorect file mime type : "+mimeType);
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        ImageIO.write(resizedImage,mimeType.split("/")[1],baos);
        byte[] finalbytes=baos.toByteArray();
        userProfileService.setProfilePictureByUserId(currentUser.getId(),finalbytes,mimeType);
        return ResponseEntity.ok().build();
    }


}
