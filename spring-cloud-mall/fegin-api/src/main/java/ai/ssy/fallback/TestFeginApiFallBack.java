package ai.ssy.fallback;

import ai.ssy.feginapi.TestFeginApi;

/**
 * @ClassName TestFeginApiFallBack
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
public class TestFeginApiFallBack implements TestFeginApi {
    @Override
    public String queryById(Integer id) {

        return "这是降级方法";
    }
}
