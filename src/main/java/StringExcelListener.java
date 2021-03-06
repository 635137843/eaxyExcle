import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StringList 解析监听器
 *
 * @author zhangcanlong
 * @since 2019-10-21
 */
public class StringExcelListener extends AnalysisEventListener<ExcelTestBean> {

    /**
     * 自定义用于暂时存储data
     * 可以通过实例获取该值
     */
    private List<ExcelTestBean> datas = new ArrayList<>();
    private Map<String, String> heads = new HashMap<>();

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param object  读取后的数据对象
     * @param context 内容
     */
    /*@Override
    public void invoke(Object object, AnalysisContext context) {
        @SuppressWarnings("unchecked") Map<String, String> stringMap = (HashMap<String, String>) object;
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(new ArrayList<>(stringMap.values()));
        //根据自己业务做处理
    }*/

    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        Map<String, String> map = (HashMap<String, String>)headMap;
        heads.putAll(map);
    }

    /*@Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        datas.add(integerStringMap);
    }*/

    @Override
    public void invoke(ExcelTestBean data, AnalysisContext context) {
        datas.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

    /**
     * 返回数据
     *
     * @return 返回读取的数据集合
     **/
    public List<ExcelTestBean> getDatas() {
        return datas;
    }

    /**
     * 返回数据
     *
     * @return 返回读取的数据集合
     **/
    public Map<String, String> getHeads() {
        return heads;
    }

    /**
     * 设置读取的数据集合
     *
     * @param datas 设置读取的数据集合
     **/
    public void setDatas(List<ExcelTestBean> datas) {
        this.datas = datas;
    }
}