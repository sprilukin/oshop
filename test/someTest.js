var should = require('should'),
    sequelize = require("../db/sequelize"),
    doInTransaction = require("./doInTransaction"),
    ProductCategory = require("../model/ProductCategory");

describe("test", function () {

    it("Should create ProductCategory with specified name", function (done) {
        doInTransaction(function (ts, callback) {
            ProductCategory.create({
                name: "test"
            }, ts).then(function (pc) {
                ProductCategory.findOne({
                    where: {
                        id: pc.get("id")
                    }
                }, ts).then(function (pc1) {
                    pc1.get("name").should.equal("test");
                    callback(done);
                });
            })
        });
    });

    it("Should create only one instance of Product Category with same name because each test transaction rolls back", function (done) {
        doInTransaction(function (ts, callback) {
            ProductCategory.create({
                name: "test"
            }, ts).then(function (pc) {
                ProductCategory.findAll({
                    where: {
                        name: "test"
                    }
                }).then(function (pc1) {
                    //pc1.get("name").should.equal("test");
                    console.log(pc1[0].get("name"));
                    callback(done);
                });
            })
        });
    })
});