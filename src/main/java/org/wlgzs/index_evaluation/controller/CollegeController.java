package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.College;
import org.wlgzs.index_evaluation.service.CollegeService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    * 新增
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
    * 修改
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
    * 通过id查询
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

    @RequestMapping("/to")
    public ModelAndView to(){
        return new ModelAndView("test");
    }
    /**
     * 批量导入学院
     * @param in
     * @param file
     */
    @RequestMapping("/upload")
    public Result upload(InputStream in, @RequestParam("file") MultipartFile file){
        if(file.getOriginalFilename()==null){
            Result result = new Result(ResultCodeEnum.UNSAVE);
            result.setMsg("上传失败");
            return result;
        }
        List<List<Object>> lists;
        try {
            lists = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            List<College> colleges = new ArrayList<College>();
            //遍历listob数据，把数据放到List中
            for (List<Object> ob : lists) {
                College college = new College();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                college.setCollegeName(String.valueOf(ob.get(0)));
                colleges.add(college);
            }
            //批量插入
            for (College college1 : colleges) {
                collegeService.save(college1);
            }
            return new Result(ResultCodeEnum.SAVE,colleges);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ResultCodeEnum.UNSAVE);
    }

}
