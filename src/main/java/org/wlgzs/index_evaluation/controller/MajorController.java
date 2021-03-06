package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.service.MajorService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/13 11:11
 * @Description:
 */
@RestController
@RequestMapping("/major")
@Log4j2
public class MajorController {
    @Resource
    MajorService majorService;

    //导入专业与学院关系表
    @PostMapping("/import")
    public Result importExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String str = file.getOriginalFilename();//.equals("");
        if (!str.equals("1.专业学院对应关系样表.xlsx")) {
            return new Result(-1, "请确认文件名是否为--<1.专业学院对应关系样表.xlsx>");
        }
        QueryWrapper<Major> queryW = new QueryWrapper<>();
        List<Major> majors = majorService.list(queryW);
        if (majors != null && majors.size() >= 0) {
            for (Major major : majors
                    ) {
                majorService.removeById(major.getMajorId());
            }

        }
        boolean isTrue = majorService.importExcel(file);
        return new Result(1, "导入成功");
    }

    //添加专业
    @PostMapping
    public Result add(Major major) {
        if (major != null) {
            majorService.save(major);
            return new Result(ResultCodeEnum.SAVE);
        } else {
            return new Result(ResultCodeEnum.UNSAVE);
        }
    }

    //删除专业
    @DeleteMapping("/{majorId}")
    public Result delete(@PathVariable("majorId") int majorId) {
        if (majorId != 0) {
            majorService.removeById(majorId);
            return new Result(ResultCodeEnum.DELETE);
        } else
            return new Result(ResultCodeEnum.UNDELETE);
    }

    //修改专业
    @PutMapping()
    public Result update(Major major) {
        if (major != null) {
            majorService.save(major);
            return new Result(ResultCodeEnum.UPDATE);
        } else
            return new Result(ResultCodeEnum.UNUPDATE);
    }

    //查询全部专业
    @GetMapping()
    public List<Major> findAll(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<Major> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        IPage<Major> pageList = majorService.page(page, wrapper);
        return pageList.getRecords();
    }
}
