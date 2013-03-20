(function ($, oshop, api, utils) {
    oshop.itemCategories = (function() {

        var init = function() {
            attachListeners();
            loadAllItemCategories();
        };

        var attachListeners = function() {
            _.each(clickListeners, function(value, key) {
                $(key).on("click", value);
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

        var removeItemCategory = function(id) {
            api.itemCategories.delete(id, function() {
                loadAllItemCategories();
            });
        };

        var clickListeners = {
            "#addItemCategoryButton": function () {
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
            },
            "#itemCategoriesTableContainer": function(event) {
                var target = $(event.target).parent("li");
                if (target.size() > 0) {
                    var id = target.attr("data-id");
                    var action = target.attr("data-action");
                    if (action === "delete") {
                        removeItemCategory(id);
                    }
                }
            }
        };

        return {
            init : init
        }
    })()
})(jQuery, window.oshop, window.oshop.api, window.oshop.utils);
