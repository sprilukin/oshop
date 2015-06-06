var should = require('should'),
    sequelize = require("../db/sequelize"),
    Order = require("../model/Order"),
    OrderState = require("../model/OrderState"),
    OrderHasOrderStates = require("../model/OrderHasOrderStates"),
    Product = require("../model/Product"),
    Promise = require("promise"),
    testUtils = require("./testUtils");

describe("Order - OrderState/Product associations tests", function () {
    before(function (done) {
        testUtils.before(done);
    });

    beforeEach(function (done) {
        testUtils.beforeEach(done);
    });

    afterEach(function (done) {
        testUtils.afterEach(done);
    });

    var createProductsAndStates = function () {
        var p1 = Order.create({
            date: Date.now(),
            description: "Order1"
        });

        var p2 = Product.create({
            name: "product1",
            price: 10
        });

        var p3 = Product.create({
            name: "product2",
            price: 10
        });

        var p4 = OrderState.create({
            name: "new"
        });

        var p5 = OrderState.create({
            name: "sent"
        });

        return Promise.all([p1, p2, p3, p4, p5]);
    };

    var associateOrderProductAndStates = function () {
        return new Promise(function (resolve, reject) {
            createProductsAndStates().then(function (res) {
                testUtils.tryCatch(reject)(function () {
                    var order = res[0],
                        product1 = res[1],
                        product2 = res[2],
                        state1 = res[3],
                        state2 = res[4];

                    var p1 = order.addProduct(product1),
                        p2 = order.addProduct(product2),
                        p3 = order.addOrderState(state1, {
                            date: Date.now(),
                            description: "ddddddd1"
                        }),
                        p4 = order.addOrderState(state2, {
                            date: Date.now(),
                            description: "ddddddd2"
                        });

                    Promise.all([p1, p2, p3, p4]).then(function () {
                        resolve();
                    })
                });
            }, function (err) {
                reject(err);
            });
        });
    };

    it("Should be able to add products and orderStates to order", function (done) {
        associateOrderProductAndStates().then(function () {
            Order.find({
                id: 1
            }).then(function (order) {
                order.getProducts().then(function (products) {
                    (products.length).should.be.equal(2);
                    (products[0]).name.should.be.equal("product1");
                    (products[1]).name.should.be.equal("product2");
                }).then(function () {
                    order.getOrderState().then(function (orderStates) {
                        (orderStates.length).should.be.equal(2);
                        done();
                    })
                });
            });
        }, function (err) {
            done(err);
        });
    });

    it("Should be able to remove products and orderStates from order", function (done) {
        associateOrderProductAndStates().then(function () {
            OrderState.destroy({
                where: {
                    id: {
                        $in: [1, 2]
                    }
                }
            }).then(function () {
                OrderHasOrderStates.findAll().then(function (states) {
                    (states.length).should.be.equal(0);
                    done();
                })
            })
        }, function (err) {
            done(err);
        });
    });
})
;