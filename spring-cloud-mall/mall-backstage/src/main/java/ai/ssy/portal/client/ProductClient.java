package ai.ssy.portal.client;

import ai.ssy.portal.client.fallback.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-product", fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/api/product")
    String findBySn(@RequestParam(value = "sn") String sn);

}
