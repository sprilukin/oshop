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
            highcharts.setOptions({
                global: {
                    useUTC: false
                },
                lang: {
                    months: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
                    weekdays: ['Воскресенье', 'Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'],
                    shortMonths: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек']
                }
            });

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
                    type: 'datetime'
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
                    align: 'left',
                    verticalAlign: 'top',
                    y: 0,
                    floating: true,
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
                        data : this.model.get("incomesMinusExpenses"),
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
                    data : this.model.get("incomesMinusExpensesCumulative"),
                    tooltip: {
                        valueDecimals: 2
                    }
                }]
            });
        }
    });
});