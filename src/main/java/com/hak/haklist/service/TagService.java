package com.hak.haklist.service;

import com.hak.haklist.domain.Tag;
import com.hak.haklist.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Tag.
 */
@Service
@Transactional
public class TagService {

    private final Logger log = LoggerFactory.getLogger(TagService.class);
    
    @Inject
    private TagRepository tagRepository;
    
    /**
     * Save a tag.
     * @return the persisted entity
     */
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        Tag result = tagRepository.save(tag);
        return result;
    }

    /**
     *  get all the tags.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tag> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        Page<Tag> result = tagRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one tag by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tag findOne(Long id) {
        log.debug("Request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        return tag;
    }

    /**
     *  delete the  tag by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
    }
}
