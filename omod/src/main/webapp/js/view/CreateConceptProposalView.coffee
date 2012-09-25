
templateStr = ""
$ = {}
SearchConceptsDialog = {}

define ['jquery', 'text!./CreateConceptProposalView.html', 'view/SearchConceptsDialog'], (jquery, templateStrParameter, dialog) ->
  $ = jquery
  templateStr = templateStrParameter
  SearchConceptsDialog = dialog
  return CreateConceptProposalView


#
# A pure js view for the create proposal form
#
class CreateConceptProposalView

  constructor: ->
    @dialog = new SearchConceptsDialog()
    @dialog.setDelegate(this)
    @template = $(templateStr)

    $('.addConcepts', @template).click =>
      @dialog.open()

  setDelegate: (delegate) =>
    @delegate = delegate

  setConcepts: (concepts) =>
    @dialog.setConcepts(concepts)

  render: =>
    return @template

  # delegate back to presenter
  findConcepts: (query) =>
    @delegate.findConcepts(query)

  setSelectedConcepts: (concepts) =>
    if concepts.length > 0
      $('.conceptList', @template).show().render(concepts)
      $('.noConceptsMsg', @template).hide()
      @addConceptRowHandlers()
    else
      $('.conceptList', @template).hide()
      $('.noConceptsMsg', @template).show()

  addConceptRowHandlers: =>
    template = @template
    $('.conceptList .remove', @template).click ->
      if confirm "Are you sure?"
        $(this).parents('tr').eq(0).remove()
        if ($('.conceptList tr', template).length == 0)
          $('.conceptList', template).hide()
          $('.noConceptsMsg', template).show()
          
