(function ($, oshop, api, utils) {
    oshop.itemCategories = (function() {

        var init = function() {
            attachListeners();
            loadAllItemCategories();
        };

        var attachListeners = function() {
            $("#addItemCategoryButton").on("click", function () {
                $("#addItemCategoryGroup").removeClass("error").find(".help-inline").html("");

                api.add(
                    JSON.stringify({"name": $("#addItemCategory").val()}),
                    function(obj) {
                        $("#itemCategoriesTable").find("tbody").append(utils.applyTemplate("#itemCategoryTemplate", obj));
                        $("#addItemCategory").val("");
                    },
                    function(json, statusCode) {
                        $("#addItemCategoryGroup").addClass("error").find(".help-inline").html(json.fields.name);
                    });
            });

            $("#modalB").on("click", function () {
                $("#myModal").modal();
            });
        };

        var loadAllItemCategories = function() {
            api.list(
                function(obj) {
                    $("#itemCategoriesTableContainer").html(utils.applyTemplate("#itemCategoriesTemplate", obj));
                },
                function(json, statusCode) {
                    $("#itemCategoriesTableContainer").html(utils.applyTemplate("#itemCategoriesTemplate", []));
                });
        };

        return {
            init : init
        }
    })()
})(jQuery, window.oshop, window.oshop.api.itemCategories, window.oshop.utils);
