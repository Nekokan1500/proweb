package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;
import com.arthur.learn.proweb.entity.Fruit;

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet{

    private FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit newFruit = new Fruit(fid, fname, price, fcount, remark);

        fruitDao.updateFruit(newFruit);

        //super.processTemplate("index", request, response);
        response.sendRedirect("index");

    }
    
}
