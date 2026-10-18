package com.itheima.aop;

import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private  OperateLogMapper operateLogMapper;


    @Around("@annotation(com.itheima.anno.log )")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();


        //执行目标方法
        Object result = joinPoint.proceed();

        //计算耗时
        long endTime = System.currentTimeMillis();
        long coatTime =  endTime-startTime;


        //构建实体对象
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(getCurrentUserId());//获取当前登陆用户的ID
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result != null? result.toString():"voic");
        olog.setCostTime(coatTime);


        log.info("记录操作日志：{}",olog);
        //保存日志
        operateLogMapper.insert(olog);

        return result;

    }

    private Integer getCurrentUserId(){
        return CurrentHolder.getCurrentId();
    }
}
