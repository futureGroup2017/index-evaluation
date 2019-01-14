package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.pojo.Employment;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.EmploymentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 10:06
 * @Describe
 */
@RestController
@RequestMapping("/em")
@Log4j2
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;

    @RequestMapping("/import")
    public void impor(HttpServletRequest request, Model model) throws IOException {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");
        InputStream in = file.getInputStream();
        List<Employment> teachersStructures = employmentService.importExcelInfo(in, file);

        }
}
