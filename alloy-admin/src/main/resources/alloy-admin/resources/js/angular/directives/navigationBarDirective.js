var adminApp = angular.module('alloyAdminDashboard', []);

adminApp.directive('alloyNavigationBar', function() {
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
		template : '${template.navbar.inject}'
	};
});

/*
<% template.navbar.start >
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a href="../" class="navbar-brand">Bootswatch</a>
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="navbar-main">
			<li ng-repeat="navigationItem in simpleNavigationItems">
				<a href="{{navigationItem.url}}">
					{{navigationItem.name}}
				</a>
			</li>
			
			<li class="dropdown" ng-repeat="navigationItem in complexNavigationItems">
				<a class="dropdown-toggle" data-toggle="dropdown" href="{{navigationItem.url}}" id="{{navigationItem.name}}">{{navigationItem.name}} <span class="caret"></span></a>
				<ul class="dropdown-menu" aria-labelledby="{{navigationItem.name}}">
					<li ng-repeat="childNavigationItem in navigationItem.children">
						<a href="{{childNavigationItem.url}}">
							{{childNavigationItem.name}}
						</a>
					</li>
				</ul>
			</li>
		</div>
	</div>
</div>
<% template.navbar.end >
*/