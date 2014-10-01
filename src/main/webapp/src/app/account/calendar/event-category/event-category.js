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
                },
            resolve: {
                eventCategories: function() {
                    return EventCategory.query({'account_id' : session.getAccount()});
                }
            }
        });
    }
])
.controller('eventCategoryCtrl', ['$rootScope', '$scope', '$state', 'EventCategory', 'sessionService',
        function($rootScope, $scope, $state, EventCategory, session) {

        $scope.eventCategory = new EventCategory();

        $scope.reload = function() {
            $state.reload();
        };

        $scope.$state = $state;
        $scope.$watch('$state.$current.locals.globals.eventCategories', function (eventCategories) {
            $scope.event.eventCategory = eventCategories[0].id;
            $scope.eventCategorys = eventCategories;
        });


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