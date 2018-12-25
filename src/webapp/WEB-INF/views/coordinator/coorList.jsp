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
<style>
.custom-select-sm{
	font-size:100%;
}

.dropdown-toggle-split{
	border-color:blue;
	color:blue;
}
/* Important part */
.modal-dialog{
    overflow-y: initial !important;
    width: 1000px;
}
.modal-body{
    overflow-y: auto;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<form:form method="POST" modelAttribute="coordinator" id="editSrvyGrp">
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
        <div class="breadcrumb-holder">  
	        <div class="container-fluid" style="padding:5px 15px 5px 15px;">
	          <h5>Coordinator Maintenance</h5>
	        </div> 
    	</div>


			<section>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<div class="form-group row">
							<div class="col-sm-12">
								<button type="button" id="list_addButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-plus-square"></i>&nbsp;Add&nbsp;</button>
								<button type="button" disabled="true" id="list_editButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-edit"></i>&nbsp;Edit&nbsp;</button>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-12" style="border-top: 1px #EEE solid;">
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-12">
								<table id="coordTable"  class="table-hover table table-sm table-striped table-bordered"
									style="width: 100%">
									<thead>
										<tr>
											<th>ID</th>
											<th>Name</th>
											<th>Survey Group ID</th>
											<th>Survey Group</th>
											<th>Division</th>
											<th>Designation</th>
											<th>Rank</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
										<tr>
											<th>ID</th>
											<th>Name</th>
											<th>Survey Group ID</th>
											<th>Survey Group</th>
											<th>Division</th>
											<th>Designation</th>
											<th>Rank</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="/WEB-INF/views/shared/footer.jsp"%>
	</div>
	<!-- Add Modal -->
	<div class="modal fade" id="addModal" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Coordinator</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2">Name</label>
							<div class="col-sm-4">
								<div class="input-group">
									<input id="add_details_id" type="hidden">
									<input id="add_details_name" type="text"
										style="padding-top: 4px; padding-bottom: 4px;"
										class="form-control"
										aria-label="Text input with segmented dropdown button"
										readonly>
									<div class="input-group-append">
										<button type="button" id="add_searchButton" class="btn btn-sm dropdown-toggle dropdown-toggle-split"
										aria-expanded="false" aria-haspopup="true" >
										<!-- data-toggle="dropdown" -->
										
											<span class="sr-only">Toggle Dropdown</span>
										</button>
									</div>
								</div>
							</div>
							<label class="col-sm-2">Division</label>
							<div class="col-sm-4">
								<input id="add_details_division" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2">Designation</label>
							<div class="col-sm-4">
								<input id="add_details_designation" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
							<label class="col-sm-2">Rank</label>
							<div class="col-sm-4">
								<input id="add_details_rank" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2">Survey Group</label>
							<div class="col-sm-4">
								<form:select multiple="true" size="5" path="addGrpList" class="form-control form-control-sm">
									<form:options items="${srvyGrpHashMap}" />
								</form:select>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-12">
								<button id="add_addButton" type="button" class="btn btn-primary btn-sm">&nbsp;Add&nbsp;</button>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Edit Modal -->
	<div class="modal fade" id="editModal" role="dialog" 
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Coordinator Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2">Name</label>
							<div class="col-sm-4">
								<div class="input-group">
									<input id="update_details_id" type="hidden">
									<input id="update_details_name" type="text"
										style="padding-top: 4px; padding-bottom: 4px;"
										class="form-control"
										aria-label="Text input with segmented dropdown button"
										readonly>
									<div class="input-group-append">
											<span class="sr-only">Toggle Dropdown</span>
									</div>
								</div>
							</div>
							<label class="col-sm-2">Division</label>
							<div class="col-sm-4">
								<input id="update_details_division" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2">Designation</label>
							<div class="col-sm-4">
								<input id="update_details_designation" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
							<label class="col-sm-2">Rank</label>
							<div class="col-sm-4">
								<input id="update_details_rank" type="text"
									class="form-control form-control-sm form-control-success "
									readonly>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2">Survey Group</label>
							<div class="col-sm-4">
								<form:select multiple="true" size="5" path="modifyGrpList" class="form-control form-control-sm">
									<form:options items="${srvyGrpHashMap}" />
								</form:select>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-12">
								<button id="edit_updateButton" type="button" class="btn btn-primary btn-sm" style="margin-right: 10px;"> Update </button>
								<button id="edit_deleteButton" type="button" class="btn btn-danger btn-sm" style="margin-right: 10px;"> Delete </button>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Search Modal -->
	<div class="modal fade" id="searchModal" role="dialog" style="top:50px" 
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Search User</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
						<div class="form-group row">
							<label class="col-sm-2">Division</label>
							<div class="col-sm-4">
								<form:select path="divCode" class="form-control form-control-sm">
									<form:options items="${pptDivCodeHashMap}" />
								</form:select>
							</div>
							<label class="col-sm-2">Rank</label>
							<div class="col-sm-4">
								<input id="search_rank" type="text"
									class="form-control form-control-sm form-control-success ">
							</div>
						</div>
						<div class="form-group row">		
							<label class="col-sm-2">Designation</label>
							<div class="col-sm-4">
								<input id="search_designation" type="text"
									class="form-control form-control-sm form-control-success ">
							</div>
							<label class="col-sm-2">Name</label>
							<div class="col-sm-4">
								<input id="search_name" type="text"
									class="form-control form-control-sm form-control-success ">
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-12">
								<button id="search_searchButton" type="button" class="btn btn-primary btn-sm">&nbsp;Search&nbsp;</button>
								&nbsp;
								<button id="search_pickButton" type="button" disabled="true" class="btn btn-primary btn-sm">&nbsp;Pick&nbsp;</button>
							</div>
							<div class="col-sm-12">
								<table id="result-table" class="table-hover table table-sm table-striped table-bordered">
									<thead>
										<tr>
											<th class="dt-body-center">ID</th>
											<th class="dt-body-center">Division</th>
											<th class="dt-body-center">Rank</th>
											<th class="dt-body-center">Designation</th>
											<th>Name</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
	<script>			
		$(document).ready(function() {
			// main coordinator
			var otable;
			var userData;
			var ctable = $('#coordTable').DataTable({
				"ajax" : { 
					url : "${basePath}/coordinator/getCoordinator",
					type : "POST",
					data : {},					
					error : function(data) {
						alert("error");
						waitingDialog.hide();
						setTimeout(function() {
							ajaxLoading = false;
							$('.modal-backdrop').removeClass('modal-backdrop');
						}, 300);
					},				
					dataFilter : function(data) {
						try{
							var json = jQuery.parseJSON(data);
							tableData = json;
							$("#text-total").text(json.recordsFiltered);
							return JSON.stringify(json);
						}catch(ex){
							location.reload();
						}
					},
					
					"orderMulti" : false,
					"destroy" : true,
					"order": [[ 0, "desc" ]],
					"serverSide" : true,
					"processing": true,
					"destroy" : true,
				    "bFilter": false,
				    "bInfo": true,
			    	"bAutoWidth": true
				},
				"pageLength":20,
				"lengthChange":false,
		        "columnDefs" : [{"targets": [ 0, 2 ],"visible": false}]
			});
			ctable.on( 'draw', function () {
				setTimeout(function(){$("#search_pickButton").prop("disabled", true);}, 500);
			});
			$("#coordTable > tbody").on("click", "tr", function () {
				var data = ctable.row($(this)).data();
				if(data!=null){
					$("#update_details_id").val(data[0]);
					$("#update_details_name").val(data[1]);
					var jsonObj = JSON.parse(data[2]);
					$("#modifyGrpList").val(jsonObj);
					$("#update_details_division").val(data[4]);
					$("#update_details_designation").val(data[5]);
					$("#update_details_rank").val(data[6]);
				}
			});
			$('#coordTable tbody').on( 'click', 'tr', function () {
				var data = ctable.row($(this)).data();
				if(data!=null){
					if ($(this).hasClass('selected') ) {
			            $(this).removeClass('selected');
			            $("#list_editButton").prop("disabled", true);
			        } else {
			            ctable.$('tr.selected').removeClass('selected');
			            $(this).addClass('selected');
			            $("#list_editButton").prop("disabled", false);
			        }
				}
		    });
			$("#coordTable > tbody").on("dblclick", "tr", function () { 
				var data = ctable.row($(this)).data();
				if(data!=null){
					$('#editModal').modal({
				        show: true,
				        backdrop: 'static'
				    });
				}
			});
			$('#list_addButton').on('click', function() {
				$("#add_details_id").val("");
				$("#add_details_division").val("");
			 	$("#add_details_rank").val("");
			 	$("#add_details_designation").val("");
			 	$("#add_details_name").val("");
				$('#addGrpList').val(""); 
				$('#addModal').modal(null);
			});
			$('#list_editButton').on('click', function() {
				$('#editModal').modal(null);
			});
			// Edit coordinator
			$('#edit_updateButton').on('click', function() {
				console.log($('#modifyGrpList').val().toString());
				if ($('#modifyGrpList').val().toString()==""){
					modal("System message","Please select an user and assign a survey group.");
		    	}else{
					userDetailsUpdate();
		    	}
			});
			$('#edit_deleteButton').on('click', function() {
				confirmModal("Confirm message","Do you want to delete the Coordinator?",function(){
					userDetailsDelete();
				},function(){
					
				});
			});


			// Add coordinator
			$('#add_searchButton').on('click', function() {
				$('#searchModal').modal('show');
			});
			$('#add_addButton').on('click', function() {
				var selFlag = ($('#addGrpList')[0][0].selected||$('#addGrpList')[0][1].selected||$('#addGrpList')[0][2].selected||$('#addGrpList')[0][3].selected||$('#addGrpList')[0][4].selected)
				console.log(selFlag);
				console.log($('#add_details_id').val());
		    	if (($('#add_details_id').val()) &&	selFlag){
					var found = false;
					for (i=0 ; i< ctable.rows()[0].length; i++){
						if (ctable.row(i).data()[0] == $('#add_details_id').val()){
							found = true;
						}
					}
					if (found){
						modal("System message","The coordinator already existed !");
					}else{
						userDetailsAdd();
					}
		    	}else{
					modal("System message","Please select an user and assign a survey group.");
		    	}
			});
			$('#example tbody').on('click', 'tr', function() {
				$('#addModal').modal('show');
			});

			// Search user
			otable = $('#result-table').DataTable({
				"bPaginate" : true,
				"bLengthChange" : false,
				"bFilter" : false,
				"bInfo" : true,
				"bAutoWidth" : true,
				"retrieve" : true,
				"destroy" : true,
			});
			$('#search_searchButton').on('click', function() {		
				otable = $('#result-table').DataTable({ 
					"ajax" : { 
						url : "${basePath}/coordinator/searchUserCoordinator",
						type : "POST",
						data : { 
							'search_division' : $('#divCode').val(),
							'search_rank' : $('#search_rank').val(),
							'search_designation' : $('#search_designation').val(),
							'search_name' : $('#search_name').val()} 
					},
					error : function(data) {
						alert("error");
						waitingDialog.hide();
						setTimeout(function() {
							ajaxLoading = false;
							$('.modal-backdrop').removeClass('modal-backdrop');
						}, 300);
					},				
					dataFilter : function(data) {
						try{
							var json = jQuery.parseJSON(data);
							tableData = json;
							$("#text-total").text(json.recordsFiltered);
							return JSON.stringify(json);
						}catch(ex){
							location.reload();
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
				    "bAutoWidth": true,
			        "columnDefs" : [{"targets": [ 0],"visible": false}]
				});			
			});
			$('#search_pickButton').on('click', function() {		
				if (userData != null) {
					$("#add_details_id").val(userData[0]);
					$("#add_details_division").val(userData[1]);
				 	$("#add_details_rank").val(userData[2]);
				 	$("#add_details_designation").val(userData[3]);
				 	$("#add_details_name").val(userData[4]);
				 	$('#searchModal').modal('hide');
				}
			});
			$('#example2 tbody').on('click', 'tr', function() {
				$('#searchModal').modal('hide');
			});
			$('#result-table tbody').on( 'click', 'tr', function () {
				userData = otable.row($(this)).data();
				if ($(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            userData = null;
		            $("#search_pickButton").prop("disabled", true);
		        } else {
		        	$("#search_pickButton").prop("disabled", false);
		            otable.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		    });
			$("#result-table > tbody").on("dblclick", "tr", function () {
				userData = otable.row($(this)).data();
				 $("#add_details_id").val(userData[0]);
				 $("#add_details_division").val(userData[1]);
				 $("#add_details_rank").val(userData[2]);
				 $("#add_details_designation").val(userData[3]);
				 $("#add_details_name").val(userData[4]);
				 $('#searchModal').modal('hide');
			});
		});
		
		function userDetailsDelete() {
			$.ajax({
				url: '${basePath}/coordinator/deleteCoordinator',      
				type : "POST",
			    data: { 
			    	"details_id":$('#update_details_id').val()
			    },success: function (data) {
			    	data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						window.location = "coordinator";
					});
			    },error: function(data){
					modal("System message","System is busy, please try again.");
				}, timeout: 10000
			});
		}
		
		function userDetailsAdd() {
			$.ajax({
				url: '${basePath}/coordinator/addCoordinator',      
				type : "POST",
			    data: { 
			    	"details_id":$('#add_details_id').val(),
			    	"details_group":$('#addGrpList').val().toString()
			    },success: function (data) {
			    	data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						window.location = "coordinator";
					});
			    },error: function(data){
					modal("System message","System is busy, please try again.");
				}, timeout: 10000
			});
		}
		
		function userDetailsUpdate() {
			$.ajax({
				url: '${basePath}/coordinator/updateCoordinator',      
				type : "POST",
			    data: { 
			    	"details_id":$('#update_details_id').val(),
			    	"details_group":$('#modifyGrpList').val().toString()
			    },
			    success: function (data) {
			    	data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						window.location = "coordinator";
					});
			    },error: function(data){
					modal("System message","System is busy, please try again.");
				}, timeout: 10000
			});
		}
		</script>
	</form:form>
</body>
</html>