/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text!common/templates/pagination.html',
    "bundle!messages",
    'bootstrap'
], function ($, Backbone, Mustache, paginationTemplate, messages) {

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
            this.$el.html(Mustache.render(paginationTemplate, _.extend({
                total: this.collection.getTotal(),
                pages: this.collection.getTotal() > 0 ? this.getPaginationDataForRendering(this.collection) : []
            }, messages)));
        },

        getPaginationDataForRendering: function(collection) {

            var pagesCount = Math.ceil(collection.getTotal() / collection.getLimit());

            //pagination of paginator
            var pagitationPagesCount = Math.ceil(pagesCount / paginationItemsPerPage);
            var currentPaginationPage = Math.floor((collection.getOffset()) / paginationItemsPerPage) + 1;
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
                    return {disabled: false, active: index == collection.getOffset() + 1, page: index, label: index}
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