'use strict';

var Sequelize = require("sequelize"),
    config = require('config'),
    log = require("../logging/log");

module.exports = new Sequelize(
    config.get("database.name"),
    config.get("database.user"),
    config.get("database.password"),
    {
        host: config.get("database.host"),
        dialect: config.get("database.dialect"),

        pool: config.get("database.pool"),

        //general table options
        freezeTableName: true,  //do not use plural table names
        timestamps: true,       //use timestamp tables to save timestamp when entity was created
                                //and last updated (true is default value)
        createdAt: 'created_at', //name of the createdAt table (createdAt is default)
        updatedAt: 'updated_at',  //name of the updatedAt table (updatedAt is default)

        isolationLevel: config.get("database.isolationLevel"),

        logging: function () {
            if (config.get("database.showSql")) {
                log.debug.apply(log, arguments);
            }
        }
    }
);