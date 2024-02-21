package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;
import com.arthur.learn.proweb.entity.Fruit;;

public class AddServlet extends HttpServlet {

    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(null, fname, price, fcount, remark);

        fruitDao.createFruit(fruit);
        response.sendRedirect("index");

    }
}
