package org.wlgzs.index_evaluation.service;

import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.StudentQuality;

import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:34
 * @Description:
 */
public interface StudentQualityService {
     boolean importExcel(MultipartFile file, String year) throws IOException;
     void add(List<StudentQuality> studentQualityList);
}
