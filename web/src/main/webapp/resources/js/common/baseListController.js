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
    'common/baseListRouter',
    'common/settingsStorage'
], function (_, Mustache, WarningView, PaginationView, SearchView, Filter, Sorter, BaseListRouter, settingsStorage) {

    var Controller = function(options) {
        this.initialize(options);
    };

    _.extend(Controller.prototype, {

        DELETE_URL_TEMPLATE: "delete/{{id}}",

        LIST_URL_TEMPLATE: "list/filter;{{filter}}/sort;{{sort}}/{{page}}",

        initialize: function(options) {
            //options = {
                //page: 1,
                //itemsPerPage: settingsStorage.get("itemsPerPage"),
                //filter: new Filter(),
                //sorter: new Sorter(),
                //Model: undefined,
                //collection: undefined,
                //View: undefined, or view: undefined,
                //paginationView: new PaginationView(),
                //searchView: new SearchView(),
                //search: undefined,
                //router: new BaseListRouter()
            //};

            this.Model = options.Model;
            this.page = options.page || 1;
            this.itemsPerPage = options.itemsPerPage || settingsStorage.get("itemsPerPage");
            this.filter = new Filter();
            this.defaultFilter = options.filter || new Filter();
            this.sorter = new Sorter();
            this.defaultSorter = options.sorter || new Sorter();

            this.collection = options.collection;
            this.listView = options.view || new options.View({collection: this.collection, sorter: this.sorter});
            this.paginationView = options.paginationView || new PaginationView({collection: this.collection});
            this.searchView = options.searchView || new SearchView({collection: this.collection, filter: this.filter, search: options.search});
            this.router = options.router || new BaseListRouter({controller: this});

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
            this.filter && this.filter.parse(filter ? filter : this.defaultFilter.format(), {silent:true});
            this.sorter && this.sorter.parse(sort ? sort : this.defaultSorter.format(), {silent:true});

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
                    //new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.router && that.router.navigate(that.getListUrl(), {trigger: true});
                }
            });
        }
    });

    return Controller;
});