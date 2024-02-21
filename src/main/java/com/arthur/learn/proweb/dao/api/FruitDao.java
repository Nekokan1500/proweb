package com.arthur.learn.proweb.dao.api;

import java.util.List;
import com.arthur.learn.proweb.entity.Fruit;

public interface FruitDao {

    int createFruit(Fruit fruit);

    Fruit getFruitById(int fid);

    List<Fruit> getFruits();

    List<Fruit> getFruits(Integer pageNo);

    int updateFruit(Fruit fruit);

    int deleteFruit(int fid);

    long getFruitCount();
    
}
