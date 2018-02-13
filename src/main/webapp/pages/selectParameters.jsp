<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>Выбор параметров</title>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="media/jQuery-3.3.1/jQuery-3.3.1.js"></script>
    <script src="media/bootstrap-4.0.0/js/bootstrap.min.js"></script>
    <script src="media/pickmeup/pickmeup.js"></script>
    <script src="media/pickmeup/jquery.pickmeup.twitter-bootstrap.js"></script>
    <script src="js/selectParameters.js"></script>
    <link rel="stylesheet" href="media/bootstrap-4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="media/pickmeup/pickmeup.scss">
    <link rel="stylesheet" href="media/pickmeup/pickmeup.css">
    <link rel="stylesheet" href="css/selectParameters.css">
</head>
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/media/datePicker/pickmeup.css"/>">--%>


<script>
</script>
<body>
<div class="container">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="index.jsp">Загрузка</a></li>
            <li class="breadcrumb-item active" aria-current="page">Выбор параметров</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col-4">
            <form action="ShowChartServlet" id="form" method="post">
                <div class="form-group">
                    <h4 class="h4">Пользователи</h4>
                    <div class="form-control">
                        <c:forEach items="${sessionScope.users}" var="user" varStatus="status">
                            <div class="form-check form-check-inline">
                                <label class="form-check-label">
                                    <input class="form-check-input" type="checkbox" name="users"
                                           value="${user}" checked>${user}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <h4 class="h4">Действия</h4>
                    <div class="form-control">
                        <c:forEach items="${sessionScope.actions}" var="action" varStatus="status">
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input class="form-check-input" type="checkbox" name="actions"
                                           value="${action}" checked>${action}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <h4 class="h4">Период</h4>
                    <input class="form-control" id="date" name="period" value="">
                </div>
                <br>
                <div class="form-group">
                    <label>
                        <h4 class="h4">Период обобщения статистики</h4>
                        <select class="form-control" name="generalizePeriod">
                            <option value="month">Месяц</option>
                            <option value="week">Неделя</option>
                            <option value="day">День</option>
                        </select>
                    </label>
                </div>
                <div class="form-group">
                    <h4 class="h4">Открыть:</h4>
                    <input type="submit" class="btn" value="В новом окне">
                    <input type="button" id="loadChart" class="btn btn-primary" value="На этой странице">
                </div>
            </form>
        </div>
        <div class="col-8">
            <div class="container-fluid" id="AreasplineChart"></div>
            <div class="container-fluid" id="StackedBarChart"></div>
        </div>
    </div>
</div>
</body>
</html>
