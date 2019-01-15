package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.service.EmployerSatisfactionService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/13 17:48
 * @Description:
 */
@RestController
@RequestMapping("/es")
@Log4j2
public class EmployerSatisfactionController {

    @Resource
    private EmployerSatisfactionService  empService;

    @PostMapping("/import")
    public void importExcel(@RequestParam("file") MultipartFile multipartFile,String year) throws IOException {
        List<EmployerSatisfaction> list = empService.importExcel(multipartFile,year);
        boolean isTrue =  empService.add(list);
        System.out.println(isTrue);
        }

}
