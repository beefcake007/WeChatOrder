package com.fanye.sell.service.impl;

import com.fanye.sell.dataobject.ProductCategory;
import com.fanye.sell.enums.ResultEnum;
import com.fanye.sell.exception.SellException;
import com.fanye.sell.repository.ProductCategoryRepository;
import com.fanye.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findById(Integer categoryId) {
        try{
            ProductCategory productCategory  = repository.findById(categoryId).get();
            return productCategory;
        }catch (Exception e){
            throw new SellException(ResultEnum.PRODUCT_CATEGORY_NOT_EXIST);
        }

    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
