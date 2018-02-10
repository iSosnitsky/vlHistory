$(document).ready(function () {
    pickmeup('#date',{
        mode: 'range',
        format: 'd/m/Y',
        date: "01/12/2012 - 31/12/2012"
    });

    $('#loadChart').click(function () {
        var dataToSend = $('#form').serialize();
        $.post('getAreasplineChartData', dataToSend, function (areaSplaneChartData) {
            Highcharts.chart('AreasplineChart', {
                chart: {
                    type: 'areaspline',
                    height: 350
                },
                title: {
                    text: 'Действия пользователей по времени'
                },
                xAxis: {
                    categories: areaSplaneChartData.categories
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
                series: areaSplaneChartData.series
            });
        });

        $.post('getStackedBarChartData', dataToSend, function (stackedBarChartData) {
            Highcharts.chart('StackedBarChart', {
                chart: {
                    type: 'bar',
                    height: 350
                },
                title: {
                    text: 'Действия каждого пользователя'
                },
                xAxis: {
                    categories: stackedBarChartData.categories
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Пользователи'
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
                series: stackedBarChartData.series
            });
        })
    });
});