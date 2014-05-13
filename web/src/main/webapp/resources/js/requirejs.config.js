var require = {
    //baseUrl: "resources/js",

    paths: {
        // Major libraries
        jquery: 'lib/jquery-2.1.0',
        underscore: 'common/underscoreTemplateSettings',
        backbone: 'lib/backbone-associations-0.6.1-patched',
        "backbone.wreqr": 'lib/backbone.wreqr-1.3.1',
        "backbone.babysitter": 'lib/backbone.babysitter-0.1.4',
        mustache: 'lib/mustache-0.7.2',
        bootstrap: 'lib/bootstrap-2.3.1',
        marionette: 'lib/backbone.marionette-1.8.5',
        select2: 'lib/select2-3.3.2',
        cookies: 'lib/cookies-0.3.1',
        datePicker: 'lib/bootstrap-datepicker',
        datePickerRu: 'lib/bootstrap-datepicker.ru',
        moment: 'lib/moment-2.0.0',
        momentRu: 'lib/moment_ru',
        highcharts: 'lib/highstock.src-1.3.9',
        async: "lib/async-0.1.2",

        //fileupload
        fileupload: 'lib/fileup/jquery.fileupload-5.30',
        iframeTransport: 'lib/fileup/jquery.iframe-transport-1.6.1',
        jqueryUiWidget: 'lib/fileup/jquery.ui-widget-1.10.1',

        //image-gallery
        "load-image": 'lib/load-image-1.5',
        "image-gallery": 'lib/bootstrap-image-gallery-2.10',

        // Require.js plugins
        text: 'lib/require.text-2.0.5',
        bundle: 'common/bundle'
    },

    shim: {
        bootstrap: {
            deps: ["jquery"]
        },
        select2: {
            deps: ["jquery"]
        }
    }
};