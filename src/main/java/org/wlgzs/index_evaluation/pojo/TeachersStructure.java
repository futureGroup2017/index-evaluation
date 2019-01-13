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
    private Integer teaNum;

    /**
     * 研究生学位教师数
     */
    private Integer graNum;

    /**
     * 高级职务教师总数
     */
    private Integer senNum;
}
