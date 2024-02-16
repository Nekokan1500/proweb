package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.base.BaseDao;
import com.arthur.learn.proweb.entity.Fruit;;

public class AddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        BaseDao<Fruit> baseDao = new BaseDao<Fruit>();
        String sql = "INSERT INTO fruits (fname, price, fcount, remark) VALUES ( ?, ?, ?, ?)";
        int rows = baseDao.update(sql, fname, price, fcount, remark);
        System.out.println("Number of rows affected: " + rows);

    }
}
