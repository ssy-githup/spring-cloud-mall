package ai.ssy.order.business.client.fallback;

import ai.ssy.order.business.client.ProductClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public int updatePriceById(Long id, BigDecimal price) {
        System.out.println("ProductClientFallback");
        throw new RuntimeException("updatePriceBySn更新失败");
    }

}
