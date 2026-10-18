package com.itheima;

import com.itheima.mapper.EmpExprMapper;
import com.itheima.pojo.EmpExpr;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 复现「保存员工工作经历（emp_expr 批量插入）」的问题。
 * 加了 @Transactional，测试结束自动回滚，不会污染 tlias 库的数据。
 */
@SpringBootTest
@Transactional
public class EmpExprSaveReproTest {

    @Autowired
    private EmpExprMapper empExprMapper;

    @Test
    public void insertBatch() {
        EmpExpr e1 = new EmpExpr();
        e1.setEmpId(37);
        e1.setBegin(LocalDate.of(2020, 1, 1));
        e1.setEnd(LocalDate.of(2021, 1, 1));
        e1.setCompany("测试公司A");
        e1.setJob("开发工程师");

        EmpExpr e2 = new EmpExpr();
        e2.setEmpId(37);
        e2.setBegin(LocalDate.of(2021, 2, 1));
        e2.setEnd(LocalDate.of(2022, 3, 1));
        e2.setCompany("测试公司B");
        e2.setJob("高级开发工程师");

        List<EmpExpr> exprList = Arrays.asList(e1, e2);
        empExprMapper.insertBatch(exprList);
        System.out.println(">>> 批量插入成功，共 " + exprList.size() + " 条");
    }
}