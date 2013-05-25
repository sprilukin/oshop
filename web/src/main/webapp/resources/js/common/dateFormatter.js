/**
 * Date formatter
 */
define([
    'momentRu',
    'common/settingsStorage'
], function (moment, settingsStorage) {

    var DEFAULT_DATE_FORMAT = "YYYY MMM DD";
    var DEFAULT_TIME_FORMAT = "HH:mm:ss";
    var DEFAULT_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;

    var old_format = moment.fn.format;

    moment.lang(settingsStorage.get("lang") || "en");
    moment.fn.format = function (format) {
        return old_format.call(this, format ? format : DEFAULT_FORMAT);
    };


    return moment;
});