package com.hak.haklist.repository;

import com.hak.haklist.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
     UserProfile findOneByUserId(Long id);

     UserProfile findOneByUserLogin(String login);

     Page<UserProfile> findAllByOrderByUserCreatedDateDesc(Pageable pageable);


}
