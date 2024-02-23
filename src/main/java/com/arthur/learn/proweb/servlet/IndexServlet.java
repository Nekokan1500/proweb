package com.arthur.learn.proweb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;
import com.arthur.learn.proweb.entity.Fruit;
import com.arthur.learn.proweb.util.StringUtil;

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oper = request.getParameter("oper");

        Integer pageNo = 1;

        String keyword = null;

        HttpSession session = request.getSession();

        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)){
            keyword = request.getParameter("keyword");
            if(StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else{
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }

            Object keywordObj = session.getAttribute("keyword");

            if (keywordObj != null){
                keyword = (String)keywordObj;
            }
            else
                keyword = "";
        }
        
        session.setAttribute("pageNo", pageNo);

        FruitDao fruitDao = new FruitDaoImpl();
        List<Fruit> fruits = fruitDao.getFruits(keyword, pageNo);
        session.setAttribute("fruitList", fruits);
        long fruitCount = fruitDao.getFruitCount(keyword);
        long pageCount = (fruitCount+5-1)/5;
        session.setAttribute("pageCount", pageCount);
        super.processTemplate("index", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
}
