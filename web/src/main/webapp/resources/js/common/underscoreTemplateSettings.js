define(function (require) {
    "use strict";

    var _ = require("lib/underscore-1.6.0-patched");

    _.templateSettings = {
        evaluate:/\{\{([\s\S]+?)\}\}/g,
        interpolate:/\{\{=([\s\S]+?)\}\}/g,
        escape:/\{\{-([\s\S]+?)\}\}/g
    };

    return _;
});