module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    require('time-grunt')(grunt);

    grunt.initConfig({
        // Configure a mochaTest task
        mochaTest: {
            test: {
                options: {
                    reporter: 'spec',
                    captureFile: 'results.txt', // Optionally capture the reporter output to a file
                    quiet: false, // Optionally suppress output to standard out (defaults to false)
                    clearRequireCache: false // Optionally clear the require cache before running tests (defaults to false)
                },
                src: ['test/**/*.js']
            }
        },
        env : {
            dev : {
                NODE_ENV : 'development'
            },
            test : {
                NODE_ENV : 'test'
            }
        }
    });

    grunt.registerTask('test', ['env:test', 'mochaTest']);

    grunt.registerTask('default', ['test']);
};