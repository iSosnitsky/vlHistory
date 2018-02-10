package ru.vhistory.ikurbanov.controller.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import ru.vhistory.ikurbanov.dto.DataObject;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;
import ru.vhistory.ikurbanov.util.StackedBarChartData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/getStackedBarChartData")
public class GetStackedBarChartData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        ArrayList<DataObject> dataObjects = (ArrayList<DataObject>) req.getSession().getAttribute("dataObjects");
        ArrayList<HistoryAction> historyActions = new ArrayList<>();


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
        try {
            periodBegin = simpleDateFormat.parse(period.split("-")[0].trim());
            periodEnd = simpleDateFormat.parse(period.split("-")[1].trim());
            dataObjects
                    .forEach(x -> historyActions.addAll(x.getHistoryActions()
                            .stream()
                            .filter(historyAction -> periodBegin.before(historyAction.getTime()) && periodEnd.after(historyAction.getTime()))
                            .collect(Collectors.toList())));
//            historyActions = historyActions.stream().filter(x-> periodBegin.before(x.getTime())&&periodEnd.after(x.getTime())).collect(Collectors.toList());
            ArrayUtils.reverse(users);
            ObjectMapper objectMapper = new ObjectMapper();
            StackedBarChartData stackedBarChartData = new StackedBarChartData(users, actions, historyActions);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(objectMapper.writeValueAsString(stackedBarChartData));
//            req.setAttribute("jsonData", objectMapper.writeValueAsString(stackedBarChartData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
