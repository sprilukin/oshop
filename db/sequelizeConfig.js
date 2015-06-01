'use strict';

var Sequelize = require("sequelize"),
    dbConfig = require('config').get('database'),
    log = require("../logging/log");

module.exports = new Sequelize(dbConfig.name, dbConfig.user, dbConfig.password, {
    host: dbConfig.host,
    dialect: dbConfig.dialect,

    pool: dbConfig.pool,

    //general table options
    freezeTableName: true,  //do not use plural table names
    timestamps: true,       //use timestamp tables to save timestamp when entity was created
                            //and last updated (true is default value)
    createdAt: 'created_at', //name of the createdAt table (createdAt is default)
    updatedAt: 'updated_at',  //name of the updatedAt table (updatedAt is default)

    isolationLevel: Sequelize.REPEATABLE_READ,

    logging: function() {
        if (dbConfig.showSql) {
            log.debug.apply(log, arguments);
        }
    }
});