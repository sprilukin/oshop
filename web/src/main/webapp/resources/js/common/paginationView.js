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

    var paginationItemsPerPage = 5;

    var PaginationView = Backbone.View.extend({
        el: '.forPagination',

        events: {
            "click a": "onClick"
        },

        initialize: function() {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        onClick: function(event) {
            var page = $(event.currentTarget).attr("data-page");
            event.preventDefault();

            this.trigger("page:change", page);
        },

        render: function () {
            this.$el.html(Mustache.render(paginationTemplate, {
                total: this.collection.total,
                pages: this.collection.total > 0 ? this.getPaginationDataForRendering(this.collection) : []
            }));
        },

        getPaginationDataForRendering: function(collection) {

            var pagesCount = Math.ceil(collection.total / collection.limit);

            //pagination of paginator
            var pagitationPagesCount = Math.ceil(pagesCount / paginationItemsPerPage);
            var currentPaginationPage = Math.floor((collection.page - 1) / paginationItemsPerPage) + 1;
            var isFirst = currentPaginationPage == 1;
            var isLast = (currentPaginationPage == pagitationPagesCount);

            var paginationPageStart = (currentPaginationPage - 1) * paginationItemsPerPage + 1;
            var paginationPageEnd = Math.min(paginationPageStart + paginationItemsPerPage - 1, pagesCount);

            return _.union(
                //Previous buttons
                [
                    {disabled: isFirst, active: false, page: 1, isFirst: true},
                    {disabled: isFirst, active: false, page: (currentPaginationPage - 1) * paginationItemsPerPage, isPrevious: true}
                ],

                _.map(_.range(paginationPageStart, paginationPageEnd + 1), function(index) {
                    return {disabled: false, active: index == collection.page, page: index, label: index}
                }, this),

                //Next buttons
                [
                    {disabled: isLast, active: false, page: currentPaginationPage * paginationItemsPerPage + 1, isNext: true},
                    {disabled: isLast, active: false, page: pagesCount, isLast: true}
                ]
            )
        }
    });

    return PaginationView;
});