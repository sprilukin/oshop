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
    'incomes/chart/view',
    'incomes/chart/model'
], function (_, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit, dateFormatter, settingsStorage, ChartView, ChartModel) {

    var IncomesController = function() {
        _.bindAll(this, "_changeDate");

        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "description"
        });

        $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            todayBtn: "linked",
            todayHighlight: true,
            language: settingsStorage.get("lang")
        }).on("changeDate", this._changeDate);

        $("input[name='start']").val(dateFormatter(new Date(new Date().getTime() - 604800000)).format("YYYY-MM-DD"));
        $("input[name='end']").val(dateFormatter(new Date()).format("YYYY-MM-DD"));

        this.chartModel = new ChartModel();
        this.chartView = new ChartView({model: this.chartModel});

        this._changeDate();
    };

    _.extend(IncomesController.prototype, BaseControllerWithListAndEdit.prototype, {
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