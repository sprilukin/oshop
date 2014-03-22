var require = {
    baseUrl: "resources/js",

    paths: {
        // Major libraries
        jquery: 'lib/jquery-2.0.3',
        underscore: 'lib/underscore-1.4.4',
        backbone: 'lib/backbone-1.0.0',
        mustache: 'lib/mustache-0.7.2',
        bootstrap: 'lib/bootstrap-2.3.1',
        select2: 'lib/select2-3.3.2',
        cookies: 'lib/cookies-0.3.1',
        datePicker: 'lib/bootstrap-datepicker',
        datePickerRu: 'lib/bootstrap-datepicker.ru',
        moment: 'lib/moment-2.0.0',
        momentRu: 'lib/moment_ru',
        highcharts: 'lib/highstock.src-1.3.9',

        //fileupload
        fileupload: 'lib/fileup/jquery.fileupload-5.30',
        iframeTransport: 'lib/fileup/jquery.iframe-transport-1.6.1',
        jqueryUiWidget: 'lib/fileup/jquery.ui-widget-1.10.1',

        //image-gallery
        "load-image": 'lib/load-image-1.5',
        "image-gallery": 'lib/bootstrap-image-gallery-2.10',

        // Require.js plugins
        text: 'lib/require.text-2.0.5',
        bundle: 'common/bundle',
        bundleBase: "../../i18n/messages"
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