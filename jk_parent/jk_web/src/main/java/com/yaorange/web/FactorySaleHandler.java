package com.yaorange.web;

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
@RequestMapping("/factorySale")
public class FactorySaleHandler {
    @Resource(name = "contractProductService")
    private ContractProductService contractProductService;
    @GetMapping("/map")
    public Map<String,Object> fatorySale1(){
        Map<String,Object> map=new HashMap<>();
        List<Object[]> data = contractProductService.findFactorySale();
        //准备标题集合
        List<String> titles = new ArrayList<String>();
        //准备数据集合
        List<Integer> datas = new ArrayList<Integer>();
        //给集合添加数据
        for (Object[] rowData : data){
            titles.add((String)rowData[0]);
             Long da = (Long) rowData[1];
            datas.add(da.intValue());
        }
        //将集合添加到返回的map中
        map.put("titles",titles);
        map.put("datas",datas);
        return map;
    }
}
