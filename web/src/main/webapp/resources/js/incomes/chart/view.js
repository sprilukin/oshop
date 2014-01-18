/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'chart'
], function ($, _, Backbone, Mustache, dateFormatter, Chart) {

    return Backbone.View.extend({

        initialize: function(options) {
            this.model.on("sync", this.render, this);
            this.context = $("#expenseChart").get(0).getContext("2d");
        },

        fetch: function() {
            this.model.fetch();
        },

        setRange: function(startDate, endDate) {
            this.model.setRange(startDate, endDate);
        },

        render: function () {
            var data = {
                labels : this.model.get("labels"),
                datasets : [
                    {
                        fillColor : "rgba(220,220,220,0.5)",
                        strokeColor : "rgba(220,220,220,1)",
                        pointColor : "rgba(220,220,220,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("expenses")
                    },
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,1)",
                        pointColor : "rgba(151,187,205,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("incomes")
                    }
                ]
            };

            this.chart = new Chart(this.context).Line(data, {bezierCurve : false});
        }
    });
});