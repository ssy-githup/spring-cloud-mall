package ai.ssy.mapper;

import ai.ssy.vo.GatewayDefine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GatewayDefineMapper {
    /**
     * 从数据库获取动态配置；
     * 可以根据业务需要进行sql修改
     */
    @Select("select * from gateway_define")
    List<GatewayDefine> findAll();
}
