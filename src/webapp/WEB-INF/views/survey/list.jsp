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
<link rel="stylesheet" href="${basePath}/resources/css/jquery-ui.css">
<link rel="shortcut icon" href="${basePath}/resources/img/favicon.ico">
<script src="${basePath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${basePath}/resources/vendor/jquery/jquery-ui.js"></script>
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
	          <h5>List Surveys</h5>
	        </div> 
	    </div>
		<section>
			<div class="container-fluid" style="padding:0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<form:form method="POST" modelAttribute="srvyRec" id="srvyRecForm">
							<div class="form-group row"> 
								<div class="col-sm-12">
									<button type="button" id="addButton" class="btn btn-primary btn-sm" style="margin-right: 10px;"><i class="fas fa-plus-square"></i> Add</button>
									<button type="button" disabled="true" id="editButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-edit"></i> Edit</button>
									<button type="button" disabled="true" id="cloneButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-clone"></i> Clone</button>
									<button type="button" disabled="true" id="dataExportButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-file-export"></i> Data Export</button>
									<button type="button" id="searchButton" class="btn btn-primary btn-sm float-right"><i class="fas fa-search"></i> Search</button>
								</div>
							</div>
							<div class="form-group row">
							    <form:label path="startDateFrom" class="col-sm-2 col-form-label col-form-label-sm">Start Date From</form:label>
								<div class="col-sm-4">
									<form:input path="startDateFrom" class="form-control form-control-sm" placeholder="dd-mm-yyyy" />
								</div>
							    <form:label path="startDateTo" class="col-sm-2 col-form-label col-form-label-sm">Start Date To</form:label>
								<div class="col-sm-4">
									<form:input path="startDateTo" class="form-control form-control-sm" placeholder="dd-mm-yyyy" />
								</div>
					 		</div>
					 		<div class="form-group row">
								<form:label path="coor.userId" class="col-sm-2 col-form-label col-form-label-sm">Coordinator</form:label>
								<div class="col-sm-4">
									<form:input path="coor.userId" class="form-control form-control-sm" />
								</div>
								<form:label path="srvyYear" class="col-sm-2 col-form-label col-form-label-sm">Year</form:label>
								<div class="col-sm-4">
									<form:input path="srvyYear" class="form-control form-control-sm" placeholder="yyyy" />
								</div>
							</div>
							<div class="form-group row">
								<form:label path="srvyTtl" class="col-sm-2 col-form-label col-form-label-sm">Survey Title</form:label>
								<div class="col-sm-4">
									<form:input path="srvyTtl" class="form-control form-control-sm"/>
								</div>
								<form:label path="srvySts.srvyStsId" class="col-sm-2 col-form-label col-form-label-sm">Status</form:label>
								<div class="col-sm-4">
									<form:select path="srvySts.srvyStsId" class="form-control form-control-sm">
										<form:option value=""></form:option>
										<form:options items="${srvyStsHashMap}" />
									</form:select>
								</div>
							</div>
							<div class="form-group row">
							 	<div class="col-sm-12">
									<table id="resultTable"  class="table-hover table table-sm table-striped table-bordered"
										style="width: 100%">
										<thead>
											<tr>
												<th>Survey ID</th>
												<th>Create Date</th>
												<th>Coordinator</th>
												<th>Year</th>
												<th>Survey Title</th>
												<th>Status</th>
												<th>Start Date</th>
												<th>End Date</th>
												<th>No. of Participants</th>
												<th>No. of Respondents</th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th>Survey ID</th>
												<th>Create Date</th>
												<th>Coordinator</th>
												<th>Year</th>
												<th>Survey Title</th>
												<th>Status</th>
												<th>Start Date</th>
												<th>End Date</th>
												<th>No. of Participants</th>
												<th>No. of Respondents</th>
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
			$("#startDateFrom").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			$("#startDateTo").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			
			table = $('#resultTable').DataTable({
				"ajax": {
				    "url": "${basePath}/survey/listSurvey/",
				    "type": "POST",
				    "data": function(d) {
				    	d.filter = $('#srvyRecForm').serializeArray();
				    }
				},
				"columnDefs": [{
					"targets": 8,
					"orderable": false
				},{
					"targets": 9,
					"orderable": false
				}],
				"pageLength": 20,
				"order": [[ 0, "desc" ]],
				"serverSide" : true,
				"processing": true,
				"destroy" : true,
				"pagingType": "full_numbers",
			    "bPaginate": true,
			    "bLengthChange": false,
			    "bFilter": false,
			    "bInfo": true,
			    "bAutoWidth": true });
			
			table.on( 'draw', function () {
				setTimeout(function(){$("#searchButton").prop("disabled", false);}, 500)
				$("#dataExportButton").prop("disabled", true);
	            $("#cloneButton").prop("disabled", true);
	            $("#editButton").prop("disabled", true);
			});
			
			$('#resultTable tbody').on( 'click', 'tr', function () {
				if ($(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            $("#dataExportButton").prop("disabled", true);
		            $("#cloneButton").prop("disabled", true);
		            $("#editButton").prop("disabled", true);
		        } else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            $("#dataExportButton").prop("disabled", false);
		            $("#cloneButton").prop("disabled", false);
		            $("#editButton").prop("disabled", false);
		        }
		    });
			
			$('#resultTable tbody').on( 'dblclick', 'tr', function () {
				var d = table.row(this).data();
				$.redirect('${basePath}/survey/modify/', {'id': d[0]});
			});
			
			$('#editButton').on( 'click', function () {
				$("#editButton").prop("disabled", true);
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				$.redirect('${basePath}/survey/modify/', {'id': d[0]});
		    });

			$('#searchButton').on( 'click', function () {
				$("#searchButton").prop("disabled", true);
			    table.draw();
			});
			
			$('#addButton').on( 'click', function () {
				$("#addButton").prop("disabled", true);
				window.location.href = "${basePath}/survey";
			});
			
			function cloneSurvey(isWithPpt){
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				$.ajax({
				  type: "POST",
				  url: "${basePath}/survey/cloneSurvey",
				  data: {isWithPpt : isWithPpt, srvyRecId: d[0]},
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						if(data.success=="true"){
							window.location.href = "${basePath}/survey/list/";
						}
					});
				  	$("#cloneButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#cloneButton").prop("disabled", false);
				  }, timeout: 10000    
				});
			}
			
			$('#cloneButton').on( 'click', function () {
				$("#cloneButton").prop("disabled", true);
				yesNoModal("System Message","Clone together with the participant list?",
				function () {
					cloneSurvey(true);
				},function () {
					cloneSurvey(false);
				},function () {/* when cancel button clicker*/
					$("#cloneButton").prop("disabled", false);
				});
		    });
			
			$('#dataExportButton').on( 'click', function () {
				$("#dataExportButton").prop("disabled", true);
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				if (d[9] == '0') {
				    modal("System message","No Form Data can be exported !");
				}else {
					$.redirect('${basePath}/survey/dataExport/', {'id': d[0]});
				}
				setTimeout(function(){$("#dataExportButton").prop("disabled", false);}, 500)
		    });
		});
		
		
	</script>
</body>
</html>