<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MatcherChart</title>
    <meta charset="UTF-8">
</head>
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/media/datePicker/pickmeup.css"/>">--%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="media/jQuery-3.3.1/jQuery-3.3.1.js"></script>
<script src="media/bootstrap-4.0.0/js/bootstrap.js"></script>
<link rel="stylesheet" href="media/bootstrap-4.0.0/css/bootstrap.css">
<body>
<div class="container">
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="index.jsp">Загрузка</a></li>
        <li class="breadcrumb-item"><a href="UploadServlet">Выбор Параметров</a></li>
        <li class="breadcrumb-item active" aria-current="page">График</li>
    </ol>
</nav>
<div id="container" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>
</div>



</body>
<script>

    var dataArray = ${requestScope.jsonData}

    console.log(dataArray);

    Highcharts.chart('container', {

        chart: {
            type: 'areaspline'
        },
        title: {
            text: 'Действия пользователей на графике'
        },
        xAxis: {
            categories: dataArray.categories
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Действия пользователей'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            verticalAlign: 'top',
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        tooltip: {
            shared: true,
            valueSuffix: ' действий'
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            areaspline: {
                fillOpacity: 0.5
            }
        },
        series: dataArray.series
    });
</script>
</html>
