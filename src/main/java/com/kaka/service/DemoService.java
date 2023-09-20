package com.kaka.service;

import com.kaka.model.demo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DemoService {
   public List<demo> findAll();
}
