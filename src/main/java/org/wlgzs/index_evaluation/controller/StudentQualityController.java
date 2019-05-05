package org.wlgzs.index_evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.MajorService;
import org.wlgzs.index_evaluation.service.StudentQualityService;
import org.wlgzs.index_evaluation.service.YearService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:31
 * @Description:
 */
@RestController()
@RequestMapping("/sq")
public class StudentQualityController {
    @Resource
    StudentQualityService studentQualityService;
    @Resource
    MajorService majorService;
    @Resource
    YearService yearService;

    /*//导入生源质量原始数据表
    @PostMapping("import")
    public Result importExcel(@RequestParam(value = "file", required = false) MultipartFile file, String year) {
        String fileName = file.getOriginalFilename();
        boolean isContion = fileName.equals("1.生源质量指数样表.xlsx");
        if (!isContion) {
            return new Result(-1, "请确认文件名是否为--<1.生源质量指数样表.xlsx>");
        }
        QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
        if (year != null && !year.equals("")) {
            queryWrapper.eq("year", Integer.parseInt(year));
            queryWrapper.last("limit 2");
            List<StudentQuality> list = studentQualityService.list(queryWrapper);
            if (list != null && list.size() > 0) {
                return new Result(0, "导入数据重复");
            }
        }
        try {
            boolean isTrue = studentQualityService.importExcel(file, year);
            if (isTrue) {
                return new Result(1, "导入成功");
            } else {
                return new Result(-1, "导入失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(1, "导入成功");
    }
*/
    //按照专业和年份来查询结果数据
    @GetMapping("/search")
    public ModelAndView search(Model model, Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "60") int pageSize) {
        QueryWrapper<Major> queryW = new QueryWrapper<>();
        List<Major> majors = majorService.list(queryW);
        if (majors == null || majors.size() <= 0) {
            model.addAttribute("mark", -1);
            return new ModelAndView("Being-interviewed");
        } else {
            model.addAttribute("mark", 1);
        }
        Page<StudentQuality> page = new Page<>(pageNum, pageSize);
        QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<StudentQuality>();
        if (query.getYear() != null && !query.getYear().equals("")) {
            queryWrapper.eq("year", query.getYear());
        }
        if (query.getMajorName() != null && !query.getMajorName().equals("")) {
            queryWrapper.eq("major_name", query.getMajorName()).or().eq("colleage_name", query.getMajorName());
        }
        List<Year> allYear = yearService.findAllYear();
        model.addAttribute("allYear", allYear);
        IPage<StudentQuality> iPage = studentQualityService.page(page, queryWrapper);
        model.addAttribute("current", iPage.getCurrent());//当前页数
        model.addAttribute("pages", iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions", iPage.getRecords());//所有的数据集合
        model.addAttribute("query", query);
        return new ModelAndView("Being-interviewed");
    }

    //按照年份删除相应的结果数据
    @GetMapping("/delete")
    public Result delete(Integer year) {
        boolean isDelete = studentQualityService.delete(year);
        if (isDelete) {
            return new Result(1, "删除成功");
        } else {
            return new Result(-1, "没有该年份数据");
        }
    }

    //按照学院名字来查询结果数据
    @GetMapping("/searchColleage")
    public ModelAndView searchColleage(Model model, Query query,
                                       @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        Page<StudentQuality> page = new Page<>(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (query.getYear() != null) {
            queryWrapper.eq("year", query.getYear());
        }
        if (query.getMajorName() != null) {
            queryWrapper.eq("colleage_name", query.getMajorName());
        }
        List<Year> allYear = yearService.findAllYear();
        model.addAttribute("allYear", allYear);
        IPage<StudentQuality> iPage = studentQualityService.page(page, queryWrapper);
        model.addAttribute("current", iPage.getCurrent());//当前页数
        model.addAttribute("pages", iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions", iPage.getRecords());//所有的数据集合
        model.addAttribute("query", query);
        return new ModelAndView("Enrolment");
    }

    @GetMapping("export")
    public void exportExcell(@RequestParam("year") String year, HttpServletResponse response) throws IOException {
        studentQualityService.exportData(Integer.parseInt(year), response);
    }
    /*

        @GetMapping("test")
        public List<StudentQuality> test(@RequestParam("year") int year) {
            return studentQualityService.getQualityIndex(year);
        }
    */
    @GetMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response) {
        studentQualityService.download(response);
    }

    @PostMapping("/import")
    public Result upload (@RequestParam(value = "file", required = false) MultipartFile file, String year) throws IOException {
        String fileName = file.getOriginalFilename();
        boolean isContion = fileName.equals("生源质量原始数据.zip");
        if (!isContion) {
            return new Result(-1, "请确认文件名是否为--<生源质量原始数据.zip>");
        }
        QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
        if (year != null && !year.equals("")) {
            queryWrapper.eq("year", Integer.parseInt(year));
            queryWrapper.last("limit 2");
            List<StudentQuality> list = studentQualityService.list(queryWrapper);
            if (list != null && list.size() > 0) {
                return new Result(0, "导入数据重复");
            }
        }
        if (studentQualityService.upload(file, year)) {
            return new Result(1, "导入成功");
        } else {
            return new Result(-1, "导入失败");
        }
    }
}
