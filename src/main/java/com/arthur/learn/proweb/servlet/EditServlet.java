package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.dao.base.BaseDao;
import com.arthur.learn.proweb.util.StringUtil;
import com.arthur.learn.proweb.entity.Fruit;

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {

    private BaseDao<Fruit> baseDao = new BaseDao<>();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = baseDao.getSingleBean("select * from Fruits where fid = ?", Fruit.class, fid);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }
}
