/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text',
    'text!templates/pagination.html',
    'bootstrap'
], function ($, Backbone, Mustache, text, paginationTemplate) {

    var PaginationView = Backbone.View.extend({
        el: '.forPagination',

        initialize: function() {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            var paginationDataForRendering = this.getPaginationDataForRendering(this.collection);

            this.$el.html(Mustache.render(paginationTemplate, {
                total: this.collection.total,
                pages: paginationDataForRendering
            }));
        },

        getPaginationDataForRendering: function(collection) {

            var pagesCount = Math.ceil(collection.total / collection.limit);
            var isFirst = collection.page == 1;
            var isLast = (collection.page == pagesCount);

            var paginationPageStart = Math.max(isLast ? pagesCount - 5 : collection.page - 2, 1);
            var paginationPageEnd = Math.min(paginationPageStart + 5, pagesCount);

            return _.union(
                //Previous buttons
                [
                    {disabled: isFirst, active: false, page: 1, label: "&#171;"},
                    {disabled: isFirst, active: false, page: this.page - 1, label: "&#60;"}
                ],

                _.map(_.range(paginationPageStart, paginationPageEnd + 1), function(index) {
                    return {disabled: false, active: index == collection.page, page: index, label: index}
                }, this),

                //Next buttons
                [
                    {disabled: isLast, active: false, page: collection.page + 1, label: "&#62;"},
                    {disabled: isLast, active: false, page: pagesCount, label: "&#187;"}
                ]
            )
        }
    });

    return PaginationView;
});