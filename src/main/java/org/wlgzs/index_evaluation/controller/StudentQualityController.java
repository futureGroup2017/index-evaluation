package org.wlgzs.index_evaluation.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.service.StudentQualityService;

import javax.annotation.Resource;
import java.io.IOException;

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
    @PostMapping("import")
    public Result importExcel(MultipartFile file, String year){
        try {
          boolean isTrue =  studentQualityService.importExcel(file, year);
          if (isTrue){
              return new Result(1,"导入成功");
          }
          else {
              return new Result(0,"导入失败");
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(1,"导入成功");
    }


}
