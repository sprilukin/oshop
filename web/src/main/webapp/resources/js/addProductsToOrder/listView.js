/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/imageGallery',
    'common/messages',
    'common/sortView',
    'common/context',
    'text!templates/products/list.html',
    'text!templates/products/tileList.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, SortView, context, listEntityTemplate, tileListEntityTemplate) {

    return Backbone.View.extend({

        el: '.listEntities',

        events: {
            "click a.delete": "delete",
            "click #listType": "changeListType",
            "click div.tile-list-item": "select",
            "click tr": "select"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.selectedModel = options.selectedModel;

            this.listTemplate = tileListEntityTemplate;

            this.sorterViews = [];
            _.each(["id", "name", "description", "price"], function(column) {
                this.sorterViews.push(new SortView({
                    column: column,
                    sorter: options.sorter
                }))
            }, this);
        },

        render: function () {
            _.each(this.collection.models, function(model) {
                model.selected = this.selectedModel.contains(model.id);
                model.skipMenu = true;
            }, this);

            var model = _.extend({context: context}, this.collection, messages);

            this.$el.html(Mustache.render(this.listTemplate, model));

            _.each(this.sorterViews, function(view) {
                view.render();
            });
        },

        delete: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        },

        changeListType: function(event) {
            var checked = $(event.currentTarget).is(":checked");
            this.listTemplate = checked ? tileListEntityTemplate : listEntityTemplate;
            this.render();
        },

        select: function(event) {
            var $elem = $(event.currentTarget);
            var id = $elem.attr("data-id");

            if (event.ctrlKey) {
                this.selectedModel.toggle(id);

                $elem.toggleClass("selected");
            } else {
                this.$el.find(".selected").removeClass("selected");

                this.selectedModel.clear().add(id);
                $elem.addClass("selected");
            }
        }
    });
});
