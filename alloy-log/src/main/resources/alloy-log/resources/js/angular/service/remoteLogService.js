angular.module('remoteLogging.service', [])
	.factory('remoteLog', ['$interval', '$http', function($interval, $http) {
		var methods = {};
		methods.poll = function(logId, chunkSize, delay) {
			if(!_.isDefined(chunkSize)) {
				chunkSize = 100;
			}
			if(!_.isDefined(delay)) {
				delay = 1000;
			}
			
			var since = null;
			var interval = $interval(pollInternal, delay);
			
			function pollInternal() {
				methods.getLogs(logId, since, chunkSize)
				.then(function(data) {
					
				});
			}
		};
		
		methods.getLogs = function(logId, since, limit) {
			return $http.get('/alloy/api/log/' + logId, {since: since, limit: limit})
				.success(function(data, status, headers, config) {
					console.log(data);
					return data;
				});
		};
		
		return methods;
	}]);