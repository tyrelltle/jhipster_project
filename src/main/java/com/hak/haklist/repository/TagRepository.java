package com.hak.haklist.repository;

import com.hak.haklist.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Tag entity.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findOneByName(String name);
}
