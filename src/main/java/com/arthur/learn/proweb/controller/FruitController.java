package com.arthur.learn.proweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.arthur.learn.proweb.entity.Fruit;
import com.arthur.learn.proweb.util.StringUtil;
import com.arthur.learn.proweb.dao.api.FruitDao;
import com.arthur.learn.proweb.dao.impl.FruitDaoImpl;

public class FruitController {

    private FruitDao fruitDao = new FruitDaoImpl();

    private String index(HttpServletRequest request){

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
        //super.processTemplate("index", request, response);
        return "index";
    }

    private String add(HttpServletRequest request){
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(null, fname, price, fcount, remark);

        fruitDao.createFruit(fruit);
        //response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String delete(HttpServletRequest request){
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDao.deleteFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(HttpServletRequest request) {
        String fidStr = request.getParameter("fid");

        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitById(fid);
            request.setAttribute("fruit", fruit);
            //super.processTemplate("edit", request, response);
            return "edit";
        }
        return "error";
    }

    private String update(HttpServletRequest request) {
        Integer fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit newFruit = new Fruit(fid, fname, price, fcount, remark);

        fruitDao.updateFruit(newFruit);

        //super.processTemplate("index", request, response);
        // response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
    
}
