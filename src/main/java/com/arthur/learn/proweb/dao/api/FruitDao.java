package com.arthur.learn.proweb.dao.api;

import java.util.List;
import com.arthur.learn.proweb.entity.Fruit;

public interface FruitDao {

    public int createFruit(Fruit fruit);

    public Fruit getFruitById(int fid);

    public List<Fruit> getFruits();

    public int updateFruit(Fruit fruit);

    public int deleteFruit(int fid);
    
}
