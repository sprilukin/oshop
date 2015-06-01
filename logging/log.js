var winston = require('winston'),
    config = require("config"),
    _ = require("underscore");

var defaultExceptionOpts = {
        name: "console-exception-log",
        json: config.get("logger.json"),
        timestamp: config.get("logger.timestamp")
    },
    defaultOpts = _.defaults({
        name: "console-log",
        level: config.get("logger.logLevel")
    }, defaultExceptionOpts);

var logger = new winston.Logger({
    transports: [
        new winston.transports.Console(defaultOpts),
        new winston.transports.File(_.defaults({
            name: "file-log",
            filename: __dirname + "/" + config.get("logger.logFile")
        }, defaultOpts))
    ],
    exceptionHandlers: [
        new winston.transports.Console(defaultExceptionOpts),
        new winston.transports.File(_.defaults({
            name: "file-exception-log",
            filename: __dirname + "/" + config.get("logger.exceptionLogFile")
        }, defaultExceptionOpts))
    ],
    exitOnError: false
});

module.exports = logger;