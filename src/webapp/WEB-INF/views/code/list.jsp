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
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
		<div class="breadcrumb-holder">
			<div class="container-fluid" style="padding: 5px 15px 5px 15px;">
				<h5>Code Maintenance</h5>
			</div>
		</div>
		<section>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<div class="form-group row">
							<div class="col-sm-12">
								<button type="button" id="createCodeButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
									<i class="fas fa-plus-square"></i> Add
								</button>
								<button type="button" id="editCodeButton" disabled="true" class="btn btn-primary btn-sm" style="margin-right: 10px;">
									<i class="fas fa-edit"></i> Edit
								</button>
								<button type="button" id="deleteCodeButton" disabled="true" class="btn btn-danger btn-sm " style="margin-right: 10px;">
									<i class="fas fa-trash"></i> Delete
								</button>
							</div>
						</div>
						<table id="resultTable" class="table-hover table table-sm table-striped table-bordered" style="width: 100%">
							<thead>
								<tr>
									<th>Code</th>
									<th>Value</th>
									<th>Group</th>
									<th>Description</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>Code</th>
									<th>Value</th>
									<th>Group</th>
									<th>Description</th>
								</tr>
							</tfoot>
						</table>
						<div id="codeModal" class="modal" tabindex="-1" role="dialog" aria-hidden="false">
							<form:form method="POST" modelAttribute="sysCnfg" id="sysCnfgForm">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">Create Code</h5>
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<div class="form-group row">
												<form:label path="sysCnfgId" class="col-sm-3 col-form-label col-form-label-sm">Code*</form:label>
												<div class="col-sm-9">
													<form:input type="text" path="sysCnfgId" class="form-control form-control-sm typeahead" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="sysCnfgVal" class="col-sm-3 col-form-label col-form-label-sm">Value*</form:label>
												<div class="col-sm-9">
													<form:input type="text" path="sysCnfgVal" class="form-control form-control-sm typeahead" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="sysCnfgGrp" class="col-sm-3 col-form-label col-form-label-sm">Group*</form:label>
												<div class="col-sm-9">
													<form:input type="text" path="sysCnfgGrp" class="form-control form-control-sm typeahead" />
												</div>
											</div>
											<div class="form-group row">
												<form:label path="sysCnfgDesc" class="col-sm-3 col-form-label col-form-label-sm">Description</form:label>
												<div class="col-sm-9">
													<form:input type="text" path="sysCnfgDesc" class="form-control form-control-sm typeahead" />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" id="codeModalAddButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
												<i class="fas fa-plus"></i> Create
											</button>
											<button type="button" id="codeModalEditButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
												<i class="fas fa-edit"></i> Update
											</button>
											<button type="button" id="codeModalCloseButton" class="btn btn-secondary btn-sm" data-dismiss="modal">
												<i class="fas fa-window-close"></i> Close
											</button>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="/WEB-INF/views/shared/footer.jsp"%>
	</div>
	<script>
		var table = null; 
		$(document).ready(function() {
			table = $('#resultTable').DataTable({
				"ajax": {
				    "url": "${basePath}/code/listCode/",
				    "type": "POST"
				},
				"order": [[ 2, "asc" ],[ 0, "asc" ]],
				"serverSide" : true,
				"processing": true,
				"pageLength": 20,
				"destroy" : true,
				"pagingType": "full_numbers",
			    "bPaginate": true,
			    "bLengthChange": false,
			    "bFilter": false,
			    "bInfo": true,
			    "bAutoWidth": true }); 
			
			table.on( 'draw', function () {
	            $("#editCodeButton").prop("disabled", true);
	            $("#deleteCodeButton").prop("disabled", true);
			});
			
			$('#resultTable tbody').on( 'click', 'tr', function () {
				if ($(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            $("#editCodeButton").prop("disabled", true);
		            $("#deleteCodeButton").prop("disabled", true);
		        } else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            $("#editCodeButton").prop("disabled", false);
		            $("#deleteCodeButton").prop("disabled", false);
		        }
		    });
			
			$("#codeModalAddButton").click(function() {
				$.ajax({
				  type: "POST",
				  url: "${basePath}/code/addCode",
				  data: $('#sysCnfgForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						if(data.success=="true"){
							$('#codeModal').modal("hide");
							table.draw();
						}
					});
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  }, timeout: 10000    
				});
			});
			
			$("#codeModalEditButton").click(function() {
				$("#sysCnfgId").prop("disabled", false); 
				$.ajax({
				  type: "POST",
				  url: "${basePath}/code/updateCode",
				  data: $('#sysCnfgForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);
					if(data.success=="true"){
						$('#codeModal').modal("hide");
						table.draw();
					}
					modal("System message",data.message,function(){
						
					});
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  }, timeout: 10000    
				});
				$("#sysCnfgId").prop("disabled", true);
			});
			
			$("#createCodeButton").click(function() {
				$("#codeModal .modal-title").text("Create Code");
				$("#sysCnfgId").val("");
				$("#sysCnfgId").prop("disabled", false);
				$("#sysCnfgDesc").val("");
				$("#sysCnfgVal").val("");
				$("#sysCnfgGrp").val("");
				$("#codeModalAddButton").css("display","block");
				$("#codeModalEditButton").css("display","none");
				$('#codeModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			});
			
			$("#editCodeButton").click(function() {
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				$("#codeModal .modal-title").text("Edit Code");
				$("#sysCnfgId").val(d[0]);
				$("#sysCnfgId").prop("disabled", true);
				$("#sysCnfgDesc").val(d[3]);
				$("#sysCnfgVal").val(d[1]);
				$("#sysCnfgGrp").val(d[2]);
				$("#codeModalAddButton").css("display","none");
				$("#codeModalEditButton").css("display","block");
				$('#codeModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			});
			
			$("#deleteCodeButton").click(function() {
				
				var idx = table.cell('.selected', 0).index();
				var d = table.row( idx.row ).data();
				confirmModal("System message","Do you want to delete the Code?",function(){
					$("#deleteCodeButton").prop("disabled", true);
					$.ajax({
						  type: "POST",
						  url: "${basePath}/code/deleteCode",
						  data: {sysCnfgId: d[0]},
						  success: function(data){
							data=jQuery.parseJSON(data);
							if(data.success=="true"){
								table.draw();
							}
							modal("System message",data.message,function(){
								
							});					
							$("#deleteCodeButton").prop("disabled", false);
						  },error: function(data){
							modal("System message","System is busy, please try again.");
							$("#deleteCodeButton").prop("disabled", false);
						  }, timeout: 10000    
						});
				},function(){
					
				});
			});
		});
	</script>
</body>
</html>