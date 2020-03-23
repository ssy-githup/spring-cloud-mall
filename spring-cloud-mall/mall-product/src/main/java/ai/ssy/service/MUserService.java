package ai.ssy.service;


import ai.ssy.entity.MUser;

import java.util.List;

public interface MUserService {

    MUser findMUserById(String id);

    List<MUser> findMUsers();
}
