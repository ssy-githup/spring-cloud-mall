package ai.ssy.business.service;

import java.math.BigDecimal;

import ai.ssy.business.model.Product;

public interface ProductService {

    Product findBySn(String sn);

    Product findCacheBySn(String sn);

    int updatePriceById(Long id, BigDecimal price);

}
