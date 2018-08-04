package com.fanye.sell.service.impl;

import com.fanye.sell.dataobject.ProductInfo;
import com.fanye.sell.dto.CartDTO;
import com.fanye.sell.enums.ProductStatusEnum;
import com.fanye.sell.enums.ResultEnum;
import com.fanye.sell.exception.SellException;
import com.fanye.sell.repository.ProductInfoRepository;
import com.fanye.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
//    @Cacheable(cacheNames = "product", key = "123")
//    @Cacheable(key = "singleDog")
    public ProductInfo findById(String productId) {
        try{
            ProductInfo productInfo = repository.findById(productId).get();
            return productInfo;
        }catch (Exception e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
//    @CachePut(cacheNames = "product", key = "123")
//    @CachePut(key = "singleDog")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo;
            try{
                productInfo = repository.findById(cartDTO.getProductId()).get();
            }catch (Exception e){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo;
            try{
                productInfo = repository.findById(cartDTO.getProductId()).get();
            }catch (Exception e){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo;
        try{
            productInfo = repository.findById(productId).get();
        }catch (Exception e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo;
        try{
            productInfo = repository.findById(productId).get();
        }catch (Exception e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

}
