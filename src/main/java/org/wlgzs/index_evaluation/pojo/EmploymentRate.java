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
 * @date Created in 2019/1/13 17
 * @Description 就业率指数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("tb_e_rate")
public class EmploymentRate extends Model<EmploymentRate> {

    /**
     * 就业率指数id
     */
    @TableId(value = "employment_rate_id",type = IdType.AUTO)
    private int employmentRateId;
    /**
     * 学院
     */
    private String college;
    /**
     * 初次就业率
     */
    @TableField("first_employment_rate")
    private String firstEmploymentRate;
    /**
     * 年终就业率
     */
    @TableField("last_employment_rate")
    private String lastEmploymentRate;
    /**
     * 初次就业率指数=初次就业率*100*0.2495
     */
    @TableField("first_index")
    private String firstIndex;
    /**
     * 年终就业率指数=年终就业率*100*0.586
     */
    @TableField("last_index")
    private String lastIndex;
    /**
     * 就业率指数=（初次就业率指数+年终就业率指数）*0.2495
     */
    @TableField("employment_rate_index")
    private String employmentRateIndex;
    /**
     * 年份
     */
    private int year;

    @Override
    protected Serializable pkVal() {
        return this.employmentRateId;
    }
}
