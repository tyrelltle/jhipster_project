package com.hak.haklist.service;

import com.hak.haklist.domain.VisitorEvent;
import com.hak.haklist.repository.VisitorEventRepository;
import com.hak.haklist.web.rest.dto.VisitorEventAnalysisDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing visitor events.
 */
@Service
@Transactional
public class VisitorEventService {

    private final Logger log = LoggerFactory.getLogger(VisitorEventService.class);

    @Inject
    private VisitorEventRepository visitorEventRepository;


    public void recordEvent(String visitorId,String eventName){
        log.debug("logging new event "+eventName+" for visitor "+visitorId);
        VisitorEvent visitorEvent=visitorEventRepository.findOne(visitorId);
        if(visitorEvent==null) {
            visitorEvent = new VisitorEvent();
            visitorEvent.setId(visitorId);
        }
        visitorEvent.recordEvent(eventName);
        visitorEventRepository.save(visitorEvent);
    }

    public VisitorEventAnalysisDTO getAnalysisSummary(){
        Long val1=visitorEventRepository.contestReg_confirmedReg();
        Long val2=visitorEventRepository.contestReg_notConfirmedReg();
        Long val3=visitorEventRepository.clickedReg_confirmedReg_AndcontestReg();
        return new VisitorEventAnalysisDTO(val1,val2,val3);
    }

}
