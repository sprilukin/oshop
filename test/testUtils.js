var sequelize = require("../db/sequelize"),
    dropQuery = require("config").get("dropQuery");

module.exports = {
    before: function (done) {
        sequelize.query(dropQuery).spread(function (results, metadata) {
            done();
        })
    },

    beforeEach: function (done) {
        sequelize.sync().then(function () {
            done()
        });
    },

    afterEach: function (done) {
        sequelize.query(dropQuery).spread(function (results, metadata) {
            done()
        })
    },

    tryCatch: function(fail) {
        return function(callback) {
            try {
                callback()
            } catch (e) {
                fail(e);
            }
        }
    }
};