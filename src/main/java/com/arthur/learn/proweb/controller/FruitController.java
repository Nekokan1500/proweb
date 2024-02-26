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

    private String index(HttpServletRequest request, String oper, String keyword, Integer pageNo){

        HttpSession session = request.getSession();

        if (pageNo == null){
            pageNo = 1;
        }

        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)){
            pageNo = 1;
            if(StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else{
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

    private String add(String fname, Double price, Integer fcount, String remark){

        Fruit fruit = new Fruit(null, fname, price, fcount, remark);

        fruitDao.createFruit(fruit);
        //response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String delete(Integer fid){
        if (fid != null){
            fruitDao.deleteFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(HttpServletRequest request, Integer fid) {

        if (fid != null){
            Fruit fruit = fruitDao.getFruitById(fid);
            request.setAttribute("fruit", fruit);
            //super.processTemplate("edit", request, response);
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid, String fname, Double price, Integer fcount, String remark) {
        
        fruitDao.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        //super.processTemplate("index", request, response);
        // response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}