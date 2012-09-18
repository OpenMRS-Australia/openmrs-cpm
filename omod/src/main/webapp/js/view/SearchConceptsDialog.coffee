#define ['text!js/view/SearchConceptsDialog.html'], (templateStr) ->

#
# This class would represent a view in the Model-View-Presenter pattern
# (or sub-view for dialogs that belong to a larger view)
#
# There are 2 different flavours of MVP:
#  - Delegating all user actions to the presenter; or
#  - Allowing the view to manage user events and delegating only higher level
#    events to the presenter
#
# This view follows the 2nd flavour and encapsulates DOM manipulation & event
# handling.  This way any calling classes need not know about HTML, CSS,
# jQuery-UI, etc.  The view knows nothing about communicating back to the server,
# except only to link to pages that are still managed via MVC on the server-side.
# Instead it is the presenter's role to make the XHR and send/retrieve models.
# This would be the only time that the presenter would use jQuery.
#
class SearchConceptsDialog

  SEARCH_DELAY: 500

  IGNORE_KEYCODES: [9,12,13,16,17,18,19,20,32,33,34,35,36,37,38,39,40,45,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145,224]

  RETURN: 13

  dialogOptions: {
    autoOpen: false,
    title: "Add concepts",
    width: 800,
  }

  constructor: (templateStr) ->
    @template = $(templateStr)
    @dialog = $('<div/>').dialog(@dialogOptions).html(@template)

    # Add some view logic

    # wire up the search box
    $('.searchBox').keyup (e) =>
      if $.inArray(e.which, @IGNORE_KEYCODES) == -1
        setTimeout( =>
          @triggerSearch()
        , @SEARCH_DELAY) 

      # trigger search immediately
      else if (e.which == @RETURN)
        @triggerSearch()

    # wire up the cancel button
    $('.cancel', @template).click =>
      @close()

    # wire up the add button
    $('.add', @template).click =>
      # As part of the interface between the presenter and the view
      # the presenter must know how to accept certain high level events 
      # such as when the user is finished with the dialog and a list
      # of concepts have been chosen
      @delegate.setSelectedConcepts(@getSelectedConcepts())
      @close()

  # load the array of concepts into the results table
  setConcepts: (data) ->
    $('.loading', @template).hide()
    if typeof(data) != "undefined" && data.length > 0
      $('.instructions, .loading, .noDataMsg', @template).hide()
      $('.conceptList', @template).render(data).show()
      @addRowHandlers()
    else
      $('.conceptList, .loading, .instructions', @template).hide()
      $('.noDataMsg', @template).show()

  # refers to the dialog api
  open: ->
    @dialog.dialog("open")

  # refers to the dialog api
  close: ->
    @dialog.dialog("close")

  # wire up the rows so that clicking them checks the checkbox
  addRowHandlers: ->
    $('.conceptList tr', @template).click ->
      cb = $('input', this)
      cb.prop("checked", !cb.prop("checked"))

  # get the those concepts which have been selected
  getSelectedConcepts: ->
    list = []
    $('.conceptList input').each ->
      if $(this).parents('tr').find('input').prop("checked")
        list[list.length] = $(this).parents('tr').find('.name').text()
    return list

  #
  # The delegate is typically a presenter for which the view delegates
  # any non-ui tasks that need to be done, like communicating with
  # the server, running any business rules, etc
  #
  # The alternative would be for the view to pass all event handling
  # to the presenter whereby the view becomes a "dumb" view
  setDelegate: (delegate) ->
    @delegate = delegate

  # The alternative here would be to get the presenter to attach its
  # own handler to the search box
  triggerSearch: ->
    $('.instructions', @template).hide()
    $('.loading', @template).show()

    # why doesn't this work?
    #query = $('.searchBox', @template).val()

    query = $('.searchBox').val()

    @delegate.findConcepts query
#})
