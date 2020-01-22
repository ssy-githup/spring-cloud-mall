package ai.ssy.business.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.ssy.business.mapper.ProductMapper;
import ai.ssy.business.model.Product;
import ai.ssy.business.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Product findBySn(String sn) {
        Product product = productMapper.findBySn(sn);
        redisTemplate.opsForValue().set("Product::product:" + sn, product);
        return product;
    }

    @Override
    @Cacheable(value = "Product", key = "'product:'+#sn", unless = "#result == null")
    public Product findCacheBySn(String sn) {
        return null;
    }

    @Override
    @Transactional
    public int updatePriceById(Long id, BigDecimal price) {
        return productMapper.updatePriceById(id, price);
    }

}
