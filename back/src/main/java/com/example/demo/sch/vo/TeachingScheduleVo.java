package com.example.demo.sch.vo;

import com.example.demo.sch.entity.TeachingSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeachingScheduleVo {
    private Integer x;
    private List<TeachingSchedule> scheduleList;
}
