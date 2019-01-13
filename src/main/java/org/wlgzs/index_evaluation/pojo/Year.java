package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:24
 * @Describe 年份实体类
 */
@Data
@NoArgsConstructor
@TableName("tb_year")
public class Year implements Serializable{
    /**
     * 年份id
     */
    @TableId(type = IdType.AUTO)
    private Integer yearId;
    /**
     * 年份
     */
    private Integer yearName;
}
