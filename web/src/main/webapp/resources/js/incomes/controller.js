/**
 * Expenses controller
 */
define([
    'underscore',
    'incomes/model',
    'incomes/collection',
    'incomes/listView',
    'incomes/editView',
    'common/baseControllerWithListAndEdit',
    'common/dateFormatter',
    'common/settingsStorage',
    'common/sorter',
    'incomes/chart/view',
    'incomes/chart/model'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit, dateFormatter, settingsStorage, Sorter, ChartView, ChartModel) {

    var IncomesController = function() {
        this.sorter = new Sorter({
            sorters: [{
                name: "date",
                type: "desc"
            }]
        });

        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection({sorter: this.sorter.format()}),
            sorter: this.sorter,
            View: ListView,
            search: "description"
        });
    };

    _.extend(IncomesController.prototype, BaseControllerWithListAndEdit.prototype, {
        initialize: function(options) {
            BaseControllerWithListAndEdit.prototype.initialize.apply(this, arguments);

            _.bindAll(this, "_changeDate");

            $('#datepicker').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                todayHighlight: true,
                language: settingsStorage.get("lang")
            }).on("changeDate", this._changeDate);

            var daysToShowByDefault = 7 * (24 * 60 * 60 * 1000);
            $("input[name='start']").val(dateFormatter(new Date(new Date().getTime() - daysToShowByDefault)).format("YYYY-MM-DD"));
            $("input[name='end']").val(dateFormatter(new Date()).format("YYYY-MM-DD"));

            this.chartModel = new ChartModel();
            this.chartView = new ChartView({model: this.chartModel});

            this._changeDate();
        },

        _changeDate: function() {
            try {
                var startDate = dateFormatter($("input[name='start']").val(), "YYYY-MM-DD").toDate().getTime();
                var endDate = dateFormatter($("input[name='end']").val(), "YYYY-MM-DD").toDate().getTime();
                this.chartModel.setRange(startDate, endDate);
            } catch (e) {
                //Do nothing
            }
        }
    });

    return IncomesController;
});