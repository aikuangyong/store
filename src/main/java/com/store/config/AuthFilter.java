package com.store.config;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthFilter implements Filter {
    private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String auth = request.getHeader("Authorization");
        System.out.println("auth encoded in base64 is " + getFromBASE64(auth));

        if ((auth != null) && (auth.length() > 6)) {
            auth = auth.substring(6, auth.length());
            String decodedAuth = getFromBASE64(auth);
            System.out.println("auth decoded from base64 is " + decodedAuth);
            request.getSession().setAttribute("auth", decodedAuth);
            return true;
        } else {
            return false;
        }
    }

    private String getFromBASE64(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }

    public void nextStep(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<html> next step, authentication is : " + request.getSession().getAttribute("auth") + "<br>");
        pw.println("<br></html>");
    }

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println("Auth-Filter:" + request.getRequestURI());
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
