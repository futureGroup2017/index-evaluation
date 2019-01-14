package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.service.MajorService;
import javax.annotation.Resource;
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
    //添加专业
    @PostMapping
    public Result add(Major major){
        if (major!=null){
         majorService.save(major);
         return new Result(ResultCodeEnum.SAVE);
        }else {
            return new Result(ResultCodeEnum.UNSAVE);
        }
    }
    //删除专业
    @DeleteMapping("/{majorId}")
    public Result delete(@PathVariable("majorId") int majorId ){
        if (majorId!=0){
            majorService.removeById(majorId);
            return new Result(ResultCodeEnum.DELETE);
        }
        else
            return new Result(ResultCodeEnum.UNDELETE);
    }
    //修改专业
    @PutMapping()
    public Result update(Major major){
        if (major!=null){
            majorService.save(major);
            return new Result(ResultCodeEnum.UPDATE);
        }else
            return new Result(ResultCodeEnum.UNUPDATE);
    }
    //查询全部专业
    @GetMapping()
    public List<Major> findAll(Model model, @RequestParam(name="pageNum",defaultValue = "1") int pageNum, @RequestParam(name="pageSize",defaultValue = "10") int pageSize){
        Page<Major> page  = new Page<>(pageNum,pageSize);
        QueryWrapper<Major>  wrapper = new QueryWrapper<>();
        IPage<Major> pageList = majorService.page(page,wrapper);
        return pageList.getRecords();
    }
}
