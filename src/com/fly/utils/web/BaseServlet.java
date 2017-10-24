package com.fly.utils.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.utils.C3P0Utils;


/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String methodName = request.getParameter("method");
		
		if (methodName == null) {
			System.out.println("method name is null");
			String message = "方法名为空！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			return;
		}
		
		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String[] result = (String[]) method.invoke(this, request, response);
			
			if (result == null) {
				System.out.println("result is null");
				String message = "处理结果为空！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("message.jsp").forward(request, response);
				return;
			}
			
			if ("forward".equals(result[0])) {
				//转发
				request.getRequestDispatcher(result[1]).forward(request, response);
			} else if ("redirect".equals(result[0])) {
				//重定向
				response.sendRedirect(result[1]);
			} else if ("json".equals(result[0]) || "text".equals(result[0])) {
				//json or text
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(result[1]);
			} else {
				System.out.println("Error?未获取result?");
			}
		} catch (NoSuchMethodException e) {
			String message = "方法不存在！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			String message = "方法抛出异常，可能是SQL错误！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			e.printStackTrace();
		} finally {
			try {
				C3P0Utils.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
