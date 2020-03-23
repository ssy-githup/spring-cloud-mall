package ai.ssy.entity;

/**
 * @program: spring-cloud-mall
 * @description: 用户实体
 * @author: ssy
 * @create: 2020-03-23 15:08
 **/
public class MUser {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}