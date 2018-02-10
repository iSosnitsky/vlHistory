package ru.vhistory.ikurbanov.controller;


import ru.vhistory.ikurbanov.DataSingleton;
import ru.vhistory.ikurbanov.dto.DataObject;
import ru.vhistory.ikurbanov.dto.actions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/LoadFromResources")
public class MatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("dataObjects", DataSingleton.getInstance().getDataObjects());
        req.getSession().setAttribute("users", DataSingleton.getInstance().getUsers());
        req.getSession().setAttribute("actions", new String[]{"Create", "Modify", "Connect", "Checkin", "Checkout", "MoveFiles", "ChangeName", "DeleteFile", "Lock", "Disconnect"});
        req.getRequestDispatcher("/pages/selectParameters.jsp").forward(req, resp);
    }
}
