package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 武凯焱
 * @date 2019/3/26 21:46
 * @Description:
 */
@Data
@NoArgsConstructor
@TableName("tb_grade")
public class Grade {
    private static final long serialVersionUID = 1L;
    @TableId(value = "grade_id", type = IdType.AUTO)
    private Integer gradeId;
    @TableField(value = "college_name")
    private String collegeName;


    /**
     * 高考成绩
     */
    @TableField(value = "college_grade")
    private Double collegeGrade;
    @TableField(value = "major_name")
    private String majorName;
    /**
     * 年份
     */
    @TableField(value = "year")
    private Integer year;
}
