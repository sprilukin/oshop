({
    appDir: ".",
    mainConfigFile: 'js/requirejs.config.js',
    baseUrl: "js",
    optimize: "uglify",
    skipDirOptimize: false,
    removeCombined: false,
    preserveLicenseComments: false,
    //dir: "${project.build.directory}/${project.build.finalName}/resources",

    optimizeCss: "standard",
    cssImportIgnore: null,

    paths: {
        "jquery": "empty:",
        "underscore": "empty:",
        "backbone": "empty:",
        "returnAddress": "empty:"
    },

    modules: [
        {name: "additionalPayments/main"},
        {name: "addProductsToOrder/main"},
        {name: "cities/main"},
        {name: "customers/main"},
        {name: "discounts/main"},
        {name: "expenses/main"},
        {name: "incomes/main"},
        {name: "orders/editOrder/edit"},
        {name: "orders/listOrders/list"},
        {name: "orderStates/main"},
        {name: "productCategories/main"},
        {name: "products/main"},
        {name: "shippingAddresses/main"},
        {name: "shippingTypes/main"},
        {name: "invoicePrint/mainUkrPost"},
        {name: "invoicePrint/mainSalesReceipt"},
        {name: "dashboard/main"}
    ]
})
