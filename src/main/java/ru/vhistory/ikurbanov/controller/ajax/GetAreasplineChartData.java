package ru.vhistory.ikurbanov.controller.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vhistory.ikurbanov.dto.DataObject;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;
import ru.vhistory.ikurbanov.util.AreasplineChartData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/getAreasplineChartData")
public class GetAreasplineChartData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

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
        PrintWriter out = resp.getWriter();
        AreasplineChartData areasplineChartData;
        try {
            periodBegin = simpleDateFormat.parse(period.split("-")[0].trim());
            periodEnd = simpleDateFormat.parse(period.split("-")[1].trim());
            areasplineChartData = new AreasplineChartData(new ArrayList<String>(Arrays.asList(users)), new ArrayList<String>(Arrays.asList(actions)),historyActions,generalizationPeriod, periodBegin, periodEnd);
            areasplineChartData.getSeries();
            ObjectMapper objectMapper = new ObjectMapper();
            out.println(objectMapper.writeValueAsString(areasplineChartData));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
