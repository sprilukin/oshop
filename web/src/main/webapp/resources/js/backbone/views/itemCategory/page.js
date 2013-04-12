define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text!templates/users/page.html',
    'collections/users'
], function($, _, Backbone, Mustache, Session, usersLayoutTemplate, UsersCollection){
    var ItemCategoriesPage = Backbone.View.extend({
        el: '.page',
        initialize: function () {
            var that = this;

        },
        render: function () {
            var that = this;
            var users = new UsersCollection();
            users.fetch({
                success: function (collection) {
                    that.$el.html(Mustache.render(usersLayoutTemplate,{users: collection.models}));
                }
            });
        }
    });
    return ItemCategoriesPage;
});
