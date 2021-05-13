package com.bhb.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhb.management.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface INoticeMapper extends BaseMapper<Notice> {
    
}
