var winston = require('winston'),
    config = require("config").get("logger");

var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({
            name: "console-log",
            json: config.json,
            timestamp: config.timestamp,
            level: config.logLevel
        }),
        new winston.transports.File({
            name: "file-log",
            filename: __dirname + "/" + config.logFile,
            json: config.json,
            timestamp: config.timestamp,
            level: config.logLevel
        })
    ],
    exceptionHandlers: [
        new (winston.transports.Console)({
            name: "console-exception-log",
            json: config.json,
            timestamp: config.timestamp
        }),
        new winston.transports.File({
            name: "file-exception-log",
            filename: __dirname + "/" + config.exceptionLogFile,
            json: config.json,
            timestamp: config.timestamp
        })
    ],
    exitOnError: false
});

module.exports = logger;