package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("tb_e_practice")
public class EmploymentPractice extends Model<EmploymentPractice> {

    /**
     * 就业创业实践指数id
     */
    @TableId(value = "employment_practice_id", type = IdType.AUTO)
    private int EmploymentPracticeId;
    /**
     * 学院
     */
    private String college;
    /**
     * 参赛人数比-1(职业生涯规划大赛)
     */
    private double m11;
    /**
     * 参赛人数比-1(简历大赛)
     */
    private double m12;
    /**
     * 参赛人数比-1(创业大赛)
     */
    private double m13;
    /**
     * 参赛人数2-省创业大赛
     */
    private double m14;
    /**
     * 处理数据
     * M1: 总参赛人数比47.5
     */
    @TableField("people_number")
    private double peopleNumber;

    /**
     * 获奖质量积分-1(生涯规划大赛)
     */
    private double m21;
    /**
     * 获奖质量积分-1(简历大赛)
     */
    private double m22;
    /**
     * 获奖质量积分-1(创业大赛)
     */
    private double m23;
    /**
     * 获奖质量积分-2-省创业大赛
     */
    private double m24;
    /**
     * 处理数据
     * M2: 总获奖质量比52.5
     */
    private double quality;


    /**
     * 项目数量积分
     */
    private double m31;
    /**
     * 处理数据
     * M3: 项目数量比47
     */
    @TableField("project_number")
    private double projectNumber;

    /**
     * 项目质量积分
     */
    private double m41;
    /**
     * 处理数据
     * M4: 项目质量比53
     */
    @TableField("project_quality")
    private double projectQuality;


    /**
     *
     * 处理数据
     * M5: 特色工作32.5
     */
    @TableField("featured_work")
    private double featuredWork;

    /**
     * 处理数据
     * 就业创业实践指数
     */
    private double practice;
    /**
     * 年份
     */
    private int year;

    @Override
    protected Serializable pkVal() {
        return this.EmploymentPracticeId;
    }
}
