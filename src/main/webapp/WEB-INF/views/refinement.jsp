<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Diversification in spatial context</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript"
		src="<c:url value="/resources/jquery/1.10.2/jquery.js" />"></script>
</head>
<body>
	<div id="formsContent">
		<form:form id="form" method="post" modelAttribute="formBean"
			cssClass="cleanform" action="${pageContext.request.contextPath}/form">

			<fieldset>
				<legend>Calculate by:</legend>
				<form:select path="inputType">
					<form:option selected="selected" value="country">Country</form:option>
					<form:option value="map">Map</form:option>
				</form:select>

				<legend>Algorithm Type:</legend>
				<form:select path="algorithm">
					<c:forEach var="item" items="${formBean.algorithmTypes}">
						<form:option value="${item}">${item.name}</form:option>
					</c:forEach>
				</form:select>

				<form:label path="percentage">
		  			Percentage <form:errors path="percentage" cssClass="error" />
				</form:label>
				<form:input path="percentage" />

				<form:label path="population">
		  			City population > <form:errors path="population" cssClass="error" />
				</form:label>
				<form:input path="population" />
			</fieldset>

			<div id="byCountry">
				<fieldset>
					<legend>By Country</legend>
					<form:label path="country">
						<form:select path="country">
							<form:option value="" label="--Please Select" />
							<form:options items="${formBean.countries}" itemValue="code"
								itemLabel="name" />
						</form:select>
					</form:label>


				</fieldset>
			</div>
			<div id="byMap" style="display: none;">
				<fieldset>
					<legend>Map data:</legend>

					<form:label path="nELat">
		  			NE Latitude <form:errors path="nELat" cssClass="error" />
					</form:label>
					<form:input path="nELat" />

					<form:label path="nELng">
		  			NE Longitude <form:errors path="nELng" cssClass="error" />
					</form:label>
					<form:input path="nELng" />

					<form:label path="sWLat">
		  			SW Latitude <form:errors path="sWLat" cssClass="error" />
					</form:label>
					<form:input path="sWLat" />

					<form:label path="sWLng">
		  			SW Longitude <form:errors path="sWLng" cssClass="error" />
					</form:label>
					<form:input path="sWLng" />

					<div id="map" style="width: 600; height: 480px;"></div>
					<p>
						<input id="clearMap" type="button" value="Clear Map">
					</p>
				</fieldset>


			</div>
			<p>
				<input type="submit" value="Calculate">
			</p>
			<div id="msg"></div>
			<fieldset id="outMapFieldset">
				<legend>Output</legend>
			</fieldset>
		</form:form>
	</div>


	<script type="text/javascript">
		window.myData = [];
		
		window.statusError = false;
		
		window.stop = false;

		window.myData.box = null;

		window.myData.mapExists = false;
		
		function pollStatus(){
			   setTimeout(function(){
			      $.ajax({ url: "ajax/status.json", success: function(data){
			    	  if (!window.statusError){
			    		  if (!window.stop) {
			    	 	     $("#msg").html(data.message + " " + data.progress);
                    		  pollStatus();
                     	 	}
                     	 }
			      }, dataType: "json",
					beforeSend : function(
							xhr) {
						xhr
								.setRequestHeader(
										"Accept",
										"application/json");
						xhr
								.setRequestHeader(
										"Content-Type",
										"application/json");
					},});
			  }, 5000);
		     
		}

		function drawOutMap(data, diverData) {
			var drawDataBorders = false;
			var selectedType = $('#inputType').val();
			if (selectedType == "country") {
				var zoom = 3;
			} else {
				var zoom = 6;
				drawDataBorders = true;
			}
			var mapEl = $("#outMap")[0];
			var latitude = data[0][0];
			var longitude = data[0][1];
			var outMapCenter = new google.maps.LatLng(latitude, longitude);
			var outMap = new google.maps.Map(mapEl, {
				center : outMapCenter,
				zoom : zoom
			});
			drawMap(data, outMap);
			drawMap2(diverData, outMap);
			// if is map, draw borders
			if (drawDataBorders && window.myData.box != null) {
				var dataBorders = new google.maps.Rectangle({
					bounds : window.myData.box,
					strokeColor : '#FF33CC',
					strokeOpacity : 0.8,
					strokeWeight : 2,
					fillColor : '#FFE6F9',
					fillOpacity : 0.25,
					clickable : false,
					editable : false,
					draggable : false
				});

				dataBorders.setMap(outMap);
			}
		}

		function reDrawMap() {
			if (!window.myData.mapExists) {
				var mapEl = $("#map")[0];
				var zoom = 8;
				if (typeof window.myData.mapCenter == 'undefined') {
					window.myData.mapCenter = new google.maps.LatLng(51.508742,
							-0.120850);
				}
				map = drawSourceMap(mapEl, window.myData.mapCenter, zoom);
				areaChanged(map.getBounds());
				window.myData.mapExists = true;
			}
		}

		$(document)
				.ready(
						function() {
							$('#byCountry').show();
							$('#byMap').hide();

							$("#msg").html("");

							$('#inputType').change(function() {
								var selected = $('#inputType').val();
								if (selected == "country") {
									$('#byCountry').show();
									$('#byMap').hide();
								} else {
									$('#byCountry').hide();
									$('#byMap').show();
									reDrawMap();
								}
							});

							$("#clearMap").click(function() {
								window.myData.mapExists = false;
								reDrawMap();
							});

							$("#form")
									.submit(
											function(event) {
												$(":submit").attr("disabled", true);
												var $msg = $("#msg");//que es esta variable con $
												$msg.removeClass("error");
												$msg.addClass("warning");
												$msg.html("<p><b>Calculating...</b></p>");
												window.statusError = false;
												window.stop = false;
												// Remove the map
												$("#outMap").remove();
												pollStatus();
												$.ajax({
															type : "post",
															url : "ajax/process.json",
															data : JSON
																	.stringify($('#form').serializeObject()),
															dataType : 'json',
															beforeSend : function(
																	xhr) {
																xhr
																		.setRequestHeader(
																				"Accept",
																				"application/json");
																xhr
																		.setRequestHeader(
																				"Content-Type",
																				"application/json");
															},
															complete : function(
																	xhr, status) {
																var data = xhr.responseText;
																var parsed = $
																		.parseJSON(data);

																var status = parsed.status;
																if (status == "OK") {
																	var allCities = parsed.allCities;
																	var allCitiesData = [];
																	for (var i = 0; i < allCities.length; i++) {
																		var temp = [];
																		temp
																				.push(allCities[i].latitude);
																		temp
																				.push(allCities[i].longitude);
																		temp
																				.push(allCities[i].city);
																		allCitiesData
																				.push(temp);
																	}
																	var diversifiedCities = parsed.diversifiedCities;
																	var diversifiedCitiesData = [];
																	for (var i = 0; i < diversifiedCities.length; i++) {
																		var temp = [];
																		temp
																				.push(diversifiedCities[i].latitude);
																		temp
																				.push(diversifiedCities[i].longitude);
																		temp
																				.push(diversifiedCities[i].city);
																		diversifiedCitiesData
																				.push(temp);
																	}
																	if (typeof overlay != 'undefined') {
																		overlay
																				.setMap(null);
																	}
																	$("#outMapFieldset").append('<div id="outMap" style="width: 600px; height: 480px;"></div>');
																	overlay = drawOutMap(allCitiesData, diversifiedCitiesData);
                                                                    window.stop = true;
																	$msg.removeClass("error info warning");
																	$msg.addClass("info");
																	$msg.html("Ok");
																}
																if (status == "ERROR") {
																	window.statusError = true;
																	window.stop = true;
																	$msg.removeClass("info warning");
																	$msg.addClass("error");
																	var errors = parsed.errors;
																	var errorMessage = "";
																	for (var i = 0; i < errors.length; i++) {
																		 errorMessage = errorMessage + "<p>" + errors[i].message + "</p>";
																	}
																	$msg.html(errorMessage);
																}
																$(":submit").removeAttr("disabled");
															},
															error : function(
																	xhr) {
																window.statusError = true;
																window.stop = true;
																$msg.removeClass("info warning");
																$msg.addClass("error");
																$msg.html(
																				"<p>Error executing AJAX call</p>");
																$(":submit")
																		.removeAttr(
																				"disabled");
															}
														});

												event.preventDefault();
											});
						});
	</script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?libraries=places,drawing&amp;sensor=true&amp;key=AIzaSyCXlPs7edJYVCqhzfll3mozU5vuFc7T1Xk">
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/d3/d3.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/form.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/d3.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/diverVis.js" />"></script>
</body>
</html>
