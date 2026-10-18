package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//员工构工作经历的
@Mapper
public interface EmpExprMapper {
    //批量保存员工的工作经历信息

    void insertBatch(List<EmpExpr> exprList);
//根据员工的ID批量删除员工的基本信息
    void deleteByEmpIds(List<Integer> empIds);
}
