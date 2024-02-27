package com.arthur.learn.proweb.service;

import java.util.List;

import com.arthur.learn.proweb.entity.Fruit;

public interface FruitService {

    List<Fruit> getFruitList(String keyword, Integer pageNo);
    void addFruit(Fruit fruit);
    Fruit getFruitById(Integer fid);
    void delFruit(Integer fid);
    Integer getPageCount(String keyword);
    void updateFruit(Fruit fruit);
}
