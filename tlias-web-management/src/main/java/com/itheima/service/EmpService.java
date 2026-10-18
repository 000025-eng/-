package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface EmpService {

    //分页查询的方法
    //page 页数
    //pageSize 每页记录数
//    PageResult<Emp> page(String name, Integer gender,
//                          LocalDate begin,
//                         LocalDate end ,Integer page, Integer pageSize);

    PageResult<Emp> page(EmpQueryParam empQueryParam);
//新增加员工的信息
    void save(Emp emp);
//批量删除员工信息的
    void delete(List<Integer> ids);
//根据IO来查询员工的信息
    Emp getInfo(Integer id);
//修改员工的信息
    void update(Emp emp);
//员工登录
    LoginInfo login(Emp emp);
}
