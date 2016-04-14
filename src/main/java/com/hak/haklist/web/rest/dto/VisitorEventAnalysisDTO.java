package com.hak.haklist.web.rest.dto;

public class VisitorEventAnalysisDTO {
    private Long contestReg_confirmedReg;
    private Long contestReg_notConfirmedReg;
    private Long clickedReg_confirmedReg_AndcontestReg;

    public VisitorEventAnalysisDTO(Long contestReg_confirmedReg, Long contestReg_notConfirmedReg, Long clickedReg_confirmedReg_AndcontestReg) {
        this.contestReg_confirmedReg = contestReg_confirmedReg;
        this.contestReg_notConfirmedReg = contestReg_notConfirmedReg;
        this.clickedReg_confirmedReg_AndcontestReg = clickedReg_confirmedReg_AndcontestReg;
    }

    public Long getContestReg_confirmedReg() {
        return contestReg_confirmedReg;
    }

    public void setContestReg_confirmedReg(Long contestReg_confirmedReg) {
        this.contestReg_confirmedReg = contestReg_confirmedReg;
    }

    public Long getContestReg_notConfirmedReg() {
        return contestReg_notConfirmedReg;
    }

    public void setContestReg_notConfirmedReg(Long contestReg_notConfirmedReg) {
        this.contestReg_notConfirmedReg = contestReg_notConfirmedReg;
    }

    public Long getClickedReg_confirmedReg_AndcontestReg() {
        return clickedReg_confirmedReg_AndcontestReg;
    }

    public void setClickedReg_confirmedReg_AndcontestReg(Long clickedReg_confirmedReg_AndcontestReg) {
        this.clickedReg_confirmedReg_AndcontestReg = clickedReg_confirmedReg_AndcontestReg;
    }

    @Override
    public String toString() {
        return "VisitorEventAnalysisDTO{" +
            "contestReg_confirmedReg=" + contestReg_confirmedReg +
            ", contestReg_notConfirmedReg=" + contestReg_notConfirmedReg +
            ", clickedReg_confirmedReg_AndcontestReg=" + clickedReg_confirmedReg_AndcontestReg +
            '}';
    }
}
