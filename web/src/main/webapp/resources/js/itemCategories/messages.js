/**
 * Message bundles
 */
define([
    'text',
    'text!messages'
], function (text, messages) {

    return JSON.parse(messages);
});