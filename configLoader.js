var cliArgs = require("command-line-args"),
    allConfigs = require("./config"),
    cli = cliArgs([{
        name: "profile",
        type: String,
        alias: "p"
    }]),
    DEFAULT_PROFILE = "development";

var profileFromCli = cli.parse().profile,
    profileFromEnv = process.env.NODE_ENV,
    profile = DEFAULT_PROFILE;

if (profileFromCli) {
    profile = profileFromCli;
} else if (profileFromEnv) {
    profile = profileFromEnv;
}

console.log("profile:", profile);

module.exports = allConfigs[profile];