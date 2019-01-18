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
     * 专业名字
     */
    @TableField(value = "major_name")
    private String majorName;
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

    /**
     * 专业认可度

     */
    @TableField(value = "major_recognition")
    private Double majorRecognition;
    /**
     * 高考成绩
     */
    @TableField(value = "college_entrance")
    private  Double collegeEntrance;
    /**
     * 专业优势
     */
    @TableField(value = "major_advantage")
    private Double majorAdvantage;
    /**
     * 年份
     */
    @TableField("year")
    private Integer year;
    public StudentQuality(String majorName, Integer fistVolunteerNum, Integer studentsNum, Integer afterVolunteerNum, Double averageScore, Double majorRecognition, Double collegeEntrance, Double majorAdvantage,Integer year) {
        this.majorName = majorName;
        this.fistVolunteerNum = fistVolunteerNum;
        this.studentsNum = studentsNum;
        this.afterVolunteerNum = afterVolunteerNum;
        this.averageScore = averageScore;
        this.majorRecognition = majorRecognition;
        this.collegeEntrance = collegeEntrance;
        this.majorAdvantage = majorAdvantage;
        this.year = year;
    }

    public StudentQuality(String majorName, Double averageScore, Double majorRecognition, Double collegeEntrance, Double majorAdvantage, Integer year) {
        this.majorName = majorName;
        this.averageScore = averageScore;
        this.majorRecognition = majorRecognition;
        this.collegeEntrance = collegeEntrance;
        this.majorAdvantage = majorAdvantage;
        this.year = year;
    }


    @Override
    public String toString() {
        return "StudentQuality{"+" majorName='" + majorName + '\'' +
                ", fistVolunteerNum=" + fistVolunteerNum +
                ", studentsNum=" + studentsNum +
                ", afterVolunteerNum=" + afterVolunteerNum +
                ", averageScore=" + averageScore +
                ", majorRecognition=" + majorRecognition +
                ", collegeEntrance=" + collegeEntrance +
                ", majorAdvantage=" + majorAdvantage +
                '}';
    }
}
