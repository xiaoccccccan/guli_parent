package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaocan
 * @since 2021-04-14
 */

@Api(description = "课程管理分类")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "添加课程分类")
    @PostMapping("addCourseInfoVo")
    public R addCourseInfoVo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.saveCourseInfoVo(courseInfoVo);
        return R.ok();
    }

}

