define(function (require) {
    "use strict";

    var DOT_REPLACEMENT = "_",
        I18N_PATH = "/i18n/messages/";

    var bundlePluginFn = function(bundleName, callback) {
        require(['common/settingsStorage', "common/context"], function(settingsStorage, context) {
            var bundlePath = "text!" + context + I18N_PATH + settingsStorage.get("lang");
            require([bundlePath], function(resp) {
                callback(parse(resp));
            });
        });
    };

    bundlePluginFn.load = function (name, req, onLoad, config) {

        if (config.isBuild) {
            onLoad();
            return;
        }

        bundlePluginFn(name, onLoad);
    };

    var parse = function(messages) {
        var msgs = {};

        _.each(JSON.parse(messages), function(value, key) {
            msgs[key.replace(/\./g, DOT_REPLACEMENT)] = value;
        });

        return msgs;
    };

    return bundlePluginFn;
});