angular.module('application.account.calendar.eventAttribute', [
	'application.account.calendar.services.eventAttribute'
])
.config([
	'$stateProvider',
	function config( $stateProvider ) {
		$stateProvider
			.state('eventattribute', {
				url: '/eventattribute',
					views: {
						'accounts': {
							templateUrl: 'app/account/account-dropdown.tpl.html',
							controller: 'AccountCtrl'
						},
						'content': {
							controller: 'eventAttributeCtrl',
							templateUrl: 'app/account/calendar/event-attribute/event-attribute.tpl.html'
						}
					}
			});
		}
])
.controller('eventAttributeCtrl', ['$rootScope', '$scope', 'EventAttribute', 'sessionService',
	function($rootScope, $scope, EventAttribute, session) {

		$scope.eventAttribute = new EventAttribute();

		var getEventAttributes = function() {
            EventAttribute.query({'account_id' : session.getAccount()}, function(data) {
                $scope.eventAttributes = data;
            });
        };

        if(session.getAccount()) {
            getEventAttributes();
        } else {
            console.log('get Account to list EventAttribute');
        }

		$scope.newEventAttribute = function() {
				$scope.eventAttribute = new EventAttribute();
				$scope.editing = false;
		};

		$scope.activeEventAttribute = function(eventAttribute) {
				$scope.eventAttribute = eventAttribute;
				$scope.editing = true;
		};

		$scope.save = function(eventAttribute) {
			if (eventAttribute.id) {
				eventAttribute.$update({'account_id': session.getAccount()}, function(){
					getEventAttributes();
				});
			} else {
				eventAttribute.$save({'account_id': session.getAccount()}, function() {
					getEventAttributes();
				});
			}
			$scope.newEventAttribute();
		};

		$scope.delete = function(eventAttribute) {
            eventAttribute.$delete({account_id: session.getAccount()}, function(){
                getEventAttributes();
            });
        };

        $rootScope.$on('accountLoaded', function(event, args) {
            getEventAttributes();
        });

	}
]);