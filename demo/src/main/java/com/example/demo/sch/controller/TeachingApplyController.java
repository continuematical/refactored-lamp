package com.example.demo.sch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.sch.entity.Curriculum;
import com.example.demo.sch.entity.TeachingApply;
import com.example.demo.sch.entity.TeachingSchedule;
import com.example.demo.sch.service.CurriculumService;
import com.example.demo.sch.service.TeachingApplyService;
import com.example.demo.sch.service.TeachingScheduleService;
import com.example.demo.sch.vo.TeachingApplyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "排课申请管理接口")
@RequestMapping("/zhou/teachingApply")
@RestController
@Transactional
public class TeachingApplyController {
    @Autowired
    private TeachingApplyService teachingApplyService;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private TeachingScheduleService teachingScheduleService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/work", method = RequestMethod.GET)
    @Operation(summary = "自动排课")
    public Result<Object> work(){
        List<TeachingApply> applyList = teachingApplyService.list();
        List<TeachingApplyVo> ans = new ArrayList<>();
        // 分离用户的排课申请
        for (TeachingApply a : applyList) {
            boolean flag = true;
            for (TeachingApplyVo vo : ans) {
                if(Objects.equals(a.getUserID(),vo.getUserId())) {
                    vo.getList().add(a);
                    flag = false;
                    break;
                }
            }
            if(flag) {
                TeachingApplyVo vo = new TeachingApplyVo();
                vo.setUserId(a.getUserID());
                vo.setUsername(a.getUsername());
                List<TeachingApply> aList = new ArrayList<>();
                aList.add(a);
                vo.setList(aList);
                vo.setFlag(0);
                ans.add(vo);
            }
        }
        // 课位初始化
        List<TeachingSchedule> scheduleList = teachingScheduleService.list();
        for (TeachingSchedule s : scheduleList) {
            s.setCurID("");
            s.setCurName("");
            s.setTeacherName("");
        }
        // 根据排课申请数量升序排列，尽可能满足多的教师完成排课
        Collections.sort(ans, new Comparator<TeachingApplyVo>() {
            @Override
            public int compare(TeachingApplyVo o1, TeachingApplyVo o2) {
                return o1.getList().size() - o2.getList().size();
            }
        });
        for(int i = 0; i < ans.size(); i ++) {
            List<TeachingSchedule> tempList = new ArrayList<>();
            for (TeachingSchedule s : scheduleList) {
                tempList.add(s);
            }
            // 判断能否满足排课
            boolean flagSum = true;
            for (TeachingApply a : ans.get(i).getList()) {
                boolean flag = true;
                for (TeachingSchedule teachingSchedule : tempList) {
                    if(Objects.equals(a.getScheduleId(),teachingSchedule.getId()) && NullUtils.isNull(teachingSchedule.getCurID())) {
                        // 排课成功
                        teachingSchedule.setCurID(a.getCurriculumId());
                        teachingSchedule.setCurName(a.getCurriculumName());
                        teachingSchedule.setTeacherName(a.getUsername());
                        a.setStatus("排课成功");
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    // 排课失败,下一位
                    flagSum = false;
                    break;
                }
            }
            if(flagSum) {
                // 如果全部课程可以排课，则保存
                scheduleList = new ArrayList<>();
                for (TeachingSchedule vo : tempList) {
                    scheduleList.add(vo);
                }
                ans.get(i).setFlag(1);
            } else {
                // 否则不保存
            }
        }
        // 未完成排课的，按顺序能排上的排上
        for (TeachingApplyVo vo : ans) {
            if(Objects.equals(1,vo.getFlag())) {
                continue;
            }
            for (TeachingApply a : vo.getList()) {
                for (TeachingSchedule teachingSchedule : scheduleList) {
                    if(Objects.equals(a.getScheduleId(),teachingSchedule.getId()) && NullUtils.isNull(teachingSchedule.getCurID())) {
                        // 补排课成功
                        teachingSchedule.setCurID(a.getCurriculumId());
                        teachingSchedule.setCurName(a.getCurriculumName());
                        teachingSchedule.setTeacherName(a.getUsername());
                        a.setStatus("排课成功");
                        break;
                    }
                }
            }
        }
        // 保存排课数据
        for (TeachingSchedule teachingSchedule : scheduleList) {
            teachingScheduleService.saveOrUpdate(teachingSchedule);
        }
        // 保存申请数据
        for (TeachingApplyVo vo : ans) {
            for (TeachingApply a : vo.getList()) {
                if(!Objects.equals("排课成功",a.getStatus())) {
                    a.setStatus("排课失败");
                }
                teachingApplyService.saveOrUpdate(a);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @Operation(summary = "查询单条排课申请")
    public Result<TeachingApply> get(@RequestParam String id) {
        return new ResultUtil<TeachingApply>().setData(teachingApplyService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @Operation(summary = "查询全部排课申请个数")
    public Result<Long> getCount() {
        return new ResultUtil<Long>().setData(teachingApplyService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Operation(summary = "查询全部排课申请")
    public Result<List<TeachingApply>> getAll() {
        return new ResultUtil<List<TeachingApply>>().setData(teachingApplyService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @Operation(summary = "查询排课申请")
    public Result<IPage<TeachingApply>> getByPage(@ModelAttribute TeachingApply teachingApply, @ModelAttribute PageVo page){
        QueryWrapper<TeachingApply> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND role_id = '496138616573952'");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("user_id",currUser.getId());
        }
        if(!NullUtils.isNull(teachingApply.getUsername())) {
            qw.like("user_name",teachingApply.getUsername());
        }
        if(!NullUtils.isNull(teachingApply.getCurriculumName())) {
            qw.like("curriculum_name",teachingApply.getCurriculumName());
        }
        if(!NullUtils.isNull(teachingApply.getScheduleName())) {
            qw.like("schedule_name",teachingApply.getScheduleName());
        }
        IPage<TeachingApply> data = teachingApplyService.page(PageUtil.initMyPage(page),qw);
        return new ResultUtil<IPage<TeachingApply>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @Operation(summary = "增改排课申请")
    public Result<TeachingApply> saveOrUpdate(TeachingApply teachingApply){
        if(teachingApplyService.saveOrUpdate(teachingApply)){
            return new ResultUtil<TeachingApply>().setData(teachingApply);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(summary = "新增排课申请")
    public Result<TeachingApply> insert(TeachingApply teachingApply){
        Curriculum curriculum = curriculumService.getById(teachingApply.getCurriculumId());
        if(curriculum == null) {
            return ResultUtil.error("课程不存在");
        }
        teachingApply.setCurriculumName(curriculum.getTitle());
        TeachingSchedule schedule = teachingScheduleService.getById(teachingApply.getScheduleId());
        if(schedule == null) {
            return ResultUtil.error("排课不存在");
        }
        teachingApply.setScheduleName(schedule.getTitle());
        User currUser = securityUtil.getCurrUser();
        teachingApply.setUserID(currUser.getId());
        teachingApply.setUsername(currUser.getNickname());
        teachingApply.setStatus("未排课");
        teachingApplyService.saveOrUpdate(teachingApply);
        return new ResultUtil<TeachingApply>().setData(teachingApply);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(summary = "编辑排课申请")
    public Result<TeachingApply> update(TeachingApply teachingApply){
        teachingApplyService.saveOrUpdate(teachingApply);
        return new ResultUtil<TeachingApply>().setData(teachingApply);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除排课申请")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            teachingApplyService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    @Operation(summary = "删除排课申请")
    public Result<Object> deleteAll(){
        QueryWrapper<TeachingApply> qw = new QueryWrapper<>();
        teachingApplyService.remove(qw);
        List<TeachingSchedule> scheduleList = teachingScheduleService.list();
        for (TeachingSchedule s : scheduleList) {
            s.setCurID("");
            s.setCurName("");
            s.setTeacherName("");
            teachingScheduleService.saveOrUpdate(s);
        }
        return ResultUtil.success();
    }
}
