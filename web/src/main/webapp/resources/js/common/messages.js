/**
 * Message bundles
 */
define([
    'underscore',
    'text!messagesBase/ru',
    'text!messagesBase/en',
    //'text!messagesBase/messages.properties',
    //'text!messagesBase/messages_en.properties',
    'common/settingsStorage'
], function (_, messages_ru, messages_en, settingsStorage) {

    var DOT_REPLACEMENT = "_";

    var msgs = {};
    var lang = settingsStorage.get("lang");
    var messages = lang === "ru" ? messages_ru : messages_en;

    _.each(JSON.parse(messages), function(value, key) {
        msgs[key.replace(/\./g, DOT_REPLACEMENT)] = value;
    });

    return msgs;
});