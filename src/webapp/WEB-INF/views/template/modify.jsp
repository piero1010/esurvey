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
<script src="${basePath}/resources/vendor/popper.js/umd/popper.min.js"></script>
<script src="${basePath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/js/grasp_mobile_progress_circle-1.0.0.min.js"></script>
<script src="${basePath}/resources/vendor/jquery.cookie/jquery.cookie.js"></script>
<script src="${basePath}/resources/vendor/jquery-validation/jquery.validate.min.js"></script>
<script src="${basePath}/resources/vendor/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${basePath}/resources/js/front.js"></script>
<script src="${basePath}/resources/vendor/knockout/knockout-debug.js"></script>
<script src="${basePath}/resources/vendor/knockout/survey.ko.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/surveyeditor.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/select2.min.js"></script>
<style>
.svd_container .svd_properties{display:none;}
use{pointer-events:none;}
input[type=radio]{
  transform:scale(1.5);
}

.btn-default:not([disabled]):not(.disabled):active, .btn-default:not([disabled]):not(.disabled).active,
.show > .btn-default.dropdown-toggle {
  color:#fff;
  background-color: #17a2b8;
  border-color: #17a2b8;
  -webkit-box-shadow: 0 0 0 0.2rem rgba(206, 212, 218, 0.5);
  box-shadow: 0 0 0 0.2rem rgba(206, 212, 218, 0.5);
}
.sticky {
    position: -webkit-sticky;
    position: sticky;
    top: 0;
    z-index:999;
}
.sv_complete_btn{display:none;}

.sv_main .sv_container{color:#000;}

.sv_qstn label.sv_q_m_label{position:relative !important;} 

.sv_main .sv_q_other input, .sv_main .sv_q_text_root, .sv_main .sv_q_dropdown_control, .sv_main input[type="text"], .sv_main select, .sv_main textarea{border:1px #AAA solid;}

.sv_qstn .sv-q-col-5{
	padding-left: 5px;
}

table {
	font-size: 14px;
}
 
.matrixtable th:nth-child(n+2), .matrixtable td:nth-child(n+2){
  width: 1%;
  text-align:center;
}

.matrixtable tbody tr:first-child td:first-child {
    border-top:2px solid #ddd;
}

${srvyTmpl.srvyTmplCss}

.sv_qstn{padding:10px !important;}
.table td, .table th{border:0;}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp" %>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp" %>
		<!-- Header Section-->
		<div class="breadcrumb-holder">  
			<div class="row" style="padding: 5px 15px 5px 15px;vertical-align:middle;">
				<div class="col-sm-8 " style="padding-top:5px;">
						<h5 >Template Maintenance</h5>  
					</div>
	          	<div class="col-sm-4">
					<button type="button" id="closeButton" class="btn btn-secondary btn-sm float-right align-middle" style="padding: 1px 5px 1px 5px;"><i class="fas fa-ban"></i> Close</button>
				</div>
	        </div> 
	    </div>
		<section>
			<div class="container-fluid" style="padding:0 28px 0 28px;">
				<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12 " style="margin: 0; padding: 0;">
						<form:form method="POST" modelAttribute="SrvyTmpl" id="srvyTmplForm">
							<form:hidden path="srvyTmplCnfg" />
							<form:hidden path="srvyTmplId" />
							<div class="form-group row">
								<div class="col-sm-12">
									<c:if test="${SrvyTmpl.srvyTmplFrzInd.srvyTmplFrzIndId!='F'}">
										<button type="button" id="updateButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-edit"></i> Update</button>
									</c:if>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="srvyTmplId" class="col-sm-2 col-form-label col-form-label-sm">Template ID</form:label>
								<div class="col-sm-4">
									<form:input path="srvyTmplId" class="form-control form-control-sm" readonly="true"/>
								</div>  
								<form:label path="srvyTmplName" class="col-sm-2 col-form-label col-form-label-sm">Template Name*</form:label>
								<div class="col-sm-4">
									<c:if test="${SrvyTmpl.srvyTmplFrzInd.srvyTmplFrzIndId=='F'}">
										<form:input path="srvyTmplName" class="form-control form-control-sm" readonly="true"/>
									</c:if>
									<c:if test="${SrvyTmpl.srvyTmplFrzInd.srvyTmplFrzIndId!='F'}">
										<form:input path="srvyTmplName" class="form-control form-control-sm" />
									</c:if>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="srvyGrp.srvyGrpId" class="col-sm-2 col-form-label col-form-label-sm">Survey Group*</form:label>
								<div class="col-sm-4">
									<c:if test="${SrvyTmpl.srvyTmplFrzInd.srvyTmplFrzIndId=='F'}">
										<form:select path="srvyGrp.srvyGrpId" class="form-control form-control-sm" disabled="true">
											<form:option value=""></form:option>
											<form:options items="${srvyGrpHashMap}" />
										</form:select>
									</c:if>
									<c:if test="${SrvyTmpl.srvyTmplFrzInd.srvyTmplFrzIndId!='F'}">
										<form:select path="srvyGrp.srvyGrpId" class="form-control form-control-sm">
											<form:option value=""></form:option>
											<form:options items="${srvyGrpHashMap}" />
										</form:select>
									</c:if>
								</div>
								<form:label path="srvyTmplFrzInd.srvyTmplFrzIndId" class="col-sm-2 col-form-label col-form-label-sm">Status</form:label>
								<div class="col-sm-4">
									<form:select path="srvyTmplFrzInd.srvyTmplFrzIndId" class="form-control form-control-sm" disabled="true">
										<form:options items="${srvyTmplFrzIndHashMap}" />
									</form:select>
								</div>
							</div>
							<div class="form-group row">
								<div class="col-sm-12" style="border-top: 1px #EEE solid;"></div>
							</div>
							<div class="form-group row">
								<div class="col-sm-12">
									<div id="editorElement"></div>
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
		$( document ).ready(function() {
			
			$('#closeButton').on( 'click', function () {
				confirmModal('System message','Any unsaved data will be lost.<br/>Do you want to close this page?',function(){					
					parent.history.back();
				});
			});
			
			Survey.Survey.cssType = "bootstrap";
			var editorOptions = {
				    questionTypes: ["text", "checkbox", "radiogroup", "dropdown","comment","rating","imagepicker","boolean","panel","paneldynamic","matrix","matrixdropdown","matrixdynamic","html"], 
				    showJSONEditorTab:true
			};
			
			editor = new SurveyEditor.SurveyEditor("editorElement",editorOptions);
			editor.toolbox.changeCategories([
		        {name: "panel",category: "Panels"}, 
		        {name: "paneldynamic",category: "Panels"}, 
		        {name: "matrix",category: "Matrix"},
		        {name: "matrixdropdown",category: "Matrix"},
		        {name: "matrixdynamic",category: "Matrix"}
		    ]); 
			editor.text = $("#srvyTmplCnfg").val();
			$( "#updateButton" ).click(function() {
				if($("#srvyTmplName").val()==""){
					modal("System message",'The "Template Name" is mandatory');
					return;
				}
				if($("#srvyGrpId").val()=="0"){
					modal("System message",'The "Survey Group" is mandatory');
					return;
				}
				/* checking here */
				$("#updateButton").prop("disabled", true);
				$("#srvyTmplFrzInd\\.srvyTmplFrzIndId").prop("disabled", false);
				$("#srvyTmplCnfg").val(editor.text);
				$.ajax({
				  type: "POST",
				  url: "${basePath}/template/updateTemplate",
				  data: $('#srvyTmplForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message);					
				  	$("#updateButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#updateButton").prop("disabled", false);
				  }, timeout: 10000
				});
			});
			
			$(".svd_editors").first().removeClass("col-lg-7").removeClass("col-md-7").addClass("col-lg-10").addClass("col-md-10");
		});
	</script>
</body>
</html>