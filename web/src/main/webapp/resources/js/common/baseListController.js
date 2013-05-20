/**
 * Base List Controller
 */
define([
    'underscore',
    'mustache',
    'common/warningView',
    'common/paginationView',
    'common/searchView',
    'common/filter',
    'common/sorter',
    'common/baseListRouter'
], function (_, Mustache, WarningView, PaginationView, SearchView, Filter, Sorter, BaseListRouter) {

    var Controller = function(options) {
        this.initialize(options);
    };

    _.extend(Controller.prototype, {

        DELETE_URL_TEMPLATE: "delete/{{id}}",

        LIST_URL_TEMPLATE: "list/filter;{{filter}}/sort;{{sort}}/{{page}}",

        initialize: function(options) {
            options = _.extend({
                page: 1,
                itemsPerPage: 10,
                Filter: Filter,
                Sorter: Sorter,
                Model: undefined,
                Collection: undefined,
                View: undefined,
                PaginationView: PaginationView,
                SearchView: SearchView,
                search: undefined,
                Router: BaseListRouter
            }, options);

            this.Model = options.Model;
            this.page = options.page;
            this.itemsPerPage = options.itemsPerPage;
            this.filter = options.Filter && new options.Filter();
            this.sorter = options.Sorter && new options.Sorter();

            this.collection = new options.Collection();
            this.listView = new options.View({collection: this.collection, sorter: this.sorter});
            this.paginationView = options.PaginationView && new options.PaginationView({collection: this.collection});
            this.searchView = options.SearchView && new options.SearchView({collection: this.collection, filter: this.filter, fieldName: options.search});
            this.router = options.Router && new options.Router({controller: this});

            this.initEventListeners();
        },

        initEventListeners: function() {
            this.router && this.listView.on("delete",function (data) {
                this.router.navigate(Mustache.render(this.DELETE_URL_TEMPLATE, {id: data.id}), {trigger: true, replace: true});
            }, this);

            this.router && this.paginationView && this.paginationView.on("page:change",function (page) {
                this.page = parseInt(page, 10);
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.router && this.filter && this.filter.on("filter:change", function() {
                this.page = 1;
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.router && this.sorter && this.sorter.on("sort:change", function() {
                this.page = 1;
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        getListUrl: function() {
            var model = {page: this.page};
            if (this.filter) {
                model["filter"] = this.filter.format();
            }

            if (this.sorter) {
                model["sort"] = this.sorter.format();
            }

            return Mustache.render(this.LIST_URL_TEMPLATE, model);
        },

        list: function (filter, sort, page) {
            this.page = parseInt(page, 10) || 1;
            this.filter && this.filter.parse(filter, {silent:true});
            this.sorter && this.sorter.parse(sort, {silent:true});

            this.collection.setLimit(this.itemsPerPage);
            this.collection.setOffset(this.page - 1);
            this.filter && this.collection.setFilterString(this.filter.format());
            this.sorter && this.collection.setSorterString(this.sorter.format());
            this.collection.reset({silent: true});
            this.collection.fetch();
        },

        remove: function (id) {
            var that = this;

            var model = new this.Model({"id": id});
            model.destroy({
                wait: true,
                success: function () {
                    var maxPageCount = Math.ceil((that.collection.getTotal() - 1) / that.itemsPerPage);
                    that.page = Math.min(that.page, maxPageCount);

                    that.router && that.router.navigate(that.getListUrl(), {trigger: true});
                },

                error: function (model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.router && that.router.navigate(that.getListUrl(), {trigger: true});
                }
            });
        }
    });

    return Controller;
});