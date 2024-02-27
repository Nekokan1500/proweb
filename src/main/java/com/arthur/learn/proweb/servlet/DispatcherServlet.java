package com.arthur.learn.proweb.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arthur.learn.proweb.io.BeanFactory;
import com.arthur.learn.proweb.io.ClasspathXmlApplicationContext;
import com.arthur.learn.proweb.util.StringUtil;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    public DispatcherServlet(){}

    public void init() throws ServletException{
        super.init();
        beanFactory = new ClasspathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //request.setCharacterEncoding("UTF-8");

        // Suppose ULR is http://localhost:8080/proweb/hello.do
        // servletPath is /hello.do
        String servletPath = request.getServletPath().substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        String servletName = servletPath.substring(0, lastDotIndex);
        Object controllerBeanObj = beanFactory.getBean(servletName);

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
