package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl  implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;
    //这是方式一
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        //调用Mapper接口，查询总记录数
//        long total = empMapper.count();
//        //调用Mapper接口，查询结果列表
//        Integer start =(page -1)*pageSize;
//        List<Emp> rows = empMapper.list(start, pageSize);
//        //封装PageReul对象，返回
//        return new PageResult<Emp>(total,rows);
//    }
    //方式二
    //注意事项：1定义的SQL语句结尾不能加分号
    //2：PageHelper仅仅能对紧跟在其后的第一个查询语句进行分页处理
//    @Override
//    public PageResult<Emp> page(String name, Integer gender,
//                                LocalDate begin,
//                                LocalDate end ,Integer page, Integer pageSize) {
//        //设置分页参数（PageHelper）
//        PageHelper.startPage(page,pageSize);
//        //执行查询
//        List<Emp> emplist = empMapper.list(name, gender, begin, end);
//
//        //解析查询结果，并封装
//        Page<Emp>  p= (Page<Emp>) emplist;
//        return new PageResult<Emp>(p.getTotal(),p.getResult());
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //设置分页参数（PageHelper）
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //执行查询
        List<Emp> emplist = empMapper.list(empQueryParam);

        //解析查询结果，并封装
        Page<Emp>  p= (Page<Emp>) emplist;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})//事务管理的注解 --默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp emp) {
        try {
            //1.保存员工的基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);
            //2.保存员工的工作经历
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合，为empId赋值
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });

                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog =new EmpLog(null,LocalDateTime.now(),"新增员工："+ emp);
            empLogService.insertLog(empLog);
        }


    }



    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //删除员工的基本信息
        empMapper.deleteByIds(ids);
        //删除员工的工作信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }
@Transactional(rollbackFor = {Exception.class})//出现任何异常就回滚
    @Override
    public void update(Emp emp) {
        //根据ID修改员工的信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateBtId(emp);
        //修改员工的工作信息
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        //1先删除原有的工作经历
        // 后修改
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernamePassword(emp.getUsername(), emp.getPassword());
        //判断员工的信息，是否存在，存在则组装登陆员工信息
        if (e != null) {
            log.info("登陆成功，员工信息为:{}", e);
            //生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());

            String jwt = JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }
        return null;
    }
}
