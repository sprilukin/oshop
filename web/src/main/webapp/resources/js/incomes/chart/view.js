/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'highcharts'
], function ($, _, Backbone, Mustache, dateFormatter, highcharts) {

    return Backbone.View.extend({

        initialize: function(options) {
            this.model.on("sync", this.render, this);
        },

        setRange: function(startDate, endDate) {
            this.model.setRange(startDate, endDate);
        },

        render: function () {
            this.expensesAndIncomesChart();
            this.incomesMinusExpensesChart();
            this.incomesMinusExpensesCumulativeChart();
        },

        expensesAndIncomesChart: function() {
            $('#expenseChart').highcharts({
                title: {
                    text: 'Доходы и расходы',
                    x: -20 //center
                },
                subtitle: {
                    text: 'Доход и расход за день',
                    x: -20
                },
                xAxis: {
                    categories: this.model.get("labels")
                },
                yAxis: {
                    title: {
                        text: 'Сумма, ₴'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '₴'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: 'Доход',
                    data: this.model.get("incomes")
                }, {
                    name: 'Расход',
                    data: this.model.get("expenses")
                }]
            });
        },

        incomesMinusExpensesChart: function() {
                $('#expenseChart2').highcharts('StockChart', {


                    rangeSelector : {
                        selected : 1
                    },

                    title : {
                        text : 'Доход минус расход за день'
                    },

                    series : [{
                        name : 'Прибыль',
                        data : this.model.get("incomesMinusExpensesTimeSeries"),
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
        },

        incomesMinusExpensesCumulativeChart: function() {
            $('#expenseChart3').highcharts('StockChart', {


                rangeSelector : {
                    selected : 1
                },

                title : {
                    text : 'Баланс за период'
                },

                series : [{
                    name : 'Баланс',
                    data : this.model.get("incomesMinusExpensesCumulativeTimeSeries"),
                    tooltip: {
                        valueDecimals: 2
                    }
                }]
            });
        }
    });
});