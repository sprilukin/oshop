({
    //appDir: "${basedir}/src/main/webapp/resources/js",
    mainConfigFile: 'requirejs.config.js',
    baseUrl: "./",
    optimize: "uglify",
    skipDirOptimize: true,
    removeCombined: true,
    preserveLicenseComments: false,
    //dir: "${project.build.directory}/${project.build.finalName}/resources/js",

    paths: {
        "bundleBase": "empty:"
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
