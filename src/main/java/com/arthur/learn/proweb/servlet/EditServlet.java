package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;
import com.arthur.learn.proweb.util.StringUtil;
import com.arthur.learn.proweb.entity.Fruit;

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {

    private FruitDaoImpl fruitDao = new FruitDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");

        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitById(fid);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }
}
