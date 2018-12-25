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
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/survey.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/surveyeditor.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/select2.min.css" />
<link rel="stylesheet" href="${basePath}/resources/css/jquery-ui.css">
<link rel="stylesheet" href="${basePath}/resources/css/richtext.min.css">
<link rel="shortcut icon" href="${basePath}/resources/img/favicon.ico">
<script src="${basePath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${basePath}/resources/vendor/jquery/jquery.redirect.js"></script>
<script src="${basePath}/resources/vendor/jquery/jquery-ui.js"></script>
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
</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
	    <div class="breadcrumb-holder">  
	        <div class="container-fluid" style="padding:5px 15px 5px 15px;">
	          <h5>Create Survey</h5>
	        </div> 
	    </div>
		<section>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<form:form method="POST" modelAttribute="srvyRec" id="srvyRecForm">
							<form:hidden path="srvyRecId" />
							<div class="card">
								<div class="card-body">
									<ul class="nav nav-tabs" id="myTab" role="tablist">
										<li class="nav-item"><a class="nav-link active" id="detail-tab" data-toggle="tab" href="#detail" role="tab" aria-controls="home" aria-selected="true">Survey Details</a></li>
									</ul>
									<div class="tab-content" id="myTabContent" style="padding-top: 20px;">
										<div class="tab-pane fade show active" id="detail" role="tabpanel" aria-labelledby="detail-tab">
											<div class="form-group row hideForPartipipant mb-0">
												<div class="col-sm-12">
													<button type="button" id="addButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-plus-square"></i> Create</button>									
												</div>
											</div>
											<%@ include file="/WEB-INF/views/survey/shared/detail.jsp"%>
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
		var editor = null;
		var participantTable = null;
		var historyTable = null;
		function pptCatgOnChange(){
			if($("#srvyPptCatg\\.srvyPptCatgId").val()!="1"){
				$("#pptDivCode").val("");
				$("#pptDivCode").prop('disabled', true);
			}else{
				$("#pptDivCode").prop('disabled', false);
			}
		}		
		
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
			$('textarea').richText();
			
			pptCatgOnChange();	
			
			$("#searchHistoryButton").click(function() {
				$("#searchHistoryButton").prop("disabled", true);
				historyTable.draw();
			});
			
			$("#srvyPptCatg\\.srvyPptCatgId").change(function() {
				pptCatgOnChange();
			});
			
			$("#addButton").click(function() {
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
				$("#addButton").prop("disabled", true);
				$.ajax({
				  type: "POST",
				  url: "${basePath}/survey/addSurvey",
				  data: $('#srvyRecForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						if(data.success=="true"){
							$.redirect('${basePath}/survey/modify/', {'id': data.id});
						}
					});					
				  	$("#addButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#addButton").prop("disabled", false);
				  }, timeout: 10000    
				});
			});
			
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				if(e.target.id=="participant-tab" && participantTable==null){
					participantTable = $('#participantTable').DataTable({
						"ajax": {
						    "url": "${basePath}/survey/listParticipant/",
						    "type": "POST"
						},
						"columnDefs": [{
					         "targets": 0, 
					         "orderable": false,
					         "className": "dt-body-center ",
					         "render": function (data, type, full, meta){
					             return '<input type="checkbox" name="id[]" value="' + $('<div/>').text(data).html() + '">';
					         }
					     }],"order": [[1, 'asc']],
					     "createdRow": function ( row, data, index ) {
				            if ( data[3]=="") {
				                $('td', row).eq(0).addClass('highlight');
				                $('td', row).eq(1).addClass('highlight');
				                $('td', row).eq(2).addClass('highlight');
				                $('td', row).eq(3).addClass('highlight');
				                $('td', row).eq(4).addClass('highlight');
				                $('td', row).eq(5).addClass('highlight');
				                $('td', row).eq(6).addClass('highlight');
				                $('td', row).eq(7).addClass('highlight');
				            }
				            $('td', row).eq(1).addClass('dt-body-center');
				            $('td', row).eq(4).addClass('dt-body-center');
				            $('td', row).eq(5).addClass('dt-body-center');
				            $('td', row).eq(6).addClass('dt-body-center');
				            $('td', row).eq(7).addClass('dt-body-center');
				        },
				        "serverSide" : true,
						"processing": true, 
						"destroy" : true,
						"pagingType": "full_numbers",
					    "bPaginate": true,
					    "bLengthChange": false,
					    "pageLength": 500,
					    "bFilter": false,
					    "bInfo": true,
					    "bAutoWidth": true});
				}else if(e.target.id=="history-tab" && historyTable==null){
					historyTable = $('#historyTable').DataTable({
						"ajax": {
						    "url": "${basePath}/survey/listEmailHistory/",
						    "type": "POST",
						    "data": function(d) {
						    	console.log($('#srvyRecForm').serializeArray());
						    	d.filter = $('#srvyRecForm').serializeArray();
						    }
						},
						"columnDefs": [{
					         "targets": [0,1,3,4],
					         "className": "dt-body-center "
					     }],
					    "serverSide" : true,
						"processing": true, 
						"destroy" : true,
						"pagingType": "full_numbers",
					    "bPaginate": true,
					    "bLengthChange": false,
					    "pageLength": 50,
					    "bFilter": false,
					    "bInfo": true,
					    "bAutoWidth": true});
					
					historyTable.on( 'draw', function () {
						setTimeout(function(){$("#searchHistoryButton").prop("disabled", false);}, 500)
					});
				}
			});   
		});
	</script>
</body>
</html>