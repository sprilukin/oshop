/**
 * Message bundles
 */
define([
    'underscore',
    'text',
    'text!messages_ru',
    'text!messages_en',
    'common/settingsStorage'
], function (_, text, messages_ru, messages_en, settingsStorage) {

    var DOT_REPLACEMENT = "_";

    var msgs = {};
    var lang = settingsStorage.get("lang");
    var messages = lang === "ru" ? messages_ru : messages_en;

    _.each(JSON.parse(messages), function(value, key) {
        msgs[key.replace(/\./g, DOT_REPLACEMENT)] = value;
    });

    return msgs;
});