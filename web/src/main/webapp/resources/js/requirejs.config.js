var require = {
    baseUrl: "resources/js",

    paths: {
        // Major libraries
        //jquery: 'lib/jquery-1.9.1',
        jquery: 'lib/jquery-2.0.3',
        underscore: 'lib/underscore',
        backbone: 'lib/backbone-1.0.0',
        mustache: 'lib/mustache',
        bootstrap: 'lib/bootstrap',
        select2: 'lib/select2',
        cookies: 'lib/cookies',
        datePicker: 'lib/bootstrap-datepicker',
        datePickerRu: 'lib/bootstrap-datepicker.ru',
        moment: 'lib/moment',
        momentRu: 'lib/moment_ru',
        //fileupload
        fileupload: 'lib/fileup/jquery.fileupload',
        iframeTransport: 'lib/fileup/jquery.iframe-transport',
        jqueryUiWidget: 'lib/fileup/jquery.ui-widget',
        //image-gallery
        "load-image": 'lib/load-image',
        "image-gallery": 'lib/bootstrap-image-gallery',
        // Require.js plugins
        text: 'lib/require.text',
        templates: '../../templates',
        messagesBase: '../../i18n/messages',
        //messagesBase: 'i18n'
        chart: 'lib/Chart'
    },

    shim: {
        bootstrap: {
            deps: ["jquery"]
        },
        select2: {
            deps: ["jquery"]
        },
        underscore: {
            exports: '_'
        },
        backbone: {
            deps: ["underscore", "jquery"],
            exports: "Backbone"
        }
    }
};