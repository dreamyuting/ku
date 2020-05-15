package com.yaorange.service;

import com.yaorange.entity.Factory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FactoryService {
    List<Factory> all(String ctype);
}
