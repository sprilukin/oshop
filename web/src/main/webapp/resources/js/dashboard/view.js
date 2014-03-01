/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'common/messages',
    'highcharts'
], function ($, _, Backbone, Mustache, dateFormatter, messages, highcharts) {

    return Backbone.View.extend({

        initialize: function(options) {
            highcharts.setOptions({
                global: {
                    useUTC: false
                },
                lang: {
                    months: messages["dashboard_chart_months"].split(","),
                    weekdays: messages["dashboard_chart_weekdays"].split(","),
                    shortMonths: messages["dashboard_chart_shortMonths"].split(",")
                }
            });

            this.model.on("sync", this.render, this);
        },

        setRange: function(startDate, endDate) {
            this.model.setRange(startDate, endDate);
        },

        render: function () {
            this.expensesIncomesSum();
            this.expensesIncomesPieChart();
            this.expensesAndIncomesChart();
            this.incomesMinusExpensesCumulativeChart();
        },

        expensesIncomesSum: function() {
            $('#expensesIncomesSumChart').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: messages["dashboard_chart4_title"]
                },
                xAxis: {},
                yAxis: {
                    min: 0,
                    title: {
                        text: messages["dashboard_chart4_yAxis_title"]
                    }
                },
                tooltip: {
                    headerFormat: '<table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} ₴</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: messages["dashboard_chart4_series_expense"],
                    data: [this.model.get("expensesSum")]

                }, {
                    name: messages["dashboard_chart4_series_income"],
                    data: [this.model.get("incomesSum")]

                }, {
                    name: messages["dashboard_chart4_series_balans"],
                    data: [this.model.get("incomesSum") - this.model.get("expensesSum")]
                }]
            });
        },

        expensesIncomesPieChart: function() {
            $('#expensesIncomesPieChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: messages["dashboard_chart4_title"]
                },
                tooltip: {
                    pointFormat: '<b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    data: [
                        [
                            messages["dashboard_chart4_series_expense"],
                            this.model.get("expensesSum") / (this.model.get("expensesSum") + this.model.get("incomesSum")) * 100
                        ],
                        [
                            messages["dashboard_chart4_series_income"],
                            this.model.get("incomesSum") / (this.model.get("expensesSum") + this.model.get("incomesSum")) * 100
                        ]
                    ]
                }]
            });
        },

        expensesAndIncomesChart: function() {
            $('#expenseChart').highcharts({
                title: {
                    text: messages["dashboard_chart1_title"],
                    x: -20 //center
                },
                subtitle: {
                    text: messages["dashboard_chart1_subtitle"],
                    x: -20
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: messages["dashboard_chart1_yAxis_title"]
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: ' ₴'
                },
                legend: {
                    align: 'left',
                    verticalAlign: 'top',
                    y: 0,
                    floating: true,
                    borderWidth: 0
                },
                series: [{
                    name: messages["dashboard_chart1_series_income"],
                    data: this.model.get("incomes")
                }, {
                    name: messages["dashboard_chart1_series_expense"],
                    data: this.model.get("expenses")
                }]
            });
        },

        incomesMinusExpensesCumulativeChart: function() {
            $('#expenseChart3').highcharts('StockChart', {
                rangeSelector : {
                    selected : 1
                },
                title : {
                    text : messages["dashboard_chart3_title"]
                },
                tooltip: {
                    valueSuffix: ' ₴'
                },
                series : [{
                    name : messages["dashboard_chart3_series_name"],
                    data : this.model.get("incomesMinusExpensesCumulative"),
                    tooltip: {
                        valueDecimals: 2
                    }
                }]
            });
        }
    });
});