package com.yaorange.web;

import com.yaorange.entity.Factory;
import com.yaorange.service.FactoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/factory")
public class FactoryHandler {
    @Resource(name = "factoryService")
    private FactoryService factoryService;
    @GetMapping("/all")
    private List<Factory> all(String ctype){
        return factoryService.all(ctype);
    }


}
