package easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙继峰
 * @since 2020/4/6
 */
public class DemoDataListener extends AnalysisEventListener<Model> {
    public Set<Model> getSet() {
        return set;
    }

    private Set<Model> set = new HashSet<>();

    @Override
    public void invoke(Model model, AnalysisContext analysisContext) {
        set.add(model);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
