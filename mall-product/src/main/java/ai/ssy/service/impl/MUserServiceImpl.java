package ai.ssy.service.impl;


import ai.ssy.entity.AdminUser;
import ai.ssy.entity.MUser;
import ai.ssy.mapper.MUserMapper;
import ai.ssy.service.MUserService;
import ai.ssy.util.reids.RedisonHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: spring-cloud-mall
 * @description: 用户Service
 * @author: ssy
 * @create: 2020-03-23 15:24
 **/
@Service
public class MUserServiceImpl implements MUserService {

    @Autowired
    public MUserMapper mUserMapper;

    @Override
    public MUser findMUserById(String id) {
        return mUserMapper.queryMUserById(id);
    }

    @Override
    public List<MUser> findMUsers() {
        return mUserMapper.queryMUsers();
    }

    @Override
    public Map<String, Object> login(String userName, String passWord) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(userName)){
            map.put("status","no");
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(passWord)){
            map.put("status","no");
            map.put("msg","密码不能为空");
            return map;
        }
        AdminUser adminUser = mUserMapper.findAdminUserByUserName(userName);
        if(adminUser==null){
            map.put("status","no");
            map.put("msg","用户不存在");
            return map;
        }
        if(!adminUser.getPassWord().equals(passWord)){
            map.put("status","no");
            map.put("msg","面错误");
            return map;
        }
        map.put("status","yes");
        return map;
    }

    /**
     * 测试redis分布式锁
     * @param id
     * @return
     */
    public String testRedissonLock(String id){
        try {
            RedisonHelper.lock(id,30L);

            savemallSysUser();
            savemytest1();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisonHelper.unlock(id);
        }
        return null;
    }

    /**
     *保存一条数据到mall-sys
     */
    @Transactional(rollbackFor = Exception.class)
    public void savemallSysUser(){
        mUserMapper.savemallSysUser();
    }

    /**
     *保存一条数据到 mytest1
     */
    @Transactional
    public void savemytest1(){
        mUserMapper.savemytest1();
    }
}