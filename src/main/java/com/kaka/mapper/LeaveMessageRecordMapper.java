package com.kaka.mapper;

import com.kaka.model.LeaveMessageRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveMessageRecordMapper {


    Integer countLeaveMessageMapper();


    List<LeaveMessageRecord> queryLeaveMessage();


}
