
SearchConceptsPresenterMock = {}
data = []

define ['./SearchConceptsPresenterMock', './concept-list.data'], (presenter, theData) ->
  data = theData
  SearchConceptsPresenterMock = presenter
  return CreateConceptProposalPresenterMock

class CreateConceptProposalPresenterMock

  SIMULATED_DELAY: 2000

  constructor: (@view) ->
    @view.setDelegate(this)

  findConcepts: (query) =>
    results = []

    if query != ""
      for currRow in data
        if (currRow.name.toLowerCase().indexOf(query.toLowerCase()) != -1 || currRow.description.toLowerCase().indexOf(query.toLowerCase()) != -1)
          results[results.length] = currRow
    else
      results = data

    setTimeout(=>
      @view.setConcepts(results)
    , @SIMULATED_DELAY)
