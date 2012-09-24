
templateStr = ""
$ = {}
SearchConceptsDialog = {}

define ['text!view/CreateConceptProposalView.html', 'view/SearchConceptsDialog'], (templateStrParameter, dialog) ->
  templateStr = templateStrParameter
  SearchConceptsDialog = dialog
  return CreateConceptProposalView


#
# A pure js view for the create proposal form
#
class CreateConceptProposalView

  constructor: (jQuery) ->
    $ = jQuery
    @dialog = new SearchConceptsDialog(jQuery)
    @template = $(templateStr)

    $('.addConcepts', @template).click =>
      @dialog.open()

  setDelegate: (delegate) =>
    @dialog.setDelegate(delegate)
    @delegate = delegate

  render: =>
    return @template
