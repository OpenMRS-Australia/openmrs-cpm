<%@ taglib prefix="h"
	uri="/WEB-INF/view/module/metadatasharing/taglib/openmrsObjectHandler.tld"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Share Metadata" otherwise="/login.htm"
	redirect="/module/metadatasharing/managePackages.form" />
<c:choose>
	<c:when test="${empty type}">
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			function showItems(type) {
				var selectItems = $j('#selectItems');
				selectItems.load('selectItems.form?type='
						+ encodeURIComponent(type));
			}
			$j(document).ready(function() {
				var chooseTypeTable = $j("#chooseTypeTable").dataTable({
					"bJQueryUI" : true,
					"bSort" : false,
					"bPaginate" : false,
					"sDom" : "<t>"
				});
			});
		</script>
		<table id="chooseTypeTable">
			<thead>
				<tr>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="type" items="${types}">
					<c:if test="${empty packageItems.items[type] }">
						<tr>
							<td>${type}</td>
							<td style="width: 70%"><a
								href="addAllItems.form?includeRetired=true&type=${type}"><spring:message
										code="metadatasharing.addAll" /> </a>&nbsp;<a
								href="addAllItems.form?includeRetired=false&type=${type}"><spring:message
										code="metadatasharing.addNonRetired" /> </a>&nbsp;<a
								href="javascript:showItems('${type}')"><spring:message
										code="metadatasharing.chooseIndividually" /> </a></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
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
													"sDom" : '<"fg-toolbar ui-corner-tl ui-corner-tr ui-helper-clearfix"lfr>t<"fg-toolbar ui-corner-bl ui-corner-br ui-helper-clearfix"ip>__$tag________________________________________________________',
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
																		'<td><input type="che___________________________________________$tag_________________________________________________________$ta_$tag__________________________________________________________$tag_________________________________________________________$ta_$tag_____________________________________________________________________$tag__________________________________________________________$tag_________________________________________________________$ta_$tag_____________________________________________________________________$tag');

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
		</script>
		<p>
			<spring:message code="metadatasharing.type" />
			: ${type}
		</p>
		<p>
			<spring:message code="metadatasharing.chooseMetadata" />
		</p>
		<div>
			<table id="selectItemsTable" style="width: 90%">
				<thead>
					<tr>
						<th style="width: 5%"><select id="includeMetadataSelect">
								<option>
									<spring:message code="metadatasharing.include" />
								</option>
								<option>none</option>
								<option>all</option>
								<option>non-retired</option>
						</select></th>
						<th style="width: 30%"><spring:message
								code="metadatasharing.name" /></th>
						<th style="width: 30%"><spring:message
								code="metadatasharing.description" /></th>
						<th></th>
						<th></th>
						<th style="width: 10%"><spring:message
								code="metadatasharing.id" /></th>
						<th style="width: 30%"><spring:message
								code="metadatasharing.uuid" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</c:otherwise>
</c:choose>