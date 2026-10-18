package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


//员工管理的Controller
@RequestMapping("/emps")
@Slf4j
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    //分页查询
//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Integer gender,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate end){
//        log.info("分页查询：{},{},{},{},{},{}",name,gender,begin,end,page,pageSize);
//        PageResult<Emp> pageResult = empService.page(name,gender,begin,end,page,pageSize);
//        return Result.success(pageResult);
//    }

    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询：{},",empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }
//新增员工
    @PostMapping
    //@RequestBody是接收前端传过来的JSON格式的请求数据，成功封装到这个实体对象
    public Result save(@RequestBody Emp emp){
        log.info("新增加员工：{}", emp);
        empService.save(emp);
        return Result.success();
    }


    //方式一：以一个数组来接收
    // 删除员工
//    @DeleteMapping
//    public  Result delete(Integer[] ids){
//    log.info("删除员工：{}", Arrays.toString(ids));
//    return  Result.success();
//    }
    //方式二：用一个list集合来封装
    // 删除员工
    @DeleteMapping
    public  Result delete(@RequestParam  List<Integer> ids){
        log.info("删除员工：{}", ids);
        empService.delete(ids);
        return  Result.success();
    }

    //根据员工的ID来查询员工的信息
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable  Integer id){
        log.info("根据ID查询员工的IUD：{}",id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }
//修改员工
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工的信息:{}",emp);
        empService.update(emp);
        return Result.success();
    }
}
