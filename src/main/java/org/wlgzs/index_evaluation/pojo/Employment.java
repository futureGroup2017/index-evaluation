package org.wlgzs.index_evaluation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 9:40
 * @Describe 就业状态指数
 */
@Data
@TableName("tb_emp")
public class Employment {

    /**
     * 就业状态ID
     */
    @TableId(type = IdType.AUTO)
    private Integer emId;

    /**
     * 学院
     */
    private String college;

    /**
     * 参与调查人数
     */
    private Integer parNum;

    /**
     * 专业知识能力：非常强，很强，一般，不强，很不强
     */
    private Integer MB1111;
    private Integer MB1112;
    private Integer MB1113;
    private Integer MB1114;
    private Integer MB1115;

    /**
     * 通用知识能力：非常强，很强，一般，不强，很不强
     */
    private Integer MB1121;
    private Integer MB1122;
    private Integer MB1123;
    private Integer MB1124;
    private Integer MB1125;

    /**
     * 求职应聘能力：非常强，很强，一般，不强，很不强
     */
    private Integer MB1131;
    private Integer MB1132;
    private Integer MB1133;
    private Integer MB1134;
    private Integer MB1135;

    /**
     * 社会兼职经历：1周，半月，1个月，2个月，3个月以上
     */
    private Integer MB1211;
    private Integer MB1212;
    private Integer MB1213;
    private Integer MB1214;
    private Integer MB1215;

    /**
     * “非学历、费荣誉”证书：3个以上，3个，2个，1个，0个
     */
    private Integer MB1221;
    private Integer MB1222;
    private Integer MB1223;
    private Integer MB1224;
    private Integer MB1225;

    /**
     * 社会职务：有，无
     */
    private Integer MB1231;
    private Integer MB1232;

    /**
     * 求职积极程度：很积极，积极，一般，不积极，很不积极
     */
    private Integer MB1311;
    private Integer MB1312;
    private Integer MB1313;
    private Integer MB1314;
    private Integer MB1315;

    /**
     * 自我效能感人数：很自信，自信，一般，不自信，很不自信
     */
    private Integer MB1321;
    private Integer MB1322;
    private Integer MB1323;
    private Integer MB1324;
    private Integer MB1325;

    /**
     * 就业起薪：2001-3000，3001-4000，4001-5000，5001-6000，6001-7000，7000以上
     */
    private Integer MB211;
    private Integer MB212;
    private Integer MB213;
    private Integer MB214;
    private Integer MB215;
    private Integer MB216;

    /**
     * 专业对口状态：很对口，对口，一般，不对口，很不对口
     */
    private Integer MB2211;
    private Integer MB2212;
    private Integer MB2213;
    private Integer MB2214;
    private Integer MB2215;

    /**
     * “能力-岗位”适配度：很匹配，匹配，一般，不匹配，很不匹配
     */
    private Integer MB2221;
    private Integer MB2222;
    private Integer MB2223;
    private Integer MB2224;
    private Integer MB2225;

    /**
     * 月薪兑付状态：正常，拖欠
     */
    private Integer MB2311;
    private Integer MB2312;

    /**
     * “五险一金”执行状态：正常，拖欠
     */
    private Integer MB2321;
    private Integer MB2322;

    /**
     * 成长发展空间：很宽广，宽广，一般，不宽广，很不宽广
     */
    private Integer MB2331;
    private Integer MB2332;
    private Integer MB2333;
    private Integer MB2334;
    private Integer MB2335;

    /**
     * 工作满意度：很满意，满意，一般，不满意，很不满意
     */
    private Integer MB2341;
    private Integer MB2342;
    private Integer MB2343;
    private Integer MB2344;
    private Integer MB2345;

    /**
     * 预期就业年限：10年以上，8-10年，3-7年，2年，1年
     */
    private Integer MB241;
    private Integer MB242;
    private Integer MB243;
    private Integer MB244;
    private Integer MB245;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 知识能力结构40.75
     */
    private double B11;

    /**
     * 标识性优势31.35
     */
    private double B12;

    /**
     * 择业精神27.9
     */
    private double B13;

    /**
     * 就业起薪28.55
     */
    private double B21;

    /**
     * 岗位胜任度24.2
     */
    private double B22;

    /**
     * 就业现状满意度28
     */
    private double B23;

    /**
     * 预期就业年限19.25
     */
    private double B24;

    /**
     * 个体就业潜力44.8
     */
    private double A1;

    /**
     * 个体就业表现55.2
     */
    private double A2;

    /**
     * 就业状态指数25.3
     */
    private double employmentStatus;
}
