'use strict';

var Sequelize = require("sequelize"),
    _ = require("underscore"),
    dbConfig = require('../config.js').database;

module.exports = new Sequelize(dbConfig.name, dbConfig.user, dbConfig.password, {
    host: dbConfig.host,
    dialect: dbConfig.dialect,

    pool: dbConfig.pool,

    //general table options
    freezeTableName: true,  //do not use plural table names
    timestamps: true,       //use timestamp tables to save timestamp when entity was created
                            //and last updated (true is default value)
    createdAt: 'createdAt', //name of the createdAt table (createdAt is default)
    updatedAt: 'updatedAt',  //name of the updatedAt table (updatedAt is default)

    isolationLevel: Sequelize.REPEATABLE_READ
});