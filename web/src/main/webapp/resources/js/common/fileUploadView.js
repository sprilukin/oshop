/**
 * View for file upload
 */
define([
    "jquery",
    "underscore",
    'backbone',
    'mustache',
    'text',
    'text!templates/fileUpload.html',
    'text!templates/fileUploadPreview.html',
    "fileupload",
    "iframeTransport",
    "jqueryUiWidget"
], function ($, _, Backbone, Mustache, text, fileUploadTemplate, fileUploadPreviewTemplate) {

    var FileUploadModel = Backbone.Model.extend({
        url: function() {
            return Mustache.render("api/images/{{id}}", {id: this.id});
        }
    });

    var FileUploadCollection = Backbone.Collection.extend({
        model: FileUploadModel
    });

    var FileUploadView = Backbone.View.extend({
        initialize: function(options) {
            this.$el = options.element;
            this.width = options.width;
            this.multiple = options.multiple;
            this.collection = new FileUploadCollection();
        },

        /*events: {
            "closed .alert": "onClosed",
            "click .alert": "close"
        },*/

        render: function () {
            var that = this;

            this.$el.html(Mustache.render(fileUploadTemplate, {width: this.width, multiple: this.multiple}));
            this.$el.find(".fileUpload").fileupload({
                dataType: 'json',
                done: _.bind(that.done, that),
                add:  _.bind(that.add, that),
                progressall: _.bind(that.onProgress, that)
            });
        },

        add: function(e, data) {
            this.$el.find('.fileupload-progress .bar').css('width', 0 + '%');
            this.$el.find('.fileupload-progress').show();
            data.submit();
        },

        done: function (e, data) {
            var that = this;
            this.$el.find('.fileupload-progress').hide();
            _.each(data.result, function (id) {
                var model = new FileUploadModel();
                model.set("id", id);
                that.collection.add(model);

                that.$el.find(".imagesPreview").append(Mustache.render(fileUploadPreviewTemplate, {id: id}));
            });

            this.trigger("uploaded", {ids: data.result});
        },

        onProgress: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            this.$el.find('.fileupload-progress .bar').css('width', progress + '%');
        },

        destroy: function() {
            this.$el.find(".fileUpload").fileupload("destroy");
        },

        cancel: function() {
            this.collection.each(function(model) {
                model.destroy();
            });
        }
    });

    return FileUploadView;
});