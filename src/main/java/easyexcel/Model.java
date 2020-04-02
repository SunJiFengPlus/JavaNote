package easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author 孙继峰
 * @since 2020/4/6
 */
@Data
public class Model {
    @ExcelProperty("记账时间")
    private Date column1;

    @ExcelProperty("凭证种类")
    private String column2;

    @ExcelProperty("凭证编号")
    private Integer column3;

    @ExcelProperty("科目名称")
    private String column4;

    @ExcelProperty("业务说明")
    private String column5;

    @ExcelProperty("借方发生额")
    private Double column6;

    @ExcelProperty("贷方发生额")
    private Double column7;

    @ExcelProperty("抽样方案")
    private String column8;

    @ExcelProperty("附件")
    private String column9;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return column1.equals(model.column1) &&
                column3.equals(model.column3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column1, column3);
    }
}
