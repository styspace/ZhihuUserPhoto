/**
 * Package name:com.styspace.zhihuphoto.utils.aop
 * File name:ExecuteTimeAroundAop.java
 * Date:2016年12月1日-下午2:25:50
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.styspace.zhihuphoto.controller.BaseController;

/**
 * @ClassName ExecuteTimeAroundAop
 * @Description 增加接口消耗时间拦截器
 * @date 2016年12月1日 下午2:25:50
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Aspect
@Component
public class ExecuteTimeAroundAop {
	@Pointcut("execution(* com.styspace.zhihuphoto.controller..*.*(..))")
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		return BaseController.getResponse(result, elapsedTime);
	}
}
