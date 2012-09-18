
#
# A mocked presenter used to test SearchConceptsDialog
#
class SearchConceptsPresenterMock

  SIMULATED_DELAY: 2000

  # It may be better for presenters not to construct the views themselves, rather
  # receive an instance.
  constructor: (@view, @data) ->
    # Once the presenter has received its view it should let the view know who
    # to delegate the high level tasks to
    @view.setDelegate this

  # Simulate querying the server
  # A 2 second delay is used to test any feedback widgets letting the user know
  # that the client is waiting for the server
  findConcepts: (query) =>
    results = []

    if query != ""
      for currRow in @data
        if (currRow.name.toLowerCase().indexOf(query.toLowerCase()) != -1 || currRow.description.toLowerCase().indexOf(query.toLowerCase()) != -1)
          results[results.length] = currRow
    else
      results = @data

    setTimeout(=>
      @view.setConcepts(results)
    , @SIMULATED_DELAY)
    

  # Receive a high level event: The user has chosen concepts and closed the dialog
  setSelectedConcepts: (selectedConcepts) ->
    if selectedConcepts.length > 0
      alert "The selected concepts are: " + selectedConcepts.join ","
    else
      alert "There are no selected concepts"
