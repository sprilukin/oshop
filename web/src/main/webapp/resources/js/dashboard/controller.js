/**
 * Expenses controller
 */
define([
    "jquery",
    'underscore',
    'common/dateFormatter',
    'common/settingsStorage',
    'common/messages',
    'incomes/chart/view',
    'incomes/chart/model',
    'datePickerRu'
], function ($, _, dateFormatter, settingsStorage, messages, ChartView, ChartModel) {

    var DEFAULT_INTERVAL = 14 * (24 * 60 * 60 * 1000); //14 days

    var DashboardController = function(options) {
        this.initialize(options);
    };

    _.extend(DashboardController.prototype, {
        initialize: function(options) {
            _.bindAll(this, "_changeDate");

            this._initDatepicker();
            this._initDatepickerValues();

            this.chartModel = new ChartModel();
            this.chartView = new ChartView({model: this.chartModel});

            this._changeDate();
        },

        _initDatepicker: function() {
            $('#datepicker').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                todayHighlight: true,
                language: settingsStorage.get("lang")
            }).on("changeDate", this._changeDate);
        },

        _initDatepickerValues: function() {
            $("input[name='start']").val(dateFormatter(new Date(new Date().getTime() - DEFAULT_INTERVAL)).format(messages["common_dateFormat"]));
            $("input[name='end']").val(dateFormatter(new Date()).format(messages["common_dateFormat"]));
        },

        _changeDate: function() {
            try {
                var startDate = dateFormatter($("input[name='start']").val(), messages["common_dateFormat"]).toDate().getTime();
                var endDate = dateFormatter($("input[name='end']").val(), messages["common_dateFormat"]).toDate().getTime();
                this.chartModel.setRange(startDate, endDate);
            } catch (e) {
                //Do nothing
            }
        }
    });

    return DashboardController;
});