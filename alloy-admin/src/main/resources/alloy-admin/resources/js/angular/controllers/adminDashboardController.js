var adminApp = angular.module('alloyAdminDashboard', []);

adminApp.controller('alloyAdminNavbarController', function ($scope, $http) {
	$scope.simpleNavigationItems = [ 	
  	    	{name: 'Test', 
  				url: '/test' }
  		];
	
	$scope.complexNavigationItems = [ 	
			{name: 'Test 2', 
				url: '/test2',
				children: [{name: 'Sub Item 1', url:''}, {name: 'Sub Item 2', url:''}, {name: 'Sub Item 2', url:''}]} 
		];
});