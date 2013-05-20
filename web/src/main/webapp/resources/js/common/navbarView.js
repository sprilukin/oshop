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
    'cookies',
    'bootstrap'
], function ($, Backbone, Mustache, text, navbarTemplate, messages, context, cookies) {

    var NavbarView = Backbone.View.extend({
        el: '.navbarPlaceholder',

        events: {
            "click a.lang": "changeLanguage",
            "click a.theme": "changeTheme"
        },

        dictionaries: ["productCategories"],

        changeLanguage: function(event) {
            event.preventDefault();
            var link = $(event.currentTarget);
            var lang = link.attr("data-lang");

            window.location = window.location + "?lang=" + lang;
        },

        changeTheme: function(event) {
            event.preventDefault();
            var link = $(event.currentTarget);
            var theme = link.attr("data-theme");

            window.location = window.location + "?theme=" + theme;
        },

        getActivePage: function() {
            return $('meta[name=activePage]').attr("content")
        },

        render: function () {
            var model = {};
            var activePage = this.getActivePage();
            model[ activePage] = true;
            model["dictionaries"] = _.indexOf(this.dictionaries, activePage, false) >= 0;
            model["lang-" + cookies('lang')] = true;
            model["theme-" + cookies('theme')] = true;

            this.$el.html(Mustache.render(navbarTemplate, _.extend(model, {context: context}, messages)));
        }
    });

    return NavbarView;
});