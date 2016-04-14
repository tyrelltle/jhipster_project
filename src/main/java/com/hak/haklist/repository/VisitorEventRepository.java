package com.hak.haklist.repository;

import com.hak.haklist.domain.VisitorEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the VisitorEvent entity.
 *
 */
public interface VisitorEventRepository extends JpaRepository<VisitorEvent, String> {


    /**
     * number of users clicked on 'i want a job' button in contest page,
     * and finished account registration
     */
    @Query("SELECT COUNT(v) FROM VisitorEvent v WHERE v.contest_register_clicked=true and v.register_confirmed=true")
    Long contestReg_confirmedReg();

    /**
     * number of users clicked on 'i want a job' button in contest page,
     * but NOT finished account registration
     * @return
     */
    @Query("SELECT COUNT(v) FROM VisitorEvent v WHERE v.contest_register_clicked=true and v.register_confirmed=false")
    Long contestReg_notConfirmedReg();

    /**
     * number of users finished registration through home page register button,
     * after that clicked on 'i want a job' button in contest page
     * @return
     */
    @Query("SELECT COUNT(v) FROM VisitorEvent v WHERE v.register_clicked=true and v.register_confirmed=true and v.contest_register_clicked=true")
    Long clickedReg_confirmedReg_AndcontestReg();
}
