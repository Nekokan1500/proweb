package com.arthur.learn.proweb.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.arthur.learn.proweb.util.StringUtil;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private Map<String, Object> beanMap = new HashMap<>();

    public DispatcherServlet(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("ApplicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resourceAsStream);
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++){
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Object beanObj = Class.forName(className).getDeclaredConstructor().newInstance();
                    beanMap.put(beanId, beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // Suppose ULR is http://localhost:8080/proweb/hello.do
        // servletPath is /hello.do
        String servletPath = request.getServletPath().substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        String servletName = servletPath.substring(0, lastDotIndex);
        Object controllerBeanObj = beanMap.get(servletName);

        String operate = request.getParameter("operate");

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method :  methods){
                if (operate.equals(method.getName())){    
                    Parameter[] parameters = method.getParameters();
                    Object[] parameterValues = new Object[parameters.length];
                    for(int i = 0; i < parameters.length; i++){
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("request".equals(parameterName)){
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)){
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)){
                            parameterValues[i] = request.getSession();
                        }
                        else{
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = null;
                            if ("java.lang.Integer".equals(typeName) && parameterValue != null){
                                parameterObj = Integer.parseInt(parameterValue);
                            }else if ("java.lang.Double".equals(typeName) && parameterValue != null){
                                parameterObj = Double.parseDouble(parameterValue);
                            } else{
                                parameterObj = parameterValue;
                            }

                            parameterValues[i] = parameterObj;
                        }
                    }
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);
                    if (returnObj != null){
                        // View processing
                        String returnStr = (String)returnObj;
                        if(returnStr.startsWith("redirect:")){     // e.g., redirect:fruit.do
                            String redirectStr = returnStr.substring("redirect:".length());
                            response.sendRedirect(redirectStr);
                        }
                        else{   // e.g., edit
                            super.processTemplate(returnStr, request, response);
                        }
                    }
                }
            }
            //throw new RuntimeException("Invalid operation");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
