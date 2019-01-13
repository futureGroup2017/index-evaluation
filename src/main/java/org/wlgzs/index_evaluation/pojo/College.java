package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  CollegeController实体类
 * </p>
 *
 * @author algerfan
 * @since 2019-01-13
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_college")
public class College extends Model<College> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "college_id", type = IdType.AUTO)
    private Integer collegeId;
    @TableField("college_name")
    private String collegeName;

    @Override
    protected Serializable pkVal() {
        return this.collegeId;
    }

}
