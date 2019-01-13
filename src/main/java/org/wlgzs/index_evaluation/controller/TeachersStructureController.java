package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.TeachersStructureService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-13 10:41
 * @Describe
 */
@RestController
@RequestMapping("/ts")
@Log4j2
public class TeachersStructureController {

    @Autowired
    private TeachersStructureService teachersStructureService;

    @RequestMapping("/import")
    public void impor(HttpServletRequest request, Model model) throws IOException {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");
        InputStream in = file.getInputStream();
        List<TeachersStructure> teachersStructures = teachersStructureService.importExcelInfo(in, file);
        for (TeachersStructure t:teachersStructures){
            log.info(t);
            Integer add = teachersStructureService.add(t);
            log.info(add);
        }

    }
}
