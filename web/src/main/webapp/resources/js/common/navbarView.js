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
    'bootstrap'
], function ($, Backbone, Mustache, text, navbarTemplate, messages) {

    var NavbarView = Backbone.View.extend({
        el: '.navbarPlaceholder',

        events: {
            "click a": "onClick"
        },

        dictionaries: ["productCategories"],

        onClick: function(event) {
            /*var page = $(event.currentTarget).attr("data-page");
            event.preventDefault();

            this.trigger("page:change", page);*/

            console.log(arguments);

            event.preventDefault();
        },

        getActivePage: function() {
            return $('meta[name=activePage]').attr("content")
        },

        render: function () {
            var model = {};
            var activePage = this.getActivePage();
            model[ activePage] = true;
            model["dictionaries"] = _.indexOf(this.dictionaries, activePage, false) >= 0;

            this.$el.html(Mustache.render(navbarTemplate, _.extend(model, messages)));
        }
    });

    return NavbarView;
});