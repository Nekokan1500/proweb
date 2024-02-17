package com.arthur.learn.proweb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.base.BaseDao;
import com.arthur.learn.proweb.entity.Fruit;

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BaseDao<Fruit> baseDao = new BaseDao<>();
        List<Fruit> fruits = baseDao.getBeanList("Select * from Fruits", Fruit.class);
        request.getSession().setAttribute("fruitList", fruits);
        super.processTemplate("index", request, response);
    }
    
}
