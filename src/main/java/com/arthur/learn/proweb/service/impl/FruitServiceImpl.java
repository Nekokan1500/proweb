package com.arthur.learn.proweb.service.impl;

import java.util.List;

import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.entity.Fruit;
import com.arthur.learn.proweb.service.FruitService;
import com.arthur.learn.proweb.util.JDBCUtils;

public class FruitServiceImpl implements FruitService{

    private FruitDao fruitDao = null;

    @Override
    public void addFruit(Fruit fruit) {
        fruitDao.createFruit(fruit);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDao.deleteFruit(fid);
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        return fruitDao.getFruitById(fid);
    }

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        System.out.println("Connection used by getFruitList = " + JDBCUtils.getConnection());
        return fruitDao.getFruits(keyword, pageNo);
    }

    @Override
    public Integer getPageCount(String keyword) {
        System.out.println("Connection used by getPageCount = " + JDBCUtils.getConnection());
        long fruitCount = fruitDao.getFruitCount(keyword);
        return (int)(fruitCount +5 - 1)/5;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDao.updateFruit(fruit);
    }
}
