package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
    /**
     * 专业名称
     */
    @TableField("major_name")
    private String majorName;
    /**
     * 学院名字
     */
    @TableField("collage_name")
    private  String collage_name;

    public Major(String majorName, String collage_name) {
        this.majorName = majorName;
        this.collage_name = collage_name;

    }
}
