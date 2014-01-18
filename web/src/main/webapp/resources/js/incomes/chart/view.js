/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'incomes/chart/model',
    'chart'
], function ($, _, Backbone, Mustache, dateFormatter, ChartModel, Chart) {

    return Backbone.View.extend({

        //el: '.listEntities',

        //events: {
        //    "click a.delete": "delete"
        //},

        initialize: function(options) {
            this.chartModel = new ChartModel();
            this.chartModel.on("sync", function() {
                this.render();
            }, this);

            this.context = $("#expenseChart").get(0).getContext("2d");
        },

        fetch: function() {
            this.chartModel.fetch();
        },

        render: function () {
            var expenseModel = this._getModel(this.chartModel.get("expenseCollection"));
            var incomeModel = this._getModel(this.chartModel.get("incomesCollection"));

            var data = {
                labels : expenseModel.labels,
                datasets : [
                    {
                        fillColor : "rgba(220,220,220,0.5)",
                        strokeColor : "rgba(220,220,220,1)",
                        pointColor : "rgba(220,220,220,1)",
                        pointStrokeColor : "#fff",
                        data : expenseModel.data
                    },
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,1)",
                        pointColor : "rgba(151,187,205,1)",
                        pointStrokeColor : "#fff",
                        data : incomeModel.data
                    }
                ]
            };

            this.chart = new Chart(this.context).Line(data, {bezierCurve : false});
        },

        _getModel: function(collection) {
            var labels = [];
            _.each(collection.models, function(model) {
                labels.push(dateFormatter(model.attributes.date).format());
            });

            var data = [];
            _.each(collection.models, function(model) {
                data.push(model.attributes.amount);
            });

            return {
                labels: labels,
                data: data
            }
        }
    });
});