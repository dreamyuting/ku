package com.yaorange.web;

import com.yaorange.entity.vo.PieDataVo;
import com.yaorange.service.ContractProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ProductSale")
public class ProductSaleHandler {
    @Resource(name = "contractProductService")
    private ContractProductService contractProductService;
    @GetMapping("/map")
    public Map<String,Object> productSale(){
        //调用业务方法获取销售统计结果
        List<Object[]> productSale = contractProductService.findProductSale();
        //定义标题集合
        List<String> titleList = new ArrayList<String>();
        //定义数据集合
        List<PieDataVo> dataList = new ArrayList<PieDataVo>();
        //遍历集合，将每行数据拆分为标题集合和数据集合
        for (Object[] row : productSale) {
            //row:存储返回结果集的一行数据：[title,data]
            //组装标题
            titleList.add((String) row[0]);
            //组装数据:{value: 335, name: '直接访问'}
            PieDataVo pieDataVo = new PieDataVo();
            pieDataVo.setName((String) row[0]);
            Long value = (long)row[1];
            pieDataVo.setValue(value.intValue());
            dataList.add(pieDataVo);
        }
        //组装返回前端需要的数据
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("titles",titleList);//String指定返回到前端时是数据属性名
        map.put("datas",dataList);
        return map;
    }
}
