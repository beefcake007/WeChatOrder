package com.fanye.sell.service.impl;

import com.fanye.sell.exception.SellException;
import com.fanye.sell.service.RedisLock;
import com.fanye.sell.service.SecKillService;
import com.fanye.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {


    @Autowired
    private RedisLock redisLock;


    private static final int TIMOUT = 10000;

    /**
     * 中秋活动 秒杀月饼 限量100000
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("abc123456", 100000);
        stock.put("abc123456", 100000);
    }

    private String queryMap(String productId) {
        return "Mid-Autumn activities, moon cake special, limited"
                + products.get(productId)
                + " And then there were：" + stock.get(productId) + " part"
                + " The number of successful users of this product："
                + orders.size() + " people";
    }


    @Override
    public String querySecKillProductInfo(String productId) {
        return queryMap(productId);
    }

    /**
     * 描述逻辑
     *
     * @param productId
     */
    @Override
    public void orderProductMockDiffUser(String productId) {
        long time = System.currentTimeMillis() + TIMOUT;
        if (!redisLock.lock(productId, String.valueOf(time))) {
            throw new SellException(110, "没抢到，换个姿势再试一遍呀");
        }

        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            //库存不足
            throw new SellException(100, "活动已经结束,请留意下次活动");
        } else {

            orders.put(KeyUtil.genUniqueKey(), productId);
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        redisLock.unlock(productId, String.valueOf(time));
    }


//    @Override
//    public synchronized void  orderProductMockDiffUser(String productId) {
//        int stockNum = stock.get(productId);
//        if (stockNum == 0) {
//            //库存不足
//            throw new SellException(100, "活动已经结束,请留意下次活动");
//        } else {
//
//            orders.put(KeyUtil.genUniqueKey(), productId);
//            stockNum = stockNum - 1;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//            stock.put(productId, stockNum);
//        }
//
//    }
}