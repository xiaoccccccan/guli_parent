package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaocan
 * @since 2021-04-14
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfoVo(CourseInfoVo courseInfoVo);
}
