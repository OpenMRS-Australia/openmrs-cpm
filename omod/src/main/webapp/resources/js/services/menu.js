define([
    './index',
    'config'
  ],

  function(services, config) {

    'use strict';

    services.service('Menu', function() {
      this.getMenu = function(activeIndex) {
        var menuItems = [{
            'link': config.contextPath + '/admin',
            'text': 'Admin'
          },
          {
            'link': config.contextPath + '/module/cpm/proposals.list#edit',
            'text': 'Create Proposal'
          },
          {
            'link': config.contextPath + '/module/cpm/proposals.list',
            'text': 'Monitor Proposals'
          },
          {
            'link': config.contextPath + '/module/cpm/proposalReview.list',
            'text': 'Incoming Proposals'
          },
          {
            'link': config.contextPath + '/module/cpm/proposals.list#settings',
            'text': 'Settings'
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