
templateStr = ""
$ = {}
SearchConceptsDialog = {}

define ['jquery', 'text!view/CreateConceptProposalView.html', 'view/SearchConceptsDialog'], (jquery, templateStrParameter, dialog) ->
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
    @template = $(templateStr)

    $('.addConcepts', @template).click =>
      @dialog.open()

  setDelegate: (delegate) =>
    @dialog.setDelegate(delegate)
    @delegate = delegate

  render: =>
    return @template
