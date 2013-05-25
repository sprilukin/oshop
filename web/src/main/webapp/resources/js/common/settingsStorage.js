/**
 * Settings storage which use cookies to store values
 */
define([
    'underscore',
    'cookies'
], function (_, cookies) {

    var DEFAULT_LANG = "ru";
    var DEFAULT_THEME = "default";

    var settingsStorage = {
        set: function(key, value) {
            cookies.set(key, value);
        },

        get: function(key) {
            if (key === "lang" && !cookies.get("lang")) {
                return DEFAULT_LANG;
            }

            if (key === "theme" && !cookies.get("theme")) {
                return DEFAULT_THEME;
            }

            return cookies.get(key);
        }
    };

    return settingsStorage;
});