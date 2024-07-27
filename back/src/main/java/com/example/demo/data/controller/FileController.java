package com.example.demo.data.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.File;
import com.example.demo.data.entity.Setting;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IFileService;
import com.example.demo.data.service.ISettingService;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.MyFileUtils;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.vo.OssSettingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/zhou/file")
@Transactional
@Tag(name = "文件管理接口")
public class FileController {
    @Autowired
    private MyFileUtils myFileUtils;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private ISettingService iSettingService;

    @PersistenceContext
    private EntityManager entityManager;

    @SystemLog(about = "查询系统文件", type = LogType.DATA_CENTER, dotype = "FILE-01")
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "查询系统文件")
    @ResponseBody
    public Result<IPage<File>> getByCondition(@ModelAttribute File file, @ModelAttribute PageVo page) {
        QueryWrapper<File> qw = new QueryWrapper<>();
        if (!NullUtils.isNull(file.getFKey())) {
            qw.eq("f_key", file.getFKey());
        }
        if (!NullUtils.isNull(file.getType())) {
            qw.eq("type", file.getType());
        }
        if (!NullUtils.isNull(file.getName())) {
            qw.eq("name", file.getName());
        }
        IPage<File> fileList = iFileService.page(PageUtil.initMyPage(page), qw);

        OssSettingVo os = getOssSetting();
        Map<String, String> map = new HashMap<>(16);
        for (File e : fileList.getRecords()) {
            if (StrUtil.isNotBlank(e.getCreateBy())) {
                if (!map.containsKey(e.getCreateBy())) {
                    QueryWrapper<User> userQw = new QueryWrapper<>();
                    userQw.eq("username", e.getCreateBy());
                    User u = iUserService.getOne(userQw);
                    if (u != null) {
                        e.setNickname(u.getNickname());
                    }
                    map.put(e.getCreateBy(), u.getNickname());
                } else {
                    e.setNickname(map.get(e.getCreateBy()));
                }
            }
        }
        map = null;
        return new ResultUtil<IPage<File>>().setData(fileList);
    }

    @SystemLog(about = "文件复制", type = LogType.DATA_CENTER, dotype = "FILE-02")
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    @Operation(summary = "文件复制")
    @ResponseBody
    public Result<Object> copy(@RequestParam String id, @RequestParam String key) {
        File file = iFileService.getById(id);
        String toKey = "副本_" + key;
        key = file.getUrl();
        String newUrl = myFileUtils.copyFile(key, toKey);
        File newFile = new File().setName(file.getName()).setFKey(toKey).setSize(file.getSize()).setType(file.getType()).setLocation(file.getLocation()).setUrl(newUrl);
        iFileService.saveOrUpdate(newFile);
        return ResultUtil.data();
    }

    @SystemLog(about = "文件重命名", type = LogType.DATA_CENTER, dotype = "FILE-03")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    @Operation(summary = "文件重命名")
    @ResponseBody
    public Result<Object> upload(@RequestParam String id, @RequestParam String newKey, @RequestParam String newName) {
        File file = iFileService.getById(id);
        String newUrl = "";
        String oldKey = file.getFKey();
        if (!Objects.equals(newKey, oldKey)) {
            oldKey = file.getUrl();
            newUrl = myFileUtils.renameFile(oldKey, newKey);
        }
        file.setName(newName);
        file.setFKey(newKey);
        if (!oldKey.equals(newKey)) {
            file.setUrl(newUrl);
        }
        iFileService.saveOrUpdate(file);
        return ResultUtil.data();
    }

    @SystemLog(about = "文件重命名", type = LogType.DATA_CENTER, dotype = "FILE-04")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Operation(summary = "文件重命名")
    @ResponseBody
    public Result<Object> delete(@RequestParam String[] ids) {
        for (String id : ids) {
            File file = iFileService.getById(id);
            String key = file.getUrl();
            myFileUtils.deleteFile(key);
            iFileService.removeById(id);
        }
        return ResultUtil.data();
    }

    @SystemLog(about = "预览文件", type = LogType.DATA_CENTER, dotype = "FILE-05")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @Operation(summary = "预览文件")
    public void view(@PathVariable String id, @RequestParam(required = false) String filename, @RequestParam(required = false, defaultValue = "false") Boolean preview, HttpServletResponse httpServletResponse) throws Exception {
        File selectFile = iFileService.getById(id);
        if (selectFile == null) {
            throw new Exception("文件不存在");
        }
        if (NullUtils.isNull(filename)) {
            filename = selectFile.getFKey();
        }
        if (!preview) {
            httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        }
        httpServletResponse.setContentLengthLong(selectFile.getSize());
        httpServletResponse.setContentType(selectFile.getType());
        httpServletResponse.addHeader("Accept-Ranges", "bytes");
        if (selectFile.getSize() != null && selectFile.getSize() > 0) {
            httpServletResponse.addHeader("Content-Range", "bytes " + 0 + "-" + (selectFile.getSize() - 1) + "/" + selectFile.getSize());
        }
        myFileUtils.view(selectFile.getUrl(), httpServletResponse);
    }

    public OssSettingVo getOssSetting() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if (s1 == null || s1 == null || s1 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(), s2.getValue(), s3.getValue());
    }
}
