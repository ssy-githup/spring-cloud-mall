package ai.ssy.feginapi;

import ai.ssy.fallback.TestFeginApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TestFeginApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
//                   微服务的名称             统一的path(比如类上的那个)
@FeignClient(name = "mall-product",path = "/myfirst")
//@FeignClient(name = "mall-product",fallback = TestFeginApiFallBack.class,path = "/myfirst")
public interface TestFeginApi {

    @RequestMapping("/hellow/{id}")
    public String queryById(@PathVariable("id") Integer id);
}
