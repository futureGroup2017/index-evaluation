package org.wlgzs.index_evaluation.pojo;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

@Data
public class ExcelBean implements  java.io.Serializable{  
    private String headTextName; //列头（标题）名  
    private String propertyName; //对应字段名  
    private Integer cols; //合并单元格数  
    private XSSFCellStyle cellStyle;
}
