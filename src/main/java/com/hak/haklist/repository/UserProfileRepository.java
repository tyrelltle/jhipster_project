package com.hak.haklist.repository;

import com.hak.haklist.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
     UserProfile findOneByUserId(Long id);
}
