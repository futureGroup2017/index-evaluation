package org.wlgzs.index_evaluation.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    //状态码
    private int code;
    //状态信息
    private String msg;
    //返回数据
    private Object data;

    public Result (ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public Result (ResultCodeEnum resultCodeEnum , Object data) {
        this(resultCodeEnum);
        this.data = data;
    }
}