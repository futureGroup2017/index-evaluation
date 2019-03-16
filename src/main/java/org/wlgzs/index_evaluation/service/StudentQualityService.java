package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.StudentQuality;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:34
 * @Description:
 */
public interface StudentQualityService extends IService<StudentQuality> {
     //导入excel数据
     @Transactional
     boolean importExcel(MultipartFile file, String year) throws IOException;
     @Transactional
     void add(List<StudentQuality> studentQualityList);
     //删除一个年份的数据
     @Transactional
     boolean delete( Integer year);
     //导出一个年份结果数据
     @Transactional
     void exportData(int year,HttpServletResponse response)throws IOException;
     //获取所有学院生源质量指数
     List<StudentQuality> getQualityIndex(int year);
    void download(HttpServletResponse response);


}
