angular.module('alloy.remoteLogging.service', [])
	.factory('remoteLoggingService', ['$timeout', '$http', '$q', function($timeout, $http, $q) {
		function pollInternal(logId, chunkSize, delay, since) {
			methods.getLogs(logId, since, chunkSize)
			.then(function(logContainer) {
				if(!logContainer.log.open) {
					delay = 0;
					if(_.isEmpty(logContainer.entries)) {
						return $q.defer().promise;
					}
				}
				
				var lastEntry = _.last(logContainer.entries);
				since = lastEntry.timestamp.createdDate;
				return $timeout(pollInternal(logId, chunkSize, delay, since), delay);
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
			
			return pollInternal(logId, chunkSize, delay, null);
		};
		
		properties.getLogs = function(logId, since, limit) {
			return $http.get('/alloy/api/log/' + logId, {since: since, limit: limit})
				.success(function(logContainer, status, headers, config) {
					console.log(logContainer);
					return logContainer;
				});
		};
		
		return properties;
	}]);