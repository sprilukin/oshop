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

        initialize: function(options) {
            this.total = options.total;
            this.page = options.page;
            this.limit = options.limit;

            this.pagesCount = Math.ceil(this.total / this.limit);
            this.isFirst = this.page == 1;
            this.isLast = this.page == this.pagesCount;

            this.paginationPageStart = Math.max(this.isLast ? this.pagesCount - 5 : this.page - 2, 1);
            this.paginationPageEnd = Math.min(this.paginationPageStart + 5, this.pagesCount);

            var previousButtons = [
                {disabled: this.isFirst, active: false, page: 1, label: "&#171;"},
                {disabled: this.isFirst, active: false, page: this.page - 1, label: "&#60;"}
            ];
            var nextButtons = [
                {disabled: this.isLast, active: false, page: this.page + 1, label: "&#62;"},
                {disabled: this.isLast, active: false, page: this.pagesCount, label: "&#187;"}
            ];
            var pagesToShow = _.range(this.paginationPageStart, this.paginationPageEnd + 1);
            var pagesToShowButtons = _.map(pagesToShow, function(index) {
                return {disabled: false, active: index == this.page, page: index, label: index}
            }, this);

            this.model = _.union(previousButtons, pagesToShowButtons, nextButtons);
        },

        render: function () {
            this.$el.html(Mustache.render(paginationTemplate, {pages: this.model}));
        }
    });

    return PaginationView;
});