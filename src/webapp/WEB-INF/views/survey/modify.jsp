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
<link rel="stylesheet" href="${basePath}/resources/css/richtext.min.css">
<link rel="stylesheet" href="${basePath}/resources/vendor/typeahead/typeahead.css">
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
<script src="${basePath}/resources/js/jquery.richtext.min.js"></script>
<script src="${basePath}/resources/vendor/typeahead/typeahead.js"></script>

<style>

.col-form-label-sm{padding-top:0;padding-bottom:0;}
section{min-height:800px;}
tr[role=row] td{padding:1px !important;}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
	    <div class="breadcrumb-holder">  
	        <div class="row" style="padding: 5px 15px 5px 15px;vertical-align:middle;">
				<div class="col-sm-8 " style="padding-top:5px;">
						<h5 >Survey Maintenance</h5>  
					</div>
	          	<div class="col-sm-4">
					<button type="button" id="closeButton" class="btn btn-secondary btn-sm float-right align-middle" style="padding: 1px 5px 1px 5px;"><i class="fas fa-ban"></i> Close</button>
				</div>
	        </div> 
	    </div>
		<section> 
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<form:form method="POST" modelAttribute="srvyRec" id="srvyRecForm">
							<input type="hidden" id="clearUserTable" value="false"/>
							<input type="hidden" id="o_srvySts" value="${srvySts}"/>
							<input type="hidden" id="o_pptDivCode" value="${pptDivCode}"/>
							<form:hidden path="lastRecTxnDate" />
							<%@ include file="/WEB-INF/views/survey/shared/user.jsp"%>
							<%@ include file="/WEB-INF/views/survey/shared/bulkAddPpt.jsp"%>
							<div class="card">
								<div class="card-header hideForParticipant hideForBulkAddPpt">
									<div class="form-group row">
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Survey ID</form:label>
										<div class="col-sm-4">
											${srvyRecId}
										</div>
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Create Date</form:label>
										<div class="col-sm-4">
											${creDate}
										</div>
									</div>
									<div class="form-group row">
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Coordinator</form:label>
										<div class="col-sm-4">
											${userId}
										</div>
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Year</form:label>
										<div class="col-sm-4">
											${srvyYear}
										</div>
									</div>
									<div class="form-group row">
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Template</form:label>
										<div class="col-sm-4">
											${srvyTmplId} - ${srvyTmplName}
										</div>
										<form:label path="" class="col-sm-2 col-form-label col-form-label-sm">Survey Title</form:label>
										<div class="col-sm-4">
											${srvyTtl}
										</div>
									</div>
								</div>
								<div class="card-body">
									<ul class="nav nav-tabs" id="myTab" role="tablist">
										<li class="nav-item hideForParticipant hideForBulkAddPpt"><a class="nav-link active" id="detail-tab" data-toggle="tab" href="#detail" role="tab" aria-controls="home" aria-selected="true">Survey Details</a></li>
										<li class="nav-item hideForParticipant hideForBulkAddPpt"><a class="nav-link " id="email-tab" data-toggle="tab" href="#email" role="tab" aria-controls="email" aria-selected="false">Email Template</a></li>
										<li class="nav-item"><a class="nav-link " id="participant-tab" data-toggle="tab" href="#participant" role="tab" aria-controls="profile" aria-selected="false">Survey Participant</a></li>	
										<li class="nav-item hideForParticipant hideForBulkAddPpt"><a class="nav-link " id="adtnInfo-tab" data-toggle="tab" href="#adtnInfo" role="tab" aria-controls="adtnInfo" aria-selected="false">Additional Information</a></li>
										<li class="nav-item hideForParticipant hideForBulkAddPpt"><a class="nav-link " id="history-tab" data-toggle="tab" href="#history" role="tab" aria-controls="history" aria-selected="false">Email History</a></li>									
									</ul>
									<div class="tab-content" id="myTabContent" style="padding-top: 20px;">
										<div class="tab-pane fade show active" id="detail" role="tabpanel" aria-labelledby="detail-tab">
											<div class="form-group row hideForPartipipant mb-10">
												<div class="col-sm-12">
													<button type="button" id="updateButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
														<i class="fas fa-edit"></i> Update
													</button>
													<c:if test="${srvyRec.srvySts.srvyStsId==2}">
														<button type="button" id="clearButton" class="btn btn-danger btn-sm" style="margin-right: 10px;">
															<i class="fas fa-trash-alt"></i> Clear Trial-Run Data
														</button>
													</c:if>
												</div>
											</div>
											<%@ include file="/WEB-INF/views/survey/shared/detail.jsp"%>
										</div>
										<div class="tab-pane fade show " id="email" role="tabpanel" aria-labelledby="email-tab">	
											<%@ include file="/WEB-INF/views/survey/shared/emailTmpl.jsp"%>
										</div>
										<div class="tab-pane fade show " id="participant" role="tabpanel" aria-labelledby="participant-tab">
											<%@ include file="/WEB-INF/views/survey/shared/participant.jsp"%>
										</div>
										<div class="tab-pane fade show " id="adtnInfo" role="tabpanel" aria-labelledby="adtnInfo-tab">
											<%@ include file="/WEB-INF/views/survey/shared/adtnInfo.jsp"%>
										</div>
										<div class="tab-pane fade show " id="history" role="tabpanel" aria-labelledby="history-tab">
											<%@ include file="/WEB-INF/views/survey/shared/history.jsp"%>
										</div>	
									</div>
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
		function pptCatgOnChange(){
			if($("#o_srvySts").val()=="3"){
				$("#pptDivCode").prop('disabled', true);
				$("#srvyPptCatg\\.srvyPptCatgId").prop('disabled', true);
				$("#srvyTtl").prop('disabled', true);
				$("#srvyTmpl\\.srvyTmplId").prop('disabled', true);
			}else{
				if($("#srvyPptCatg\\.srvyPptCatgId").val()!="1"){
					$("#pptDivCode").val("");
					$("#pptDivCode").prop('disabled', true);
				}else{
					$("#pptDivCode").prop('disabled', false);
				}
			}
		}
		
		function srvyStsControl(){
			if($("#srvySts\\.srvyStsId").val()==2){
				$("#srvySts\\.srvyStsId option:contains('Prepare')").attr("disabled","disabled");
			}else if($("#srvySts\\.srvyStsId").val()==3){
				$("#srvySts\\.srvyStsId option:contains('Prepare')").attr("disabled","disabled");
				$("#srvySts\\.srvyStsId option:contains('Trial-Run')").attr("disabled","disabled");
			}else if($("#srvySts\\.srvyStsId").val()==4){
				$("#srvySts\\.srvyStsId option:contains('Prepare')").attr("disabled","disabled");
				$("#srvySts\\.srvyStsId option:contains('Trial-Run')").attr("disabled","disabled");
			}
		}
		
		function checkPptCatgHashMap(){
			console.log($('#srvyPptCatg\\.srvyPptCatgId').val());
			if($('#srvyPptCatg\\.srvyPptCatgId').val()=='3'){
				$("#addParticipantButton").css("display","none");
				$("#bulkAddPptButton").css("display","block");
			}else{
				
				$("#bulkAddPptButton").css("display","none");
				$("#addParticipantButton").css("display","block");
			};
		}
		
		$(document).on('focusin', 'select', function(){
		    console.log("Saving value " + $(this).val());
		    $(this).data('val', $(this).val());
		}).on('change','select', function(){
		    var prev = $(this).data('val');
		    var current = $(this).val();
		    console.log("Prev value " + prev);
		    console.log("New value " + current);
		});
		$(document).ready(function() {

			$('#closeButton').on( 'click', function () {
				confirmModal('System message','Any unsaved data will be lost.<br/>Do you want to close this page?',function(){					
					parent.history.back();
				});
			});
			
			$("#autoRmdrDate").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			$("#bgnDate").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			$("#endDate").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			$("#srvyEmailHist\\.emailSendDateFrom").datepicker({ dateFormat: 'dd-mm-yy' }).val();
			$("#srvyEmailHist\\.emailSendDateTo").datepicker({ dateFormat: 'dd-mm-yy' }).val();

			checkPptCatgHashMap();
			srvyStsControl();	      	
			pptCatgOnChange();
		
			$("#srvyPptCatg\\.srvyPptCatgId").change(function() {
				/* TODO handle change of srvyPptCatgId */
				tmpData = $(this).data('val');
				if($("#srvyRecId").val()){
					confirmModal('System message','Change of "Participant Category" will remove all Survey Participants.<br/>Do you want to change the "Participant Category"?',function(){
					},function(){
						$("#srvyPptCatg\\.srvyPptCatgId").val(tmpData);
					});
				}
				pptCatgOnChange();
				checkPptCatgHashMap();
			});
			$("#pptDivCode").change(function() {
				tmpData = $(this).data('val');
				/* TODO handle change of pptDivCode */
				if(!$("#o_pptDivCode").val()){
					if($("#pptDivCode").val()!=$("#o_pptDivCode").val()){
						confirmModal('System message','Change of "Division of Participants" will remove all Survey Participants.<br/>Do you want to change the "Division of Participants"?',function(){							
						},function(){
							$("#pptDivCode").val(tmpData);
						});
					}
				}
			});
			
			$("#createButton").click(function() {
				/* checking here */
				if($("#srvyYear").val()==""){
					modal("System message",'The "Year" is mandatory.');
					return;
				}
				var date = new Date( "01/05/"+$("#srvyYear").val());
				var year = date.getFullYear();
				var month = date.getMonth();
				var day = date.getDate();
				if(!(year > 1950)){
					modal("System message",'The "Year" is not proper. Please check.');
					return;
				}
				if($("#srvyTmpl\\.srvyTmplId").val()==""){
					modal("System message",'The "Template Name" is mandatory.');
					return;
				}
				if($("#srvyTtl").val()==""){
					modal("System message",'The "Survey Title" is mandatory.');
					return;
				}
				if($("#srvyTtl").val().length > 256){
					modal("System message",'The "Survey Title" must be less than or equal to 256 characters.');
					return;
				}
				if($("#bgnDate").val()==""){
					modal("System message",'The "Start Date" is mandatory.');
					return;
				}
				if($("#endDate").val()==""){
					modal("System message",'The "End Date" is mandatory.');
					return;
				}
				if ($('#endDate').datepicker("getDate") < $('#bgnDate').datepicker("getDate")) {
					modal("System message",'"Start Date" cannot be greater than "End Date".');
					return;					
				}
				if($("#srvyPptCatg\\.srvyPptCatgId").val()=="1" && $("#pptDivCode").val()=="" ){
					modal("System message",'The "Division of Participants" is mandatory.');
					return;
				}
				if($("#srvyTtl").val().length > 1000){
					modal("System message",'The "Survey Remark" must be less than or equal to 1000 characters.');
					return;
				}
				/* checking here */
				$.ajax({
				  type: "POST",
				  url: "${basePath}/survey/addSurvey",
				  data: $('#srvyRecForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){});
				  	$("#createButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#createButton").prop("disabled", false);
				  }, timeout: 10000
				});
			});
			
			$("#updateButton").click(function() {
				/* checking here */
				if($("#srvyYear").val()==""){
					modal("System message",'The "Year" is mandatory.');
					return;
				}
				var date = new Date( "01/05/"+$("#srvyYear").val());
				var year = date.getFullYear();
				var month = date.getMonth();
				var day = date.getDate();
				if(!(year > 1950)){
					modal("System message",'The "Year" is not proper. Please check.');
					return;
				}
				if($("#srvyTmpl\\.srvyTmplId").val()==""){
					modal("System message",'The "Template Name" is mandatory.');
					return;
				}
				if($("#srvyTtl").val()==""){
					modal("System message",'The "Survey Title" is mandatory.');
					return;
				}
				if($("#srvyTtl").val().length > 256){
					modal("System message",'The "Survey Title" must be less than or equal to 256 characters.');
					return;
				}
				if($("#bgnDate").val()==""){
					modal("System message",'The "Start Date" is mandatory.');
					return;
				}
				if($("#endDate").val()==""){
					modal("System message",'The "End Date" is mandatory.');
					return;
				}
				if ($('#endDate').datepicker("getDate") < $('#bgnDate').datepicker("getDate")) {
					modal("System message",'"Start Date" cannot be greater than "End Date".');
					return;					
				}
				if($("#srvyPptCatg\\.srvyPptCatgId").val()=="1" && $("#pptDivCode").val()=="" ){
					modal("System message",'The "Division of Participants" is mandatory.');
					return;
				}
				if($("#srvyTtl").val().length > 1000){
					modal("System message",'The "Survey Remark" must be less than or equal to 1000 characters.');
					return;
				}
				/* checking here */
				$("#updateButton").prop("disabled", true);
				$("#pptDivCode").prop('disabled', false);
				$("#srvyPptCatg\\.srvyPptCatgId").prop('disabled', false);
				$("#srvyTtl").prop('disabled', false);
				$("#srvyTmpl\\.srvyTmplId").prop('disabled', false);
				$.ajax({
					type: "POST",
					url: "${basePath}/survey/updateSurvey",
					data: $('#srvyRecForm').serializeArray(),
					success: function(data){
						data=jQuery.parseJSON(data);console.log(data);
						modal("System message",data.message,function(){
							if(data.success=="true"){
								$.redirect('${basePath}/survey/modify/', {'id': $("#srvyRecId").val()});
							}
						});					
						$("#updateButton").prop("disabled", false);
					},error: function(data){
						modal("System message","System is busy, please try again.");
						$("#updateButton").prop("disabled", false);
					}, timeout: 10000    
				});
			});
			
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				if(e.target.id=="participant-tab" && participantTable==null){
					initialParticipantTable();
				}else if(e.target.id=="participant-tab" && participantTable!=null){
					participantTable.draw();
				}else if(e.target.id=="history-tab" && historyTable==null){
					initialHistoryTable();
				}else if(e.target.id=="adtnInfo-tab" && adtnInfoTable==null){
					initialAdtnInfoTable();
				}
				if(e.target.id=="participant-tab" && participantTable!=null){
					if($('#srvyPptCatg\\.srvyPptCatgId').val()=='3'){
				       participantTable.column(11).visible(true);
				       participantTable.column(12).visible(true);
				       participantTable.column(13).visible(true);
					}else{
					   participantTable.column(11).visible(false);
				       participantTable.column(12).visible(false);
				       participantTable.column(13).visible(false);
					}			
				}
			});
		});
	</script>
</body>
</html>