package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Api(description = "讲师管理")//swagger提示注解，方便查看
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //1 查询所有讲师列表
    @ApiOperation(value = "所有讲师列表")//swagger提示注解，方便查看
    @GetMapping("/findAll")
    //R统一json返回，注意导入自己commom中R
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);;
        return R.ok().data("items",list);
    }

//    @ApiOperation(value = "所有讲师列表")//swagger提示注解，方便查看
//    @GetMapping("/findAll")
//    public List<EduTeacher> findAllTeacher(){
//
//        List<EduTeacher> list = tacherService.list(null);;
//        return list;
//    }

    //2逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")//swagger提示注解，方便查看
    @DeleteMapping("{id}")
    public R removeTeacher(
            //swagger提示注解，方便查看
            @ApiParam(name = "id",value = "讲师ID",required =true )
            //路径{id}获取
            @PathVariable String id){
        boolean falg = teacherService.removeById(id);
        if (falg){
            return R.ok();
        }else{
            return R.error();
        }
    }


//    @ApiOperation(value = "逻辑删除讲师")//swagger提示注解，方便查看
//    @DeleteMapping("{id}")
//    public boolean removeTeacher(
//            //swagger提示注解，方便查看
//            @ApiParam(name = "id",value = "讲师ID",required =true )
//            //路径{id}获取
//            @PathVariable String id){
//        boolean falg = tacherService.removeById(id);
//        return falg;
//    }


    //3 分页查询讲师的方法
    //current当前页，limit每页显示的记录数
    @ApiOperation(value = "分页查询")//swagger提示注解，方便查看
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @PathVariable long current,
            @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据的list集合

//        Map map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }


    //4 多条件查询带分页的方法
    @ApiOperation(value = "多条件查询带分页的方法")//swagger提示注解，方便查看
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            //@RequestBody 用json传递数据 需要使用post提交 get取不到
            //因为在http协议里，get是没有请求体的
            //required = false 表示teacherQuery 可以没有
            @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page 分页查询
        Page<EduTeacher> pageTeacher= new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wapper = new QueryWrapper();
        //多条件组合查询  名字模糊查询、等级、开始时间、结束时间
        //获取参数 name level begin end
        //swagger提示注解，方便查看
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，如果不为空拼接条件
         if (!StringUtils.isEmpty(name)){
             wapper.like("name",name);//参数表中字段和获取的值
         }
        if (!StringUtils.isEmpty(level)){
            wapper.like("level",level);//参数表中字段和获取的值
        }
        if (!StringUtils.isEmpty(begin)){
             //gt 大于等于
            wapper.gt("gmt_create",begin);  //参数表中字段创建时间和获取的值
        }
        if (!StringUtils.isEmpty(end)){
             //le 小于等于
            wapper.le("gmt_create",end);//参数表中字段和获取的值
        }

        //排序
        wapper.orderByDesc("gmt_create");


        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据的list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //5 添加讲师的接口方法
    @ApiOperation(value = "添加讲师的方法")//swagger提示注解，方便查看
    @PostMapping("addTeacher")
    public R addTeacher(
            //@RequestBodyjson形式传递参数
            @RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);//添加讲师对象
        if (save){ //添加成功
            return R.ok();
        }else {    //添加失败
            return R.error();
        }
    }

    //6 根据讲师id查询
    @ApiOperation(value = "根据讲师id查询")//swagger提示注解，方便查看
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){

        //异常测试
//        try {
//            int i = 10/0;
//        }catch (Exception e){
//            //执行自定义异常
//            throw new GuliException(20001,"执行了自定义异常处理");
//        }
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //7 根据Id修改讲师数据
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            //@RequestBodyjson形式传递参数
            @RequestBody EduTeacher eduTeacher){
        boolean falg = teacherService.updateById(eduTeacher);
        if (falg){ //添加成功
            return R.ok();
        }else {    //添加失败
            return R.error();
        }
    }
    //用put提交方式 不能用@RequestBody注解
//    @ApiOperation(value = "根据ID修改讲师")
//    @PutMapping("{id}")
//    public R updateById(
//        @ApiParam(name = "id", value = "讲师ID", required = true)
//        @PathVariable String id,
//        @ApiParam(name = "teacher", value = "讲师对象", required = true)
//        @RequestBody EduTeacher eduTeacher){
//        eduTeacher.setId(id);
//        teacherService.updateById(eduTeacher);
//        return R.ok();
//    }
}

