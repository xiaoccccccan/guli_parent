package com.atguigu.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//配置实体类中创建时间和更新时间的自动填充
//启动类中有  @ComponentScan(basePackages = {"com.atguigu"})  扫描才生效
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //创建时间  gmtCreate是属性名称，不是表中字段名称
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        //更新时间
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时间
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
