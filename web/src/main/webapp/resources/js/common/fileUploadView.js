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

    var ImagePreviewView = Backbone.View.extend({

        el: ".imagesPreview",

        initialize: function(options) {
            this.collection = options.collection;
            this.collection.on("sync", this.render, this);
        },

        render: function () {
            this.$el.html(Mustache.render(fileUploadPreviewTemplate, {images: this.collection.models}));
        }
    });

    var FileUploadView = Backbone.View.extend({
        initialize: function(options) {
            this.$el = options.element;
            this.width = options.width;
            this.multiple = options.multiple;
            this.collection = new FileUploadCollection(_.map(options.images, function(id) {return {id: id, original: true}}));
            this.toRemoveCollection = new FileUploadCollection();
        },

        render: function () {
            var that = this;

            this.$el.html(Mustache.render(fileUploadTemplate, {width: this.width, multiple: this.multiple}));
            this.$el.find(".fileUpload").fileupload({
                dataType: 'json',
                done: _.bind(that.done, that),
                add:  _.bind(that.add, that),
                fail:  _.bind(that.fail, that),
                always:  _.bind(that.always, that),
                progressall: _.bind(that.onProgress, that)
            });

            if (!this.imagePreviewView) {
                this.imagePreviewView = new ImagePreviewView({
                    collection: this.collection
                });
                this.imagePreviewView.render();
            }
        },

        add: function(e, data) {
            this.$el.find('.fileupload-progress .bar').css('width', 0 + '%');
            this.$el.find('.fileupload-progress').show();
            this.$el.find('.help-inline').html("").hide();
            data.submit();
        },

        getImageIds: function() {
            return this.collection.map(function(model) {
                return model.id;
            });
        },

        fail: function (event, xhr) {
            this.$el.find('.help-inline').html(JSON.parse(xhr.jqXHR.responseText)).show();
        },

        always: function () {
            this.$el.find('.fileupload-progress').hide();
        },

        done: function (e, data) {
            var that = this;

            var models = _.map(data.result, function (id) {
                return {id: id, original: false}
            });

            if (this.multiple) {
                this.collection.add(models, {silent: true});
            } else {
                this.collection.each(function(model) {
                    !model.get("original") && model.destroy();
                    model.get("original") && this.toRemoveCollection.add(model);
                }, this);
                this.collection.reset(models, {silent: true});
            }

            this.collection.trigger("sync");

            this.trigger("uploaded", {ids: data.result});
        },

        onProgress: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            this.$el.find('.fileupload-progress .bar').css('width', progress + '%');
        },

        destroy: function() {
            this.$el.find(".fileUpload").fileupload("destroy");
        },

        submit: function() {
            this.toRemoveCollection.each(function(model) {
                model.destroy();
            });

            this.destroy();
        },

        cancel: function() {
            this.collection.each(function(model) {
                if (!model.get("original")) {
                    model.destroy();
                }
            });

            this.destroy();
        }
    });

    return FileUploadView;
});