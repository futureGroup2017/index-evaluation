package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 武凯焱
 * @date 2019/1/13 10:59
 * @Description:
 */
@Data
@NoArgsConstructor
@TableName("tb_major")
public class Major {
    private static final long serialVersionUID = 1L;

    @TableId(value = "major_id", type = IdType.AUTO)
    private Integer majorId;
    @TableField("major_name")
    private String majorName;
}
