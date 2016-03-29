package com.hak.haklist.repository;

import com.hak.haklist.domain.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ProfilePicture entity.
 */
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long> {
}
