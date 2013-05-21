/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text',
    'text!templates/navbar.html',
    'common/messages',
    'common/context',
    'common/settingsStorage',
    'bootstrap'
], function ($, Backbone, Mustache, text, navbarTemplate, messages, context, settingsStorage) {

    var NavbarView = Backbone.View.extend({
        el: '.navbarPlaceholder',

        events: {
            "click a.lang": "changeLanguage",
            "click a.theme": "changeTheme"
        },

        dictionaries: ["productCategories", "shippingTypes", "orderStates", "discounts", "additionalPayments"],

        changeLanguage: function(event) {
            event.preventDefault();
            var link = $(event.currentTarget);
            var lang = link.attr("data-lang");

            settingsStorage.set("lang", lang);
            window.location = window.location;
        },

        changeTheme: function(event) {
            event.preventDefault();
            var link = $(event.currentTarget);
            var theme = link.attr("data-theme");

            settingsStorage.set("theme", theme);
            window.location = window.location;
        },

        getActivePage: function() {
            return $('meta[name=activePage]').attr("content")
        },

        render: function () {
            var model = {};
            var activePage = this.getActivePage();
            model[ activePage] = true;
            model["dictionaries"] = _.indexOf(this.dictionaries, activePage, false) >= 0;
            model["lang-" + settingsStorage.get('lang')] = true;
            model["theme-" + settingsStorage.get('theme')] = true;

            this.$el.html(Mustache.render(navbarTemplate, _.extend(model, {context: context}, messages)));
        }
    });

    return NavbarView;
});