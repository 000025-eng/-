package com.itheima.controller;


import com.itheima.pojo.*;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    //查询所有的班级信息
    @GetMapping
    public Result page(ClazzQueryParam param){
        log.info("分页查询班级数据：{},",param);
        PageResult<Clazz> pageResult =clazzService.page(param);
        return Result.success(pageResult);
    }


}
