(function ($, oshop, api, utils) {
    oshop.itemCategories = (function() {

        var init = function() {
            attachListeners();
            loadAllItemCategories();
        };

        var attachListeners = function() {
            _.each(listeners, function(value, key) {
                $(key).on(value.events, value.selector, value.data, value.handler);
            })
        };

        var loadAllItemCategories = function() {
            api.itemCategories.list(
                function(json) {
                    json = json || [];
                    $("#itemCategoriesTableContainer").html(utils.applyTemplate("#itemCategoriesTemplate", json));
                },
                function(json) {
                    $("#itemCategoriesTableContainer").html(utils.applyTemplate("#itemCategoriesTemplate", []));
                });
        };

        var itemCategoryActions = {
            "removeItemCategory": function (id) {
                api.itemCategories.delete(id, function () {
                    loadAllItemCategories();
                });
            }};

        var listeners = {
            "#addItemCategoryButton": {events: "click", handler: function () {
                $("#addItemCategoryGroup").removeClass("error").find(".help-inline").html("");

                api.itemCategories.add(
                    JSON.stringify({"name": $("#addItemCategory").val()}),
                    function(json, statusCode) {
                        $("#itemCategoriesTable").find("tbody").append(utils.applyTemplate("#itemCategoryTemplate", json));
                        $("#addItemCategory").val("");
                        $("#addCategoryModal").modal("hide");
                    },
                    function(json, statusCode) {
                        $("#addItemCategoryGroup").addClass("error").find(".help-inline").html(json.fields.name);
                    });
            }},
            "#itemCategoriesTableContainer": {events: "click", selector: "li", handler: function(event) {
                var menuItem = $(this);
                var id = menuItem.attr("data-id");
                itemCategoryActions[menuItem.attr("data-action")](id);
            }}
        };

        return {
            init : init
        }
    })()
})(jQuery, window.oshop, window.oshop.api, window.oshop.utils);
