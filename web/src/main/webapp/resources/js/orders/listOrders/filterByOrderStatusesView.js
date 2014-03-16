/**
 * Filter by order statuses view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'common/messages',
    'text!orders/templates/filterByOrderStatuses.html'
], function ($, Backbone, Mustache, messages, filterByOrderStatusesTemplate) {

    var FilterByOrderStatusesView = Backbone.View.extend({
        el: '.orderStatus',

        events: {
            "click input": "change"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.field = "orderStateNotIn";
            this.filter = options.filter;
            this.model = {
                skipSent: typeof options.skipSent !== "undefined" ?  options.skipSent : false,
                skipRecieved: typeof options.skipRecieved !== "undefined" ?  options.skipRecieved : false,
                skipPostponed: typeof options.skipPostponed !== "undefined" ?  options.skipPostponed : false
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

            this.filter.set(this.field, toSkip.join(","));
        }
    });

    return FilterByOrderStatusesView;
});