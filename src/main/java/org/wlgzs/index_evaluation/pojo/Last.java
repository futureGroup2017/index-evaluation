package org.wlgzs.index_evaluation.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlgerFan
 * @date Created in 2019/1/21 16
 * @Description
 */
@Data
@NoArgsConstructor
public class Last {

    /**
     * 学院
     */
    private String college;
    /**
     * 生源质量指数10.08
     */
    private String studentQuality;
    /**
     * 师资结构指数11.07
     */
    private String teachersStructure;
    /**
     * 就业状态指数25.3
     */
    private String employment;
    /**
     * 就业率指数24.95
     */
    private String employmentRate;
    /**
     * 用人满意度指数13.25
     */
    private String employerSatisfaction;
    /**
     * 就业创业实践指数15.35
     */
    private String employmentPractice;
    /**
     * 就业竞争力指数
     */
    private String lastEmployment;

    public Last(String college) {
        this.college = college;
    }
}
