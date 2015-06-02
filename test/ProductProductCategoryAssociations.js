var should = require('should'),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("../model/ProductCategory"),
    Product = require("../model/Product"),
    dropQuery = require("config").get("dropQuery"),
    Promise = require("promise");

describe("Product - ProductCategory associations tests", function () {

    before(function (done) {
        sequelize.query(dropQuery).spread(function (results, metadata) {
            done();
        })
    });

    beforeEach(function (done) {
        sequelize.sync().then(function () {
            done()
        });
    });

    afterEach(function (done) {
        sequelize.query(dropQuery).spread(function (results, metadata) {
            done()
        })
    });

    var fillWithData = function () {
        var pc = ProductCategory.create({
            name: "test"
        });

        var p1 = Product.create({
            name: "product1",
            price: 11.22
        });

        var p2 = Product.create({
            name: "product2",
            price: 10
        });

        return Promise.all([pc, p1, p2]);
    };

    it("Should be able to add products to ProductCategory", function (done) {
        fillWithData().then(function (res) {
            var pc = res[0],
                p1 = res[1],
                p2 = res[2];

            pc.setProducts([p1, p2]);
            pc.save().then(function (pc) {
                pc.getProducts().then(function (products) {
                    (products.length).should.be.equal(2);
                    (products[0]).name.should.be.equal("product1");
                    (products[1]).name.should.be.equal("product2");
                    done();
                });
            })
        });
    });

    it("Should be able to add ProductCategory to products", function (done) {
        fillWithData().then(function (res) {
            var pc = res[0],
                p1 = res[1],
                p2 = res[2];

            p1.setProductCategory(pc);
            p2.setProductCategory(pc);

            Promise.all([p1.save(), p2.save()]).then(function (res) {

                Product.findAll({include: [{
                    model: ProductCategory,
                    as: "ProductCategory"
                }]}).then(function (products) {
                    (products.length).should.be.equal(2);
                    (products[0].ProductCategory.id).should.be.equal(1);
                    (products[1].ProductCategory.id).should.be.equal(1);

                    done();
                });
            })
        });
    });
});