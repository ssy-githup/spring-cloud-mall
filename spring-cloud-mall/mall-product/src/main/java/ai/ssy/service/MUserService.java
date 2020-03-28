package ai.ssy.service;


import ai.ssy.entity.MUser;

import java.util.List;
import java.util.Map;

public interface MUserService {

    MUser findMUserById(String id);

    List<MUser> findMUsers();

    Map<String,Object> login(String userName, String passWord);
}
