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
 * @Description
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_employment_rate")
public class EmploymentRate extends Model<EmploymentRate> {

    @TableId(value = "employment_rate_id",type = IdType.AUTO)
    private int EmploymentRateId;
    @TableField("college")
    private String college;
    @TableField("first_employment_rate")
    private String firstEmploymentRate;
    @TableField("last_employment_rate")
    private String lastEmploymentRate;
    @TableField("first_index")
    private String firstIndex;
    @TableField("last_index")
    private String lastIndex;
    @TableField("employment_rate_index")
    private String employmentRateIndex;
    @TableField("year")
    private int year;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
