package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 武凯焱
 * @date 2019/1/14 8:11
 * @Description:
 */
@Data
@NoArgsConstructor
@TableName("tb_es")
public class EmployerSatisfaction {

    private static final long serialVersionUID = 1L;
    /**
     * 用人单位满意度Id
     */
    @TableId(value = "es_id", type = IdType.AUTO)
    private Integer esId;
    /**
     * 学院
     */
    @TableField(value = "college")
    private String college;
    /**
     * 毕业生的精神状态与工作水平
     */
    @TableField(value = "level")
    private Double level;
    /**工作水平非常满意人数
     *
     */
    @TableField(value = "level1")
    private int level1;
    /**
     * 工作水平满意人数
     */
    @TableField(value = "level2")
    private int level2;
    /**
     * 工作水平一般人数
     */
    @TableField(value = "level3")
    private int level3;
    /**
     * 工作水平不满意人数
     */
    @TableField(value = "level4")
    private int level4;
    /**
     * 工作水平非常不满意人数
     */
    @TableField(value = "level5")
    private int level5;
    /**
     * 毕业生的综合素质能力
     */
    @TableField(value = "ability")
    private Double ability;
    /**
     * 素质能力非常满意人数
     */
    @TableField(value = "ability1")
    private int ability1;
    /**
     * 素质能力满意人数
     */
    @TableField(value = "ability2")
    private int ability2;
    /**
     * 素质能力一般人数
     */
    @TableField(value = "ability3")
    private int ability3;
    /**
     * 素质能力不满意人数
     */
    @TableField(value = "ability4")
    private int ability4;
    /**
     * 素质能力非常不满意人数
     */
    @TableField(value = "ability5")
    private int ability5;
    /**
     * 毕业生的“能力-岗位”匹配度
     */
    @TableField(value = "matched")
    private Double match;
    /**
     * 匹配度非常满意人数
     */
    @TableField(value = "match1")
    private int match1;
    /**
     * 匹配度满意人数
     */
    @TableField(value = "match2")
    private int match2;
    /**
     * 匹配度一般人数
     */
    @TableField(value = "match3")
    private int match3;
    /**
     * 匹配度不满意人数
     */
    @TableField(value = "match4")
    private int match4;
    /**
     * 匹配度非常不满意人数
     */
    @TableField(value = "match5")
    private int match5;
    /**
     * 对毕业生的工作满意度
     */
    @TableField(value = "satisfaction")
    private Double satisfaction;
    /**
     * 工作满意度非常满意人数
     */
    @TableField(value = "satisfaction1")
    private int satisfaction1;
    /**
     * 工作满意度满意人数
     */
    @TableField(value = "satisfaction2")
    private int satisfaction2;
    /**
     * 工作满意度一般人数
     */
    @TableField(value = "satisfaction3")
    private int satisfaction3;
    /**
     * 工作满意度不满意人数
     */
    @TableField(value = "satisfaction4")
    private int satisfaction4;
    /**
     * 工作满意度非常不满意人数
     */
    @TableField(value = "satisfaction5")
    private int satisfaction5;
    /**
     * 招聘毕业生的持续度
     */
    @TableField(value = "sustain")
    private Double sustain;
    /**
     * 招聘毕业生的持续度非常满意人数
     */
    @TableField(value = "sustain1")
    private int sustain1;
    /**
     * 招聘毕业生的持续度满意人数
     */
    @TableField(value = "sustain2")
    private int sustain2;
    /**
     * 招聘毕业生的持续度一般人数
     */
    @TableField(value = "sustain3")
    private int sustain3;
    /**
     * 招聘毕业生的持续度不满意人数
     */
    @TableField(value = "sustain4")
    private int sustain4;
    /**
     * 招聘毕业生的持续度非常不满意人数
     */
    @TableField(value = "sustain5")
    private int sustain5;
    /**
     * 年份
     */
    @TableField(value = "year")
    private int year;
    public EmployerSatisfaction(String college, Double level, int level1, int level2, int level3, int level4, int level5, Double ability, int ability1, int ability2, int ability3, int ability4, int ability5, Double match, int match1, int match2, int match3, int match4, int match5, Double satisfaction, int satisfaction1, int satisfaction2, int satisfaction3, int satisfaction4, int satisfaction5, int year) {
        this.college = college;
        this.level = level;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.ability = ability;
        this.ability1 = ability1;
        this.ability2 = ability2;
        this.ability3 = ability3;
        this.ability4 = ability4;
        this.ability5 = ability5;
        this.match = match;
        this.match1 = match1;
        this.match2 = match2;
        this.match3 = match3;
        this.match4 = match4;
        this.match5 = match5;
        this.satisfaction = satisfaction;
        this.satisfaction1 = satisfaction1;
        this.satisfaction2 = satisfaction2;
        this.satisfaction3 = satisfaction3;
        this.satisfaction4 = satisfaction4;
        this.satisfaction5 = satisfaction5;
        this.year = year;
    }

}
