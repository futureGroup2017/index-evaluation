package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     boolean importExcel(MultipartFile file, String year) throws IOException;
     void add(List<StudentQuality> studentQualityList);
     //删除一个年份的数据
     boolean delete( Integer year);
     void exportData(int year,HttpServletResponse response)throws IOException;

}
