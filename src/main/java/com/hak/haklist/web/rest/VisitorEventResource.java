package com.hak.haklist.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hak.haklist.security.AuthoritiesConstants;
import com.hak.haklist.service.VisitorEventService;
import com.hak.haklist.web.rest.dto.VisitorEventAnalysisDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;

/**
 * REST controller for recording annoymous visitor activities (events)
 * The events include:
 * -clicked register button
 * -confirmed registration
 * -clicked on 'I want a job!' button on contest registration page
 */
@RestController
@RequestMapping("/api")
public class VisitorEventResource {

    private final Logger log = LoggerFactory.getLogger(VisitorEventResource.class);

    @Inject
    private VisitorEventService visitorEventService;


    @RequestMapping(value = "/event/record/{name}",
        method = RequestMethod.POST)
    @Timed
    public  @ResponseBody ResponseEntity<?> recordEvent(@CookieValue(value = "VISITOR_ID", defaultValue = "") String visitor_id,@PathVariable String name) throws URISyntaxException {
        if(visitor_id.equals("")){
            log.error("undefined VISITOR_ID cookie found when calling POST /event/"+name);
        }else{
            visitorEventService.recordEvent(visitor_id,name);
        }
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/event",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public  @ResponseBody ResponseEntity<VisitorEventAnalysisDTO> getSummary() throws URISyntaxException {
        VisitorEventAnalysisDTO ret=visitorEventService.getAnalysisSummary();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
