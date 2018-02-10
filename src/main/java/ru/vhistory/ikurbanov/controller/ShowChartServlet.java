package ru.vhistory.ikurbanov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vhistory.ikurbanov.dto.DataObject;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;
import ru.vhistory.ikurbanov.util.AreasplineChartData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/ShowChartServlet")
public class ShowChartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        ArrayList<DataObject> dataObjects = (ArrayList<DataObject>) req.getSession().getAttribute("dataObjects");
        ArrayList<HistoryAction> historyActions = new ArrayList<>();
        dataObjects.forEach(x -> historyActions.addAll(x.getHistoryActions()));
        Comparator<HistoryAction> comparator = (HistoryAction x, HistoryAction y) -> x.getTime().compareTo(y.getTime());
        historyActions.sort(comparator);


        String[] actions = req.getParameterValues("actions");
        if (actions == null) {
            actions = (String[]) ((List<String>) req.getSession().getAttribute("actions")).toArray();
        }

        String[] users = req.getParameterValues("users");
        if (users == null) {
            users = (String[]) ((List<String>) req.getSession().getAttribute("users")).toArray();
        }
        String period = req.getParameter("period");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String generalizationPeriod = req.getParameter("generalizePeriod");

        Date periodBegin;
        Date periodEnd;
        AreasplineChartData areasplineChartData;
        try {
            periodBegin = simpleDateFormat.parse(period.split("-")[0].trim());
            periodEnd = simpleDateFormat.parse(period.split("-")[1].trim());
            areasplineChartData = new AreasplineChartData(new ArrayList<String>(Arrays.asList(users)), new ArrayList<String>(Arrays.asList(actions)),historyActions,generalizationPeriod, periodBegin, periodEnd);
            areasplineChartData.getSeries();
            ObjectMapper objectMapper = new ObjectMapper();
            req.setAttribute("jsonData", objectMapper.writeValueAsString(areasplineChartData));
            RequestDispatcher rd = req.getRequestDispatcher("/pages/ShowChart.jsp");
            rd.forward(req,resp);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        req.getSession();


    }

}
