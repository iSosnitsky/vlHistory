package ru.vhistory.ikurbanov.controller;

import ru.vhistory.ikurbanov.DataSingleton;
import ru.vhistory.ikurbanov.dto.SeriesChart;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


//For testing purposes. Please ignore
@WebServlet("/ChartExample")
public class CommonStackedChartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        List<SeriesChart> seriesCharts = new ArrayList<SeriesChart>();
        DataSingleton dataSingleton = DataSingleton.getInstance();
        dataSingleton.getUsers().forEach(userName -> {
            seriesCharts.add(new SeriesChart(userName));
        });
        List<SeriesChart> result = seriesCharts.stream().peek(seriesChart -> {
            List<HistoryAction> actionsForUser = new ArrayList<>();
            dataSingleton.getDataObjects().forEach(dataObject -> {
                actionsForUser.addAll(dataObject.getHistoryActions()
                        .stream()
                        .filter(historyAction -> Objects.equals(historyAction.getUser(), seriesChart.getName()))
                        .collect(Collectors.toList()));
            });
            int[] actionOccurences = new int[9];
            actionOccurences[0] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Create")).count();
            actionOccurences[1] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Modify")).count();
            actionOccurences[2] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Connect")).count();
            actionOccurences[3] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "MoveFiles")).count();
            actionOccurences[4] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Checkin")).count();
            actionOccurences[5] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Checkout")).count();
            actionOccurences[6] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Disconnect")).count();
            actionOccurences[7] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "Lock")).count();
            actionOccurences[8] = (int) actionsForUser.stream().filter(x -> Objects.equals(x.getName(), "DeleteFile")).count();
            seriesChart.setData(actionOccurences);
        }).collect(Collectors.toList());

        req.setAttribute("seriesChart", result);
        RequestDispatcher rd = req.getRequestDispatcher("/pages/chartExample.jsp");
        rd.forward(req,resp);
//        req.getRequestDispatcher("/pages/chartExample.jsp").forward(req,resp);

    }


}


