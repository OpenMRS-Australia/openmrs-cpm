define([
    'angular',
    'config',
    './index'
  ],

  function(angular, config) {

    'use strict';

    angular.module('cpmr.services').service('Menu',
      function() {
      this.getMenu = function(activeIndex) {
        var menuItems = [{
            'link': config.contextPath + '/admin',
            'text': 'Admin'
          },
          {
            'link': config.contextPath + '/module/conceptreview/proposalReview.list',
            'text': 'Incoming Proposals'
          }
        ];

        if (activeIndex !== undefined && menuItems.length > activeIndex) {
          menuItems[activeIndex].active = true;
        }

        return menuItems;
      };
    });
  }
);