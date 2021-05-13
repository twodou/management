package com.bhb.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhb.management.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentMapper> {
    List<Department> queryAll();
}
