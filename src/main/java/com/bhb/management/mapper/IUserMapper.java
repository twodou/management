package com.bhb.management.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhb.management.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserMapper extends BaseMapper<User> {


    int changerImgByLoginname(@Param("img") String img,@Param("loginname") String loginname);

    void inUserRole(@Param("userid")int userid,@Param("roleid")int roleid);
    void inRolePermission(@Param("roleid")int roleid,@Param("permissionid")int permissionid);
}
