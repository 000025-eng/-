package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Override
    public JobOption getEmpJobData() {
        //调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobDate();//map:pos=学工主管，num=1
        //组装结果，并返回

        List<Object> jobList  = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList  = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {

        return empMapper.countEmpGenderData();
    }
}
