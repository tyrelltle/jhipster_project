package com.hak.haklist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Table tracking visitor click events
 */
@Entity
@Table(name = "visitor_event")
public class VisitorEvent implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "register_clicked")
    private Boolean register_clicked=false;

    @Column(name = "register_confirmed")
    private Boolean register_confirmed=false;

    @Column(name = "contest_register_clicked")
    private Boolean contest_register_clicked=false;

    public void recordEvent(String name){
        if(name==null)
            return;
        if(name.equals("register_clicked")) {
            /*  if use has not registered successfully,
                only one of contest_register_clicked or
                register_clicked could happen at the same
                time.
            */
            if(!this.register_confirmed) {
                if(this.contest_register_clicked)
                    this.setContest_register_clicked(false);
                this.setRegister_clicked(true);
            }else{
                //do nothing. once register is confirmed, register_clicked event
                //wont affect anything ...
            }
        }else if(name.equals("register_confirmed")) {
            this.setRegister_confirmed(true);
        }else if(name.equals("contest_register_clicked")) {
            if(this.register_clicked&&!this.register_confirmed)
                this.setRegister_clicked(false);
            this.setContest_register_clicked(true);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getRegister_clicked() {
        return register_clicked;
    }

    public void setRegister_clicked(Boolean register_clicked) {
        this.register_clicked = register_clicked;
    }

    public Boolean getRegister_confirmed() {
        return register_confirmed;
    }

    public void setRegister_confirmed(Boolean register_confirmed) {
        this.register_confirmed = register_confirmed;
    }

    public Boolean getContest_register_clicked() {
        return contest_register_clicked;
    }

    public void setContest_register_clicked(Boolean contest_register_clicked) {
        this.contest_register_clicked = contest_register_clicked;
    }

    @Override
    public String toString() {
        return "VisitorEvent{" +
            "id='" + id + '\'' +
            ", register_clicked=" + register_clicked +
            ", register_confirmed=" + register_confirmed +
            ", contest_register_clicked=" + contest_register_clicked +
            '}';
    }
}
