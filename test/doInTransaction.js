var sequelize = require("../db/sequelize"),
    Sequelize = require("sequelize");


module.exports = function (callback) {
    sequelize.transaction({
        autocommit: false,
        isolationLevel: Sequelize.Transaction.ISOLATION_LEVELS.REPEATABLE_READ
    }).then(function (t1) {
        sequelize.sync().then(function () {
            callback({transaction: t1}, function (done) {
                t1.rollback().then(function () {
                    done();
                });
            });
        });
    });
};