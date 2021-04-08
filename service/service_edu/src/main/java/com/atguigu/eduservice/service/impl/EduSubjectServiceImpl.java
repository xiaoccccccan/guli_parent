package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();;


        }catch (Exception e){
            e.printStackTrace();

        }

    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1查询所有一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        //service中调mapper 直接用baseMapper
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2查询二级分类parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于储存最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3封装一级分类oneSubjectList添加到finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {//遍历数据
            //得到oneSubjectList每一个EduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把eduSubject中数据添加到OneSubject对象中
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());

            BeanUtils.copyProperties(eduSubject,oneSubject);//把一个对象的数据复制到另一个对象中
            //添加到OneSubject
            finalSubjectList.add(oneSubject);

            //创建二级分类list集合
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取二级分类
                EduSubject tSubject = twoSubjectList.get(m);

                TwoSubject twoSubject = new TwoSubject();
                //判断一级分类下的id与二级分类parent_id是否一样
                if (eduSubject.getId().equals(tSubject.getParentId())) {
                    //把tSubject的值复制到twoSubject里
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }


        //4封装二级分类


        return finalSubjectList;
    }
}
