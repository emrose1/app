angular.module('application.account.calendar.eventCategory', [
    'application.account.calendar.services.eventCategory'
])
.config([
    '$stateProvider', function config( $stateProvider ) {
        $stateProvider.state('eventcategory', {
            url: '/eventcategory',
                views: {
                    'accounts': {
                        templateUrl: 'app/account/account-dropdown.tpl.html',
                        controller: 'AccountCtrl'
                    },
                    'content': {
                        controller: 'eventCategoryCtrl',
                        templateUrl: 'app/account/calendar/event-category/event-category.tpl.html'
                    }
                }
        });
    }
])
.controller('eventCategoryCtrl', ['$rootScope', '$scope', 'EventCategory', 'sessionService',
        function($rootScope, $scope, EventCategory, session) {

        $scope.eventCategory = new EventCategory();

        var getEventCategorys = function() {
            EventCategory.query({'account_id' : session.getAccount()}, function(data) {
                $scope.eventCategorys = data;
              });
        };

        if(session.getAccount()) {
            getEventCategorys();
        } else {
            console.log('get Account to list Event Cats');
        }

        $scope.newEventCategory = function() {
                $scope.eventCategory = new EventCategory();
                $scope.editing = false;
        };

        $scope.activeEventCategory = function(eventCategory) {
                $scope.eventCategory = eventCategory;
                $scope.editing = true;
        };

        $scope.save = function(eventCategory) {
            if (eventCategory.id) {
                eventCategory.$update({'account_id': session.getAccount()}, function(){
                    getEventCategories();
                });
            } else {
                eventCategory.$save({'account_id': session.getAccount()}, function() {
                    getEventCategorys();
                });
            }
            $scope.newEventCategory();
        };

        $scope.delete = function(eventCategory) {
            eventCategory.$delete({account_id: session.getAccount()}, function(){
                getEventCategories();
            });
        };

        $rootScope.$on('accountLoaded', function(event, args) {
            getEventCategorys();
        });


    }
]);