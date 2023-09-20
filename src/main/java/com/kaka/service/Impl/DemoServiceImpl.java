package com.kaka.service.Impl;

import com.kaka.mapper.DemoMapper;
import com.kaka.model.demo;
import com.kaka.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    /**
     * 查询所有demo对象
     *
     * @return 包含demo对象的列表
     */
    public List<demo> findAll() {
        // 调用UserMapper的findAll方法查询数据库中的数据，并将结果转换为List<demo>
        List<demo> list = Arrays.asList(demoMapper.findAll());
        return list;
    }
}
