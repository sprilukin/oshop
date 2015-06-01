var should = require('should'),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("../model/ProductCategory"),
    Product = require("../model/Product"),
    dropQuery = require("config").get("dropQuery");

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

    it("Should create instance with specified name", function (done) {
        ProductCategory.create({
            name: "test"
        }).then(function (pc) {
            Product.create({
                name: "product1",
                price: 11.22
            }).then(function (p1) {
                Product.create(
                    {
                        name: "product2",
                        price: 10
                    }
                ).then(function (p2) {
                        pc.setProducts([p1, p2]).then(function () {
                            pc.save().then(function(pc) {
                                pc.getProducts().then(function(products) {
                                    (products.length).should.be.equal(2);
                                    (products[0]).name.should.be.equal("product1");
                                    (products[1]).name.should.be.equal("product2");
                                    done();
                                });
                            })
                        })
                    });
            });
        });
    });
});