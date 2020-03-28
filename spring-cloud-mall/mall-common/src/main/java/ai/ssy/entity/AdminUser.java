package ai.ssy.entity;

/**
 * @ClassName AdminUser
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/27
 * @Version V1.0
 **/
public class AdminUser {

    private Integer id;

    private String userName;

    private String passWord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
