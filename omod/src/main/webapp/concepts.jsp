<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Share Metadata" otherwise="/login.htm"
	redirect="/module/metadatasharing/managePackages.form" />

		<script type="text/javascript">
			var $j = jQuery.noConflict();
			var addedUuids = [];
			var removedUuids = [];
			var metadataForm;
			var metadataTable;

			function submitChanges() {
				var sData = "";
				$j.each(addedUuids, function(index, value) {
					sData += "addUuids=" + value;
					if (index < addedUuids.length - 1) {
						sData += "&";
					}
				});
				$j.each(removedUuids, function(index, value) {
					sData += "removeUuids=" + value;
					if (index < removedUuids.length - 1) {
						sData += "&";
					}
				});
				$j.post("selectItems.form?type=${type}", sData, function() {
					window.location = "edit.form";
				});
			}

			$j(document)
					.ready(
							function() {
								metadataTable = $j("#selectItemsTable")
										.dataTable(
												{
													"bProcessing" : true,
													"bServerSide" : true,
													"sAjaxSource" : "json/items.form?type=${type}",
													"aoColumns" : [
															{
																"sName" : "selected"
															},
															{
																"sName" : "name"
															},
															{
																"sName" : "description"
															},
															{
																"sName" : "dateChanged",
																"bVisible" : false
															},
															{
																"sName" : "retired",
																"bVisible" : false
															},
															{
																"sName" : "id"
															},
															{
																"sName" : "uuid"
															} ],
													"bJQueryUI" : true,
													"sPaginationType" : "full_numbers",
													"bLengthChange" : false,
													"bAutoWidth" : false,
													"bSort" : false,
													"iDisplayLength" : 25,
													"sDom" : '<"fg-toolbar ui-corner-tl ui-corner-tr ui-helper-clearfix"lfr>t<"fg-toolbar ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
													"fnRowCallback" : function(
															nRow, aData,
															iDisplayIndex) {
														var uuid = $j(
																":last-child",
																nRow).text();
														var checked = "";

														if ($j.inArray(uuid,
																addedUuids) != -1) {
															checked = " checked";
														} else if (($j(
																":first-child",
																nRow).text() == "true")
																&& ($j
																		.inArray(
																				uuid,
																				removedUuids) == -1)) {
															checked = " checked";
														}

														$j(":first-child", nRow)
																.replaceWith(
																		'<td><input type="checkbox" name="uuids" value="' + uuid + '"' + checked + '/></td>');

														$j("input[name=uuids]",
																nRow)
																.change(
																		function() {
																			var uuid = $j(
																					this)
																					.val();
																			var checked = $j(
																					this)
																					.is(
																							":checked");
																			if (checked) {
																				if ($j
																						.inArray(
																								uuid,
																								addedUuids) == -1) {
																					addedUuids[addedUuids.length++] = uuid;
																				}
																				removedUuids = $j
																						.grep(
																								removedUuids,
																								function(
																										value) {
																									return value != uuid;
																								});
																			} else {
																				if ($j
																						.inArray(
																								uuid,
																								removedUuids) == -1) {
																					removedUuids[removedUuids.length++] = uuid;
																				}
																				addedUuids = $j
																						.grep(
																								addedUuids,
																								function(
																										value) {
																									return value != uuid;
																								});
																			}
																		});

														if (aData[4] == "true") {
															$j('td', nRow)
																	.addClass(
																			"retired");
														}

														return nRow;
													}
												});
								$j("#selectItems").dialog("option", "buttons",
										{
											"Save" : function() {
												submitChanges();
											},
											"Cancel" : function() {
												$j(this).dialog("close");
											}
										});

								$j("#includeMetadataSelect")
										.change(
												function() {
													$j("option:selected", this)
															.each(
																	function() {
																		var option = $j(
																				this)
																				.text();
																		if (option == "none") {
																			$j(
																					"tr td:first-child input",
																					metadataTable)
																					.attr(
																							'checked',
																							false);
																		} else if (option == "all") {
																			$j(
																					"tr td:first-child input",
																					metadataTable)
																					.attr(
																							'checked',
																							true);
																		} else if (option == "non-retired") {
																			$j(
																					"tr",
																					metadataTable)
																					.each(
																							function(
																									i,
																									el) {
																								var retired = $j(
																										"td:first-child",
																										el)
																										.hasClass(
																												"retired");
																								$j(
																										"td:first-child input",
																										el)
																										.attr(
																												'checked',
																												!retired);
																							});
																		}
																		$j(
																				"tr td:first-child input",
																				metadataTable)
																				.change();
																	});
													$j(this).val("Include");
												});
							});
		</script>
		<div>
			<table id="selectItemsTable" style="width: 90%">
				<thead>
					<tr>
						<th style="width: 5%"><select id="includeMetadataSelect">
								<option>
									Include
								</option>
								<option>none</option>
								<option>all</option>
								<option>non-retired</option>
						</select></th>
						<th style="width: 30%">Name</th>
						<th style="width: 30%">Description</th>
						<th></th>
						<th></th>
						<th style="width: 10%">ID</th>
						<th style="width: 30%">UUID</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
