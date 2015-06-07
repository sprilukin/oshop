/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    "bundle!messages",
    'common/sortView',
    'common/context',
    'common/dateFormatter',
    'text!orders/templates/list.html',
    'common/imageGallery',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, SortView, context, dateFormatter, listEntityTemplate) {

    return Backbone.View.extend({

        el: '.listEntities',

        events: {
            "click a.delete": "delete",
            "click tr": "select"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.selectedModel = options.selectedModel;

            this.sorterViews = [];

            _.each(["id", "date", "customer", "productsCount", "productsPrice",
                "totalPrice", "currentOrderStateDate", "currentOrderStateName"], function(column) {

                this.sorterViews.push(new SortView({
                    column: column,
                    sorter: options.sorter
                }))
            }, this);
        },

        render: function () {
            var model = _.extend({context: context}, {models: _.map(this.collection.models, function(model) {
                var modelClone = _.extend({}, model.attributes);
                modelClone.date = dateFormatter(modelClone.date).format();
                modelClone.currentOrderStateDate = dateFormatter(modelClone.currentOrderStateDate).format();

                return {id: modelClone.id, attributes: modelClone};
            })}, messages);

            this.$el.html(Mustache.render(listEntityTemplate, model));

            _.each(this.sorterViews, function(view) {
                view.render();
            });
        },

        delete: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        },

        select: function(event) {
            var $elem = $(event.currentTarget);
            if ($elem.find("th").length > 0) {
                return;
            }

            var id = $elem.find("ul a").attr("data-id");

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