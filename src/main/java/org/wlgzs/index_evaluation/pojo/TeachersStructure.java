package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-13 10:11
 * @Describe 师资结构表的测量
 */
@Data
@TableName("tb_ts")
public class TeachersStructure {

    /**
     * 师资结构ID
     */
    @TableId(type = IdType.AUTO)
    private Integer tsId;

    /**
     * 学院
     */
    private String collegeName;

    /**
     * 在校生数
     */
    private Integer stuNum;

    /**
     * 教师总数
     */
    private double teaNum;

    /**
     * 研究生学位教师数
     */
    private Integer graNum;

    /**
     * 高级职务教师总数
     */
    private Integer senNum;

    /**
     * 生师比
     */
    private double B21;

    /**
     * 研究生学位教师占教师总数比
     */
    private double B22;

    /**
     * 高级职称教师占教师总数比
     */
    private double B23;

    /**
     * 生师比合格值
     */
    private Integer qualified;

    /**
     * 生师比积分
     */
    private double M1;

    /**
     * 研究生学位教师积分
     */
    private double M2;

    /**
     * 高级职务教师积分
     */
    private double M3;

    /**
     * 生师比33.7
     */
    private double W1;

    /**
     * 高学历教师占比32.9
     */
    private double W2;

    /**
     * 高职称教师占比33.9
     */
    private double W3;

    /**
     * 师资结构指数
     */
    private double A2;

    /**
     * 年份
     */
    private Integer year;
}
