package ru.vhistory.ikurbanov.controller;

import ru.vhistory.ikurbanov.DataSingleton;
import ru.vhistory.ikurbanov.dto.DataObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (req.getPart("dataFile")!=null&&req.getPart("usersFile")!=null){
        InputStream is = req.getPart("dataFile").getInputStream();
        List<String> allLines = InputStreamToArray(is);
        List<String> users = InputStreamToArray(req.getPart("usersFile").getInputStream());
        List<DataObject> dataObjects = DataSingleton.StringListToDataObject(allLines);
        req.getSession().setAttribute("dataObjects",dataObjects);
        req.getSession().setAttribute("users",users);
        req.getSession().setAttribute("actions", new String[]{"Create","Modify","Connect","Checkin","Checkout","MoveFiles","ChangeName","DeleteFile","Lock","Disconnect"});
        req.getRequestDispatcher("/pages/selectParameters.jsp").forward(req,resp);
        } else if (req.getSession().getAttribute("dataObjects")!=null){
            req.getRequestDispatcher("/pages/selectParameters.jsp").forward(req,resp);
        } else {
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        }
    }

    private List<String> InputStreamToArray(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
        List<String> allLines = new ArrayList<String>();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allLines;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/selectParameters.jsp").forward(req,resp);
    }
}
