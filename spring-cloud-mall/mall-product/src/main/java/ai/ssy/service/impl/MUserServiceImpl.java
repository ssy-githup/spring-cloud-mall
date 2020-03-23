package ai.ssy.service.impl;


import ai.ssy.entity.MUser;
import ai.ssy.mapper.MUserMapper;
import ai.ssy.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}