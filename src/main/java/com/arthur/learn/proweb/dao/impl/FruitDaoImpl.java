package com.arthur.learn.proweb.dao.impl;

import com.arthur.learn.proweb.entity.Fruit;

import java.util.List;

import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.dao.base.BaseDao;

public class FruitDaoImpl extends BaseDao<Fruit> implements FruitDao {

    BaseDao<Fruit> baseDao = new BaseDao<Fruit>();

    @Override
    public int createFruit(Fruit fruit) {
        
        String fname = fruit.getFname();
        Double price = fruit.getPrice();
        Integer fcount = fruit.getFcount();
        String remark = fruit.getRemark();

        String sql = "INSERT INTO fruits (fname, price, fcount, remark) VALUES (?, ?, ?, ?)";
        int rows = baseDao.update(sql, fname, price, fcount, remark);
        return rows;
    }

    @Override
    public Fruit getFruitById(int fid) {
        String sql = "Select * from fruits where fid = ?";
        return baseDao.getSingleBean(sql, Fruit.class, fid);
    }

    @Override
    public List<Fruit> getFruits() {
        String sql = "Select * from fruits";
        return baseDao.getBeanList(sql, Fruit.class);
    }

    @Override
    public int updateFruit(Fruit fruit) {
        int fid = fruit.getFid();
        String fname = fruit.getFname();
        Double price = fruit.getPrice();
        int fcount = fruit.getFcount();
        String remark = fruit.getRemark();

        String sql = "UPDATE fruits SET fname=?, price=?, fcount=?, remark=? WHERE fid =?";
        return baseDao.update(sql, fname, price, fcount, remark, fid);
    }

    @Override
    public int deleteFruit(int fid) {
        String sql = "Delete from fruits where fid = ?";
        return baseDao.update(sql, fid);
    }
    
}
