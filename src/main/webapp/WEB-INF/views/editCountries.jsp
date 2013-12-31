<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>form</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript"
		src="<c:url value="/resources/jquery/1.10.2/jquery.js" />"></script>
</head>
<body>
	<div id="formsContent">
		<form:form id="form" method="post" modelAttribute="formBean"
			cssClass="cleanform" action="${pageContext.request.contextPath}/editCountries">

             <div id="select">
				<fieldset>
					<legend>Select the Country to edit:</legend>
					<form:label path="country">
						<form:select path="country">
							<form:option value="" label="--Please Select" />
							<form:options items="${formBean.countries}" itemValue="code"
								itemLabel="name" />
						</form:select>
					</form:label>


				</fieldset>
            </div>
            <div id="editCountry">
            <fieldset>
            <legend>Edit</legend>
            <form:label path="currentCountry.name">
		  			Name <form:errors path="currentCountry.name" cssClass="error" />
					</form:label>
					<form:input path="currentCountry.name" />
			<form:label path="currentCountry.code">
		  			Code <form:errors path="currentCountry.code" cssClass="error" />
					</form:label>
					<form:input path="currentCountry.code" />
            </fieldset>
            </div>


		</form:form>
		      <input type="text" id="name">
		            <input type="text" id="code">
	</div>
	<script type="text/javascript">

$(document).ready(function() {

	$('#editCountry').hide();

	$('#country').change(function() {
		var selected = $('#country').val();
		var name =  $('#country').find('option:selected').text();



		if (selected != ""){
			$('#editCountry').show();
			$('#currentCountry_code').val(selected);
			$('#currentCountry_name').val(name);
		}
	});
});

</script>
</body>

</html>