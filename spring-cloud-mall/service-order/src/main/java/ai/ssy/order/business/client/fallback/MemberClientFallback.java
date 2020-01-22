package ai.ssy.order.business.client.fallback;

import ai.ssy.order.business.client.MemberClient;
import org.springframework.stereotype.Component;

@Component
public class MemberClientFallback implements MemberClient {

    @Override
    public int updatePasswdById(Long id, String password) {
        System.out.println("MemberClientFallback中的降级方法");
        throw new RuntimeException("updatePasswdById更新失败");
    }

}
