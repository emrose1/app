angular.module('application.services.event', [])
.provider('Event', function() {
	this.$get = ['$resource', function($resource) {

		var Event = $resource('http://localhost:8080/_ah/api/booking/v1/calendar/:calendar_id/event/:id', {
				calendar_id: '@calendar_id',
				id: '@id'
			},
			{
				update: {
					method: 'PUT'
				},
				query: {
					isArray:true,
					method:'GET',
					transformResponse: function (data, headers) {
						return JSON.parse(data).items;
					}
				}
			}
		);
		return Event;
	}];
});