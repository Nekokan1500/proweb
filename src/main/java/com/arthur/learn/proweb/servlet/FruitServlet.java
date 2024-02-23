package com.arthur.learn.proweb.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arthur.learn.proweb.entity.Fruit;
import com.arthur.learn.proweb.util.StringUtil;
import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {

    FruitDao fruitDao = new FruitDaoImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String operate = request.getParameter("operate");

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        Method[] methods = this.getClass().getDeclaredMethods();

        for (Method m: methods){
            String methodName = m.getName();
            if (operate.equals(methodName)){
                try {
                    m.invoke(this, request, response);
                    return;
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("Invalid operation");
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        List<Fruit> fruits = fruitDao.getFruits(keyword, pageNo);
        session.setAttribute("fruitList", fruits);
        long fruitCount = fruitDao.getFruitCount(keyword);
        long pageCount = (fruitCount+5-1)/5;
        session.setAttribute("pageCount", pageCount);
        super.processTemplate("index", request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(null, fname, price, fcount, remark);

        fruitDao.createFruit(fruit);
        response.sendRedirect("fruit.do");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDao.deleteFruit(fid);
        }
        response.sendRedirect("fruit.do");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");

        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitById(fid);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit newFruit = new Fruit(fid, fname, price, fcount, remark);

        fruitDao.updateFruit(newFruit);

        //super.processTemplate("index", request, response);
        response.sendRedirect("fruit.do");

    }
    
}
