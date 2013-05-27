//	.run(['$rootScope', '$location', function($rootScope, $location) {

//       $rootScope.isActiveTab = function(page, route) {
//           return $location.absUrl().indexOf(page + '.list') > 0 && $location.path() == route;
//       };
//   }]);

require(['config'], function(config) {
  requirejs.config({
    baseUrl: config.resourceLocation,
	  paths: {
	    'angular': 'lib/angular',
	    'angular-resource': 'lib/angular-resource',
      'domReady': 'lib/domReady',
	    'jquery': 'lib/jquery',
	    'jquery-ui': 'lib/jquery-ui',
	    'require': 'lib/require'
	  },
	  shim: {
	    'jquery-ui': ['jquery'],
	    'angular': {
	      deps: ['jquery'],
	      exports: 'angular'
	    },
	    'angular-resource': ['angular']
	  }
	});

	require(['js/cpmr-bootstrap'], function () {
	    // nothing to do here...see bootstrap.js
	});
});