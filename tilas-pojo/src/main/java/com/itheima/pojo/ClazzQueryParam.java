package com.itheima.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
//班级查询的分页查询

@Data
public class ClazzQueryParam {
    private Integer page=1;//页码
    private  Integer pageSize=10;//页码展示的条数
    private String name;//班级姓名
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;//开课时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;//结束时间
}
