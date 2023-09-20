package com.kaka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaka.model.demo;
import com.kaka.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("/inde")

    public String findAll() {
        List<demo> demoList = demoService.findAll();


        try {
            // 使用ObjectMapper将查询结果列表转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(demoList);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return "index.html"; // 处理异常情况
        }
    }
}
