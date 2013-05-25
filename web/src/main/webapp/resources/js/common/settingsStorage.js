/**
 * Settings storage which use cookies to store values
 */
define([
    'underscore',
    'cookies'
], function (_, cookies) {

    var DEFAULT_LANG = "ru";
    var DEFAULT_THEME = "default";
    var DEFAULT_ITEMS_PER_PAGE = 50;

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

            if (key === "itemsPerPage" && !cookies.get("itemsPerPage")) {
                return DEFAULT_ITEMS_PER_PAGE;
            }

            return cookies.get(key);
        }
    };

    return settingsStorage;
});