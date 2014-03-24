/**
 * Expenses controller
 */
define([
    "jquery",
    'underscore',
    'common/dateFormatter',
    'common/settingsStorage',
    'common/filter',
    "bundle!messages",
    'dashboard/expensesAndIncomesSumView',
    'dashboard/expensesAndIncomesDailyView',
    //'dashboard/ordersMapView',
    'dashboard/dailyModel',
    'dashboard/sumModel',
    'datePickerRu'
], function ($, _, dateFormatter, settingsStorage, Filter, messages, SumView, DailyView, /*OrdersMapView,*/ DailyModel, SumModel) {

    var DEFAULT_INTERVAL = 14 * (24 * 60 * 60 * 1000); //14 days

    var DashboardController = function(options) {
        this.initialize(options);
    };

    _.extend(DashboardController.prototype, {
        initialize: function(options) {
            _.bindAll(this, "_changeDate");

            this._initDatepicker();
            this._initDatepickerValues();

            this.filter = new Filter({
                filters: [{
                    name: "dateBTWN",
                    value: ""
                }]
            });

            this.sumModel = new SumModel({
                filter: this.filter
            });
            this.dailyModel = new DailyModel({
                filter: this.filter
            });

            this.sumView = new SumView({model: this.sumModel});
            this.dailyView = new DailyView({model: this.dailyModel});
            //this.ordersMapView = new DailyView({model: this.dailyModel});

            this._changeDate();
        },

        _initDatepicker: function() {
            $('#datepicker').datepicker({
                format: messages["common_dateFormat"].toLowerCase(),
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

                this.filter.set("dateBTWN", startDate + "," + endDate);
            } catch (e) {
                //Do nothing
            }
        }
    });

    return DashboardController;
});