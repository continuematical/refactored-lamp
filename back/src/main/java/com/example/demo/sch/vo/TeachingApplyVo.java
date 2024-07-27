package com.example.demo.sch.vo;

import com.example.demo.sch.entity.TeachingApply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingApplyVo {
    private String userId;
    private String username;
    private Integer flag;
    private List<TeachingApply> list;
}
