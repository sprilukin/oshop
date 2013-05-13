/**
 * Date formatter
 */
define([
    'mustache'
], function (Mustache) {

    var DEFAULT_DATE_FORMAT = "{{day}}/{{month}}/{{year}}";
    var DEFAULT_TIME_FORMAT = "{{hour}}:{{minute}}:{{second}}";

    var toDate = function(date) {
        if (typeof date === "number") {
            return new Date(date);
        }

        return date;
    };

    var format = function (date, template) {
        if (!date) {
            return;
        }

        date = toDate(date);

        template = template ? template : DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;

        return Mustache.render(template,
            {year: date.getFullYear(),
                month: date.getMonth(),
                day: date.getDay(),
                hour: date.getHours(),
                minute: date.getMinutes(),
                second: date.getSeconds()});
    };

    var formatDate = function (date, template) {
        if (!date) {
            return;
        }

        date = toDate(date);

        template = template ? template : DEFAULT_DATE_FORMAT;

        return Mustache.render(template,
            {year: date.getFullYear(),
                month: date.getMonth(),
                day: date.getDay()});
    };

    var formatTime = function (date, template) {
        if (!date) {
            return;
        }

        date = toDate(date);

        template = template ? template : DEFAULT_TIME_FORMAT;

        return Mustache.render(template,
            {hour: date.getHours(),
                minute: date.getMinutes(),
                second: date.getSeconds()});
    };


    return {
        format: format,
        formatDate: formatDate,
        formatTime: formatTime
    }
});