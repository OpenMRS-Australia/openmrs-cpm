// to run karma:
// $ karma start

var tests = Object.keys(window.__karma__.files).filter(function (file) {
    return /Spec\.js$/.test(file);
});

var config = {
    resourceLocation: '/base/main/webapp/resources',
    contextPath: '/openmrs'
};

define('config', [], function() { return config; })

require.config({
    baseUrl: config.resourceLocation,
    paths: {
        'unit': '../../../test/webapp/unit',
        'angular': 'lib/angular',
        'angular-mocks': '../../../test/webapp/lib/angular-mocks',
        'angular-resource': 'lib/angular-resource',
        'domReady': 'lib/domReady',
        'jquery': 'lib/jquery',
        'jquery-ui': 'lib/jquery-ui',
        'underscore': 'lib/underscore'
    },
    shim: {
        'underscore': {
            exports: '_'
        },
        'jquery-ui': ['jquery'],
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angular-mocks': ['angular'],
        'angular-resource': ['angular']
    },
    // ask Require.js to load these files (all our tests)
    deps: tests,
    // start test run, once Require.js is done
    callback: window.__karma__.start
});