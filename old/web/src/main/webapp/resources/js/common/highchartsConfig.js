/**
 * ShippingTypes View
 */
define([
    "bundle!messages",
    'highcharts'
], function (messages, highcharts) {

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

    return highcharts;
});