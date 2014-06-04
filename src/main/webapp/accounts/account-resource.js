Application.Services
.provider('Account', function() {


	this.$get = ['$resource', function($resource) {
		var Account = $resource('http://localhost:8080/_ah/api/booking/v1/account/:id', {}, {
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
		});

		return Account;
	}];
});
