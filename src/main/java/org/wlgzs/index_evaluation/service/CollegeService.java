package org.wlgzs.index_evaluation.service;

import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.College;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author algerfan
 * @since 2019-01-13
 */
public interface CollegeService extends IService<College> {

    Result saveCollege(HttpServletRequest request) throws IOException;
}
