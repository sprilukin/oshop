var should = require('should'),
    sequelize = require("../db/sequelize"),
    ProductCategory = require("../model/ProductCategory");

describe("test", function() {
    beforeEach(function(done) {
        sequelize.sync().then(function() {
            done();
        });
    });

    it("should test", function(done) {
        ProductCategory.create({
            name: "test"
        }).then(function(pc) {
            ProductCategory.findOne({
                where: {
                    id: pc.get("id")
                }
            }).then(function(pc1) {
                pc1.get("name").should.equal("test");
                done()
            });
        })
    })
});