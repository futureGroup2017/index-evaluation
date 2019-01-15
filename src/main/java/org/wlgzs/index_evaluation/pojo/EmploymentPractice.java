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
     * "参赛人数比-1(职业生
     * 涯规划大赛"
     */
    private double m11;
    /**
     * "参赛人数比-1
     * (创业大赛）"
     */
    private double m12;
    /**
     * "参赛人数比2
     * -省创业大赛总人数"
     */
    private double m13;
    /**
     * 处理数据
     * M1: 参赛人数比47.5
     */
    @TableField("people_number")
    private double peopleNumber;

    /**
     * "获奖质量积分-1(生
     * 涯规划大赛"
     */
    private double m21;
    /**
     * "获奖质量积分-1
     * (创业大赛）"
     */
    private double m22;
    /**
     * 处理数据
     * M2: 获奖质量比52.5
     */
    private double quality;

    /**
     *
     * 处理数据
     * 参赛状态39
     */
    @TableField("participation_status")
    private double participationStatus;

    /**
     * 项目数量
     */
    private double m31;
    /**
     * 处理数据
     * M3: 项目数量比47
     */
    @TableField("project_number")
    private double projectNumber;

    /**
     * 项目质量
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
     * 创业项目28.5
     */
    @TableField("venture_project")
    private double ventureProject;

    /**
     *
     * 处理数据
     * 特色工作32.5
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
