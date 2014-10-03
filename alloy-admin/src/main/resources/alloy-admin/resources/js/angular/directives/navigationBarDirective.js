angular.module('alloyAdminDashboard', [])
.directive('alloyNavigationBar', function() {
	return {
		restrict : 'E',
		transclude : true,
		scope : {},
		controller : function($scope) {
			var panes = $scope.panes = [];

			$scope.select = function(pane) {
				angular.forEach(panes, function(pane) {
					pane.selected = false;
				});
				pane.selected = true;
			};

			this.addPane = function(pane) {
				if (panes.length === 0) {
					$scope.select(pane);
				}
				panes.push(pane);
			};
		},
		template : '${template.navigationBarTemplate}'
	};
});