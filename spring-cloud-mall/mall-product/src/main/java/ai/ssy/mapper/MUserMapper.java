package ai.ssy.mapper;

import ai.ssy.entity.MUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MUserMapper {

    @Select("select * from m_user where id=#{id}")
    MUser queryMUserById(String id);

    List<MUser> queryMUsers();
}
