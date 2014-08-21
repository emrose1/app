angular.module('application.account.calendar.services.eventAttribute', [])
.provider('EventAttribute', function() {
	this.$get = ['$resource', function($resource) {

		var EventAttribute = $resource('http://localhost:8080/_ah/api/booking/v1/account/:account_id/eventattribute/:id', {
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

		return EventAttribute;
	}];
});