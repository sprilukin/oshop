/**
 * Settings storage which use cookies to store values
 */
define([
    'underscore',
    'cookies'
], function (_, cookies) {

    var settingsStorage = {
        set: function(key, value) {
            cookies.set(key, value);
        },

        get: function(key) {
            return cookies.get(key)
        }
    };

    return settingsStorage;
});