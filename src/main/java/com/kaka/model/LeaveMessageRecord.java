package com.kaka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LeaveMessageRecord {
    private Integer id;

    private String pageName;

    private Integer pId=0;

    private Integer answererId;

    private Integer respondentId;

    private String leaveMessageDate;

    private Integer likes=0;

    private String leaveMessageContent;

    private int isRead=1 ;

    public LeaveMessageRecord(String pageName, Integer answererId, Integer respondentId, String leaveMessageDate, String leaveMessageContent) {
        this.pageName = pageName;
        this.answererId = answererId;
        this.respondentId = respondentId;
        this.leaveMessageDate = leaveMessageDate;
        this.leaveMessageContent = leaveMessageContent;
    }
}
