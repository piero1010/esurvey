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
use{pointer-events:none;}
.svd_container .svd_properties{display:none;}
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
						<h5 >Create Template</h5>  
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
						<form:form method="POST" modelAttribute="SrvyTmpl" id="srvyTmplForm">
							<form:hidden path="srvyTmplCnfg" />
							<form:hidden path="srvyTmplId" />
							<div class="form-group row mb-0">
								<div class="col-sm-12">
									<button type="button" id="addButton" class="btn btn-primary btn-sm"  style="margin-right: 10px;"><i class="fas fa-plus-square"></i> Create</button>									
								</div>
							</div>
							<div class="form-group row">
								<form:label path="srvyTmplId" class="col-sm-2 col-form-label col-form-label-sm">Template ID</form:label>
								<div class="col-sm-4">
									<input type="text" readonly="true" value="New Template" class="form-control form-control-sm" />
								</div>
								<form:label path="srvyTmplName" class="col-sm-2 col-form-label col-form-label-sm">Template Name*</form:label>
								<div class="col-sm-4">
									<form:input path="srvyTmplName" class="form-control form-control-sm" />
									<form:errors path="srvyTmplName" cssClass="error"/>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="srvyGrp.srvyGrpId" class="col-sm-2 col-form-label col-form-label-sm">Survey Group*</form:label>
								<div class="col-sm-4">
									<form:select path="srvyGrp.srvyGrpId" class="form-control form-control-sm">
										<form:option value=""></form:option>
										<form:options items="${srvyGrpHashMap}" />
									</form:select>
								</div>
								<form:label path="srvyTmplFrzInd.srvyTmplFrzIndId" class="col-sm-2 col-form-label col-form-label-sm">Status</form:label>
								<div class="col-sm-4"> 
									<form:select path="srvyTmplFrzInd.srvyTmplFrzIndId" class="form-control form-control-sm" disabled="true">
										<form:options items="${srvyTmplFrzIndHashMap}" />
									</form:select>
								</div>
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
		$(document).ready(function() {
			Survey.Survey.cssType = "bootstrap";
			var editorOptions = {
				    questionTypes: ["text", "checkbox", "radiogroup", "dropdown","comment","rating","imagepicker","boolean","panel","paneldynamic","matrix","matrixdropdown","matrixdynamic","html"], 
				    showJSONEditorTab:true
			};
			
			$('#closeButton').on( 'click', function () {
				confirmModal('System message','Any unsaved data will be lost.<br/>Do you want to close this page?',function(){					
					parent.history.back();
				});
			});
			
			editor = new SurveyEditor.SurveyEditor("editorElement",editorOptions);
			
			editor.toolbox.changeCategories([{
				name : "panel",
				category : "Style"
			}, {
				name : "paneldynamic",
				category : "Style"
			}, {
				name : "html",
				category : "Style"
			}]);
			editor.saveSurveyFunc = function(saveNo, callback) {

			};
			window.editor = editor;
			$("#addButton").click(function() {
				/* checking here */
				if($("#srvyTmplName").val()==""){
					modal("System message",'The "Template Name" is mandatory.');
					return;
				}
				if($("#srvyGrpId").val()=="0"){
					modal("System message",'The "Survey Group" is mandatory.');
					return;
				}
				/* checking here */
				$("#addButton").prop("disabled", true);
				$("#srvyTmplCnfg").val(editor.text);
				
				$.ajax({
				  type: "POST",
				  url: "${basePath}/template/addTemplate",
				  data: $('#srvyTmplForm').serializeArray(),
				  success: function(data){
					data=jQuery.parseJSON(data);
					modal("System message",data.message,function(){
						if(data.success=="true"){
							$.redirect('${basePath}/template/modify/', {'id': data.id});
						}
					});					
				  	$("#addButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#addButton").prop("disabled", false);
				  }, timeout: 10000    
				});
			});
			
			$(".svd_editors").first().removeClass("col-lg-7").removeClass("col-md-7").addClass("col-lg-10").addClass("col-md-10");
		});
	</script>	
</body>
</html>