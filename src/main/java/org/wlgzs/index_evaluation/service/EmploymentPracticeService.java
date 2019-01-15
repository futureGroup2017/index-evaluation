package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.EmploymentPractice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
public interface EmploymentPracticeService extends IService<EmploymentPractice> {

    Result importData(int year, HttpServletRequest request);

    Result deleteYear(int year);

    void exportData(int year, HttpServletResponse response) throws IOException;
}
