package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.Major;

import java.io.IOException;

/**
 * @author 武凯焱
 * @date 2019/1/13 11:15
 * @Description:
 */
public interface MajorService extends IService<Major> {
    //导入excel数据
    boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException;


}
