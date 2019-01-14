package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.College;
import org.wlgzs.index_evaluation.service.CollegeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  CollegeController层
 * </p>
 *
 * @author algerfan
 * @since 2019-01-13
 */
@RestController
@RequestMapping("/college")
@Log4j2
public class CollegeController {

    @Resource
    private CollegeService collegeService;

    /**
     * 新增学院
     * @param college
     */
    @PostMapping
    public Result save(College college){
        if(college == null) {
            return new Result(ResultCodeEnum.UNSAVE);
        }
        collegeService.save(college);
        return new Result(ResultCodeEnum.SAVE);
    }

    /**
     * 通过id删除
     * @param collegeId
     */
    @DeleteMapping("/{collegeId}")
    public Result delete(@PathVariable("collegeId") int collegeId){
        if(collegeId==0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        collegeService.removeById(collegeId);
        return new Result(ResultCodeEnum.DELETE);
    }

    /**
     * 修改学院
     * @param college
     */
    @PutMapping
    public Result updateById(College college){
        if(college == null) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        collegeService.updateById(college);
        return new Result(ResultCodeEnum.UPDATE);
    }

    /**
     * 通过id查询学院
     * @param collegeId
     */
    @RequestMapping("/id")
    public Result selectById(int collegeId){
        if(collegeId==0) {
            return new Result(ResultCodeEnum.UNFIND);
        }
        return new Result(ResultCodeEnum.FIND,collegeService.getById(collegeId));
    }

    /**
     * 分页查询
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @GetMapping("/page")
    public ModelAndView findAllPage(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        Page<College> page = new Page<>(pageNum,pageSize);
        QueryWrapper<College> wrapper = new QueryWrapper<>();
        IPage<College> pageList = collegeService.page(page,wrapper);
        model.addAttribute("current",pageList.getCurrent());  //当前页数
        model.addAttribute("pages",pageList.getPages());   //总页数
        model.addAttribute("college",pageList.getRecords());   //集合
        model.addAttribute("msg","查询成功");
        log.info("查询成功:"+pageList.getRecords());
        return new ModelAndView("test");
    }

    /**
     * 批量导入学院
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveCollege")
    public Result saveCollege(HttpServletRequest request) throws Exception{
        return collegeService.saveCollege(request);
    }

}
