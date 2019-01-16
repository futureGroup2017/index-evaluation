package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 武凯焱
 * @date 2019/1/15 15:49
 * @Description:
 */
@Data
@NoArgsConstructor
@TableName("tb_student_quality")
public class StudentQuality {
    private static final long serialVersionUID = 1L;
    /**
     * 生源质量Id
     */
    @TableId(value = "quality_id", type = IdType.AUTO)
    private Integer qualityId;
    /**
     * 专业1志愿报考人数
     */
    @TableField(value = "fist_volunteer_num")
    private Integer fistVolunteerNum;

    /**
     * 学生实际进档人数
     */
    @TableField(value = "students_num")
    private Integer studentsNum;

    /**
     * 专业2-5志愿报考总人次
     */
    @TableField(value = "after_volunteer_num")
    private Integer afterVolunteerNum;
    /**
     *录取平均分
     */
    @TableField(value = "average_score")
    private Double averageScore;







}
