var should = require('should'),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("../model/ProductCategory"),
    dropQuery = require("config").get("dropQuery");

describe("test", function () {

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

    it("Should create ProductCategory with specified name", function (done) {
        ProductCategory.create({
            name: "test"
        }).then(function (pc) {
            ProductCategory.findOne({
                where: {
                    id: pc.id
                }
            }).then(function (pc1) {
                pc1.name.should.equal("test");
                done();
            });
        });
    });

    it("Should create only one instance of Product Category with same name because each test transaction rolls back", function (done) {
        ProductCategory.create({
            name: "test"
        }).then(function (pc) {
            ProductCategory.findAll({
                where: {
                    name: "test"
                }
            }).then(function (productCategories) {
                (productCategories.length).should.equal(1);
                productCategories[0].name.should.equal("test");
                done();
            });
        })
    })
});