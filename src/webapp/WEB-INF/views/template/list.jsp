<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>e-Survey System</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="all,follow">
<link rel="stylesheet" href="${basePath}/resources/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${basePath}/resources/vendor/fontawesome/css/all.css">
<link rel="stylesheet" href="${basePath}/resources/css/grasp_mobile_progress_circle-1.0.0.min.css">
<link rel="stylesheet" href="${basePath}/resources/vendor/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="${basePath}/resources/css/style.default.css">
<link rel="stylesheet" href="${basePath}/resources/css/custom.css">
<link rel="stylesheet" href="${basePath}/resources/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet" href="${basePath}/resources/css/responsive.bootstrap4.min.css">
<link rel="stylesheet" href="${basePath}/resources/css/select.dataTables.min.css">
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/survey.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/surveyeditor.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/select2.min.css" />
<link rel="shortcut icon" href="${basePath}/resources/img/favicon.ico">
<script src="${basePath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${basePath}/resources/vendor/jquery/jquery.redirect.js"></script>
<script src="${basePath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${basePath}/resources/js/dataTables.bootstrap4.min.js"></script>
<script src="${basePath}/resources/js/dataTables.responsive.min.js"></script>
<script src="${basePath}/resources/js/responsive.bootstrap4.min.js"></script>
<script src="${basePath}/resources/vendor/popper.js/umd/popper.min.js"></script>
<script src="${basePath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/js/grasp_mobile_progress_circle-1.0.0.min.js"></script>
<script src="${basePath}/resources/vendor/jquery.cookie/jquery.cookie.js"></script>
<script src="${basePath}/resources/vendor/jquery-validation/jquery.validate.min.js"></script>
<script src="${basePath}/resources/vendor/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>	
<script src="${basePath}/resources/js/front.js"></script>
<script src="${basePath}/resources/vendor/knockout/knockout-debug.js"></script>
<script src="${basePath}/resources/vendor/knockout/survey.ko.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/surveyeditor.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/select2.min.js"></script>
</head> 
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp" %>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp" %>
		<!-- Header Section-->
		<div class="breadcrumb-holder">  
	        <div class="container-fluid" style="padding:5px 15px 5px 15px;">
	          <h5>List Templates</h5>
	        </div> 
	    </div>
		<section>
			<div class="container-fluid" style="padding:0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<form:form method="POST" modelAttribute="SrvyTmpl" id="srvyTmplForm">	
							<div class="form-group row">
							    <div class="col-sm-12">
							    	<button type="button" id="addButton" class="btn btn-primary btn-sm" style="margin-right: 10px;"><i class="fas fa-plus-square"></i> Add</button>
							    	<button type="button" id="editButton" disabled="true" class="btn btn-primary btn-sm " style="margin-right: 10px;"><i class="fas fa-edit"></i> Edit</button>
							    	<button type="button" id="cloneButton" disabled="true" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-clone"></i> Clone</button>
							   	 	<button type="button" id="searchButton" class="btn btn-primary btn-sm float-right"><i class="fas fa-search"></i> Search</button>
								</div>
						 	</div>			
							<div class="form-group row">
							    <form:label path="srvyTmplId" class="col-sm-2 col-form-label col-form-label-sm">Template ID</form:label>
								<div class="col-sm-4">
									<form:input path="srvyTmplId" class="form-control form-control-sm" />
								</div>
							    <form:label path="srvyTmplName" class="col-sm-2 col-form-label col-form-label-sm">Template Name</form:label>
								<div class="col-sm-4">
									<form:input path="srvyTmplName" class="form-control form-control-sm" />
								</div>
						 	</div>
						 	<div class="form-group row">
							    <form:label path="srvyGrp.srvyGrpId" class="col-sm-2 col-form-label col-form-label-sm">Survey Group</form:label>
								<div class="col-sm-4">
									<form:select path="srvyGrp.srvyGrpId" class="form-control form-control-sm">
										<form:option value=""></form:option>
										<form:options items="${srvyGrpHashMap}" />
									</form:select>
								</div>
								<label path="srvyTmplFrzInd.srvyTmplFrzIndId" class="col-sm-2 col-form-label col-form-label-sm">Status</label>
								<div class="col-sm-4">
									<form:select path="srvyTmplFrzInd.srvyTmplFrzIndId" class="form-control form-control-sm">
										<form:options items="${srvyTmplFrzIndHashMap}" />
									</form:select>
								</div>
						 	</div>
						 	<div class="form-group row">
							 	<div class="col-sm-12">
									<table id="resultTable"  class="table-hover table table-sm table-striped table-bordered"
										style="width: 100%">
										<thead>
											<tr>
												<th>Template ID</th>
												<th>Template Name</th>
												<th>Survey Group</th>
												<th>Owner</th>
												<th>Create Date</th>
												<th>Status</th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th>Template ID</th>
												<th>Template Name</th>
												<th>Survey Group</th>
												<th>Owner</th>
												<th>Create Date</th>
												<th>Status</th>
											</tr>
										</tfoot>
									</table>
							 	</div>
					 		</div>
					 	</form:form>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="/WEB-INF/views/shared/footer.jsp"%>
	</div>
	<script>
		var table = null; 
		$( document ).ready(function() {
			$('#addButton').on( 'click', function () {
				$("#addButton").prop("disabled", true);
				window.location.href = "${basePath}/template";
			});
			
			table = $('#resultTable').DataTable({
				"ajax": {
				    "url": "${basePath}/template/listTemplate/",
				    "type": "POST",
				    "data": function(d) {
				    	d.filter = $('#srvyTmplForm').serializeArray();
				    }
				},
				"order": [[ 0, "desc" ]],
				"serverSide" : true,
				"processing": true,
				"destroy" : true,
				"pageLength": 20,
				"pagingType": "full_numbers",
			    "bPaginate": true,
			    "bLengthChange": false,
			    "bFilter": false,
			    "bInfo": true,
			    "bAutoWidth": true });
			table.on( 'draw', function () {
				setTimeout(function(){$("#searchButton").prop("disabled", false);}, 500);
				$("#editButton").prop("disabled", true);
	            $("#cloneButton").prop("disabled", true);
			});
			
			$('#editButton').on( 'click', function () {
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				$.redirect('${basePath}/template/modify/', {'id': d[0]});
		    });
			
			$('#cloneButton').on( 'click', function () {
				confirmModal("System message","Do you want to clone the template?",function(){
					var idx = table.cell('.selected', 0).index();
					var d = table.row( idx.row ).data();
					$.ajax({
					  type: "POST",
					  url: "${basePath}/template/cloneTemplate",
					  data: {srvyTmplId:d[0]},
					  success: function(data){
							data=jQuery.parseJSON(data);
							modal("System message",data.message,function(){
								window.location = "${basePath}/template/list";
							});
							$("#cloneButton").prop("disabled", false);
					  },error: function(data){
							modal("System message","System is busy, please try again.");
					  		$("#cloneButton").prop("disabled", false);
					  }, timeout: 10000
					});
				},function(){
					$("#cloneButton").prop("disabled", false);
				});
				
		    });
			
			$('#resultTable tbody').on( 'dblclick', 'tr', function () {
				var d = table.row(this).data();
				$.redirect('${basePath}/template/modify/', {'id': d[0]});
			});

			$('#resultTable tbody').on('click', 'tr', function () {
				if ($(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            $("#editButton").prop("disabled", true);
		            $("#cloneButton").prop("disabled", true);
		        } else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            $("#editButton").prop("disabled", false);
		            $("#cloneButton").prop("disabled", false);
		        }
		    });
			
			$('#searchButton').on( 'click', function () {
				if($("#srvyTmplId").val()!="" && !$.isNumeric($("#srvyTmplId").val())){
					modal("System message","Template ID must be a number.");
					return
				}
				$("#searchButton").prop("disabled", true);
			    table.draw();
			});
		});
		
		
	</script>
</body>
</html>