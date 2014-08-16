angular.module('application.services.calendar', [])
.provider('Calendar', function() {
	this.$get = ['$resource', function($resource) {

		var Calendar = $resource('http://localhost:8080/_ah/api/booking/v1/account/:account_id/calendar/:id', {
				account_id: '@account_id',
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
		return Calendar;
	}];
});
