/**
 * Message bundles
 */
define([
    'underscore',
    'text',
    'text!messages'
], function (_, text, messages) {

    var DOT_REPLACEMENT = "_";

    var msgs = {};

    _.each(JSON.parse(messages), function(value, key) {
        msgs[key.replace(/\./g, DOT_REPLACEMENT)] = value;
    });

    return msgs;
});