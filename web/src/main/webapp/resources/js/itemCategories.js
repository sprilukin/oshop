(function ($, oshop, api, utils) {
    oshop.itemCategories = (function() {

        var init = function() {
            attachListeners();
        };

        var attachListeners = function() {
            $("#addItemCategoryButton").on("click", function () {
                $("#addItemCategoryGroup").removeClass("error").find(".help-inline").html("");

                api.add(
                    JSON.stringify({"name": $("#addItemCategory").val()}),
                    function(obj) {
                        $("#itemCategoriesTable").append(utils.applyTemplate("#itemCategoryTemplate", obj));
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

        return {
            init : init
        }
    })()
})(jQuery, window.oshop, window.oshop.api.itemCategories, window.oshop.utils);
