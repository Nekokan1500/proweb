package com.arthur.learn.proweb;

import org.junit.Test;

import com.arthur.learn.proweb.dao.base.BaseDao;
import com.arthur.learn.proweb.entity.Fruit;

public class TestFruit {
    private BaseDao<Fruit> baseDao = new BaseDao<>();
    
    @Test
    public void testGetSingleBean(){
        String sql = "Select * from fruits where fid = ?";
        Fruit fruit = baseDao.getSingleBean(sql, Fruit.class, 1);
        System.out.println("Fruit = " + fruit);
    }

    @Test
    public void testGetCount(){
        String sql = "select count(*) from fruits";
        long count = baseDao.getCount(sql);
        System.out.println("Number of fruits = " + count);
    }

    /* 
    @Test
    public void testInsertFruit(){
        String sql = "INSERT INTO fruits (fname, price, fcount, remark) VALUES ( ?, ?, ?, ?)";
        int rows = baseDao.update(sql, "Strawberry", 3, 20, "New");
        System.out.println(rows);
    }
    */
    
}
