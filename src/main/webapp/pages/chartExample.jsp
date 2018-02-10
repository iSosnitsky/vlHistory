<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--For testing purposes. Please ignore--%>
<html>
<head>
    <title>MatcherChart</title>
    <meta charset="UTF-8">
</head>
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/media/datePicker/pickmeup.css"/>">--%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<body>
<div id="container" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>
<table>

</table>


</body>
<script>

    var dataArray = [];
    <c:forEach items="${requestScope.seriesChart}" var="item" varStatus="status">
    details = {};
    details.name = '${item.name}';
    var data = [];

    <c:forEach items="${item.data}" var="datItem" varStatus="provinceStatus">
    data.push(${datItem});
    </c:forEach>
    details.data = data;
    dataArray.push(details);
    </c:forEach>

    console.log(dataArray);

    Highcharts.chart('container', {

        chart: {
            type: 'bar'
        },
        title: {
            text: 'Stacked bar chart'
        },
        xAxis: {
            categories: ['Create', 'Modify', 'Connect', 'Checkin', 'Checkout', 'Move files']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Действия на каждого пользователя'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },
        series: dataArray
    });
</script>
</html>
