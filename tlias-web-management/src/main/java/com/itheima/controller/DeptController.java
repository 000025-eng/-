package com.itheima.controller;

import com.itheima.anno.log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    //private static final Logger log =LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;
    //查询所有的部门信息
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)//get:指定请求方式
    @GetMapping
    public Result list(){
//        System.out.println("查询全部的部门数据");
        log.info("查询全部的部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    //删除所有部门的方法:方式一：根据httpservletRequest 获取请求参数
//   @DeleteMapping("/depts")
//   public Result delete(HttpServletRequest request){
//       String idStr = request.getParameter("id");
//       int id = Integer.parseInt(idStr);
//       System.out.println("根据ID去删除部门数据："+id);
//       return Result.success();
//   }

    //删除所有部门的方法:方式二：根据注解@RequestParam
    //注意事项：一旦声明了@RequestParam，该参数在请求时必须传递，如果不传递就会报错（默认request为true）
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){
//        System.out.println("根据ID去删除部门数据："+deptId);
//        return Result.success();
//    }

    //方式三：省略@RequestParam（前端的请求参数名与服务端的方法形参一致）

    @log
    @DeleteMapping

    public Result delete( Integer id){
        log.info("根据ID去删除部门数据："+id);
        //System.out.println("根据ID去删除部门数据："+id);
        deptService.deleteById(id);
        return Result.success();
    }
    @log
    //添加部门
    @PostMapping
    public Result add(@RequestBody Dept dept){
       // System.out.println("添加部门"+dept);
        log.info("添加部门: {}",dept);
        deptService.add(dept);
        return Result.success();
    }

    //根据iD查询部门
//    @GetMapping("/depts/{id}")
//    public Result getInfo(@PathVariable("id") Integer deptId){
//        System.out.println("修改部门"+deptId);
//        Dept dept=deptService.getInfo(id);
//        return Result.success(dept);
//    }


    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        //System.out.println("根据Id查询部门的数据"+id);
        log.info("根据Id查询部门的数据:{}",id);
        Dept dept =deptService.getById(id);
        return Result.success(dept);
    }

    //修改部门信息
    @log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门数据:{}",dept);
        //System.out.println("修改部门数据"+dept);
        deptService.update(dept);
        return Result.success();
    }
}
