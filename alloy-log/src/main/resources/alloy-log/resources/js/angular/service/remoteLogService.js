angular.module('remoteLogging.service', [])
	.factory('remoteLog', ['$timeout', '$http', function($timeout, $http) {
		function pollInternal(logId, chunkSize, delay, since) {
			methods.getLogs(logId, since, chunkSize)
			.then(function(logContainer) {
				if(!logContainer.log.open) {
					delay = 0;
				}
			});
		};
		
		var properties = {};
		properties.poll = function(logId, chunkSize, delay) {
			if(!_.isDefined(chunkSize)) {
				chunkSize = 100;
			}
			if(!_.isDefined(delay)) {
				delay = 1000;
			}
			
			pollInternal(logId, chunkSize, delay, null);
		};
		
		properties.getLogs = function(logId, since, limit) {
			return $http.get('/alloy/api/log/' + logId, {since: since, limit: limit})
				.success(function(data, status, headers, config) {
					console.log(data);
					return data;
				});
		};
		
		return properties;
	}]);