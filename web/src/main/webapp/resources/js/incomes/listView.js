/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/sortView',
    'common/context',
    'common/dateFormatter',
    'text!expenses/templates/list.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, SortView, context, dateFormatter, listEntityTemplate) {

    return Backbone.View.extend({

        el: '.listEntities',

        events: {
            "click a.delete": "delete"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.sorterViews = [];

            _.each(["id", "date", "description", "amount"], function(column) {
                this.sorterViews.push(new SortView({
                    column: column,
                    sorter: options.sorter
                }))
            }, this);
        },

        render: function () {
            var model = _.extend({context: context}, {models: _.map(this.collection.models, function(model) {
                var modelClone = _.extend({}, model.attributes);
                modelClone.date = dateFormatter(modelClone.date).format(messages["common_dateFormat"]);

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
        }
    });
});