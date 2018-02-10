<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Fgsfds</title>
    <meta charset="UTF-8">
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="media/jQuery-3.3.1/jQuery-3.3.1.js"></script>
    <script src="media/bootstrap-4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="media/bootstrap-4.0.0/css/bootstrap.min.css">
</head>
<script>
</script>
<body>
<div class="container">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Загрузка</li>
        </ol>
    </nav>
    <div class="row">
        <div class="col">
            <form action="UploadServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="form-control form-control-lg">Файл с логами:
                        <input type="file" name="dataFile"></label>
                </div>
                <div class="form-group">
                    <label class="form-control form-control-lg">Файл с юзерами:
                        <input type="file" name="usersFile"></label>
                </div>
                <br>
                <input type="submit" class="btn btn-primary" value="Загрузить">
            </form>
        </div>
        <div class="col">
            <form action="LoadFromResources" method="get">
                <h4 class="h4">Использовать данные по умолчанию</h4>
                <div class="form-group">
                В приложении имеются данные для тестовых целей.<br>
                Если нужно просто опробовать приложение - можно использовать их.
                </div>
                <input type="submit" class="btn btn" value="Использовать сохраненные">
            </form>
        </div>
    </div>
</div>
</body>
</html>
