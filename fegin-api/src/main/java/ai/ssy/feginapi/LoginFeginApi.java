package ai.ssy.feginapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName TestFeginApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
//                   微服务的名称             统一的path(比如类上的那个)
@FeignClient(name = "mall-product")
//@FeignClient(name = "mall-product",fallback = TestFeginApiFallBack.class,path = "/myfirst")
public interface LoginFeginApi {

    @RequestMapping("/login")
    public Map<String,Object> login(@RequestParam("userName")String userName,
                                    @RequestParam("passWord")String passWord);
}
