/**
 * Filter by order statuses view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'common/messages',
    'orderStates/collection',
    'text!templates/products/filterByOrderStatuses.html'
], function ($, Backbone, Mustache, messages, OrderStatesCollection, filterByOrderStatusesTemplate) {

    var FilterByOrderStatusesView = Backbone.View.extend({
        el: '.orderStatus',

        events: {
            "click input": "change"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            var that = this;
            new OrderStatesCollection().fetch({success: function(collection, result) {
                that.allStatuses = _.map(result, function(item) {
                    return item.name;
                });
            }});

            this.field = "orderStateIn";
            this.filter = options.filter;
            this.model = {
                skipSent: typeof options.skipSent !== "undefined" ?  options.skipSent : true,
                skipRecieved: typeof options.skipRecieved !== "undefined" ?  options.skipRecieved : true,
                skipPostponed: typeof options.skipPostponed !== "undefined" ?  options.skipPostponed : true
            };
        },

        render: function () {
            this.$el.html(Mustache.render(filterByOrderStatusesTemplate, _.extend(this.model, messages)));
        },

        change: function() {
            var that = this;

            var toSkip = [];
            this.$el.find("input").each(function(index, el) {
                var $el = $(el);

                var checked = $(el).is(':checked');

                checked && toSkip.push($el.attr("data-status"));
                that.model[$el.attr("id")] = checked;
            });

            var statuses = _.difference(this.allStatuses, toSkip).join(",");
            this.filter.set(this.field, statuses);
        }
    });

    return FilterByOrderStatusesView;
});