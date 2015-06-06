var should = require('should'),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("../model/ProductCategory"),
    testUtils = require("./testUtils");

describe("ProductCategory tests", function () {

    before(function (done) {
        testUtils.before(done);
    });

    beforeEach(function (done) {
        testUtils.beforeEach(done);
    });

    afterEach(function (done) {
        testUtils.afterEach(done);
    });

    it("Should create instance with specified name", function (done) {
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

    it("Should create only one instance with same name because each test drops all data", function (done) {
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
    });

    it("Should not allow to create with empty name", function (done) {
        ProductCategory.create({
            name: null
        }).then(function (productCategory) {
            throw "Should not allow to create with empty name"
        }).catch(function(err) {
            done();
        })
    });

    it("Should not allow to create with same name", function (done) {
        ProductCategory.create({
            name: "name1"
        }).then(function () {
            ProductCategory.create({
                name: "name1"
            }).then(function (productCategory) {
                throw "Should not allow to create with same name"
            }).catch(function(err) {
                done();
            })
        });
    })
});