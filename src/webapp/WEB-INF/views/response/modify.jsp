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
<script src="${basePath}/resources/js/stickyfill.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/survey.jquery.js"></script>
<script src="${basePath}/resources/vendor/survey-jquery/select2.min.js"></script>
<script src="${basePath}/resources/js/showdown.min.js"></script>
<style>

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

${srvyRec.srvyTmpl.srvyTmplCss}

.sv_qstn{padding:10px !important;}
.table td, .table th{border:0;}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<div class="page active">
		<!-- navbar-->
		<div class="widget sticky" >
			<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
			<!-- Header Section-->
			<div class="breadcrumb-holder">
				<div class="row" style="padding: 5px 15px 5px 15px;vertical-align:middle;">
					<div class="col-sm-8 " style="padding-top:5px;">
						<h5 >${srvyRec.srvyTtl}</h5>  
					</div>
					<div class="col-sm-4">
						<c:if test="${empty srvyRec.srvyPpt[0].sbmtSts}">
							<button type="button" id="submitButton" class="btn btn-primary btn-sm float-right align-middle" style="padding:1px 5px 1px 5px;margin-right: 10px;" ><i class="fas fa-caret-right"></i> Submit</button>
							<button type="button" id="saveButton" class="btn btn-info btn-sm float-right align-middle" style="padding:1px 5px 1px 5px;margin-right: 10px;" ><i class="fas fa-save"></i> Save</button>
						</c:if>
						<button type="button" id="printButton" class="btn btn-info btn-sm float-right align-middle" style="padding:1px 5px 1px 5px;margin-right: 10px;" ><i class="fas fa-print"></i> Print</button>
						<button type="button" id="closeButton" class="btn btn-secondary btn-sm float-right align-middle" style="padding: 1px 5px 1px 5px; margin-right: 10px;"><i class="fas fa-ban"></i> Close</button>
					</div>
				</div>
			</div>
		</div>
		<section>
			<div class="container-fluid p-10">
				<div id="surveyContainer"></div>
			</div>
		</section>
		<%@ include file="/WEB-INF/views/shared/footer.jsp"%>
	</div>
	<script>
		Survey.Survey.cssType = "bootstrap";
		var survey = null;
		var surveyJSON = ${srvyRec.srvyTmpl.srvyTmplCnfg}
		
		function getSurveyAnswer(survey) {
			var item = "";
		   	for(var i=0;i<survey.getAllQuestions().length;i++){
		   		console.log(survey.getAllQuestions()[i].getType());
		   		switch(survey.getAllQuestions()[i].getType()) {
			   	    case "html":
			   	        break;
			   	    case "multipletext":
			   	    	if(typeof  survey.getAllQuestions()[i].value != 'undefined'){
				   	 		item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ JSON.stringify(survey.getAllQuestions()[i].value)+',';	
			    		}else{
			    			item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ '\"\",';
			    		}
		   	        	break;
			   	 	case "matrix":
			   	 		var out = "{";
			   	 		if(survey.getAllQuestions()[i].generatedVisibleRows.length>0){
				   	 		for(var j=0;j<survey.getAllQuestions()[i].generatedVisibleRows.length;j++){
				   	 			if(survey.getAllQuestions()[i].generatedVisibleRows[j].value && survey.getAllQuestions()[i].generatedVisibleRows[j].value!=null && survey.getAllQuestions()[i].generatedVisibleRows[j].value!='null'){
				   	 				out += '\"'+survey.getAllQuestions()[i].generatedVisibleRows[j].name+'\"' +':'+ '\"'+ survey.getAllQuestions()[i].generatedVisibleRows[j].value+'\",';
				   	 			}else{
				   	 				out += '\"'+survey.getAllQuestions()[i].generatedVisibleRows[j].name+'\"' +':'+ '\"\",';
				   	 			}
				   	 		}
				   	 		out = out.slice(0,-1);
			   	 		}
			   	 		out += "}";
				   	 	if(typeof  survey.getAllQuestions()[i].value != 'undefined'){
				   	 		item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ out+',';	
			    		}else{
			    			item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ '\"\",';
			    		}
		   	        	break;
			   	    default:
			   	    	if(typeof  survey.getAllQuestions()[i].value != 'undefined'){
			    			item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ '\"'+ survey.getAllQuestions()[i].value+'\",';	
			    		}else{
			    			item += '\"'+survey.getAllQuestions()[i].name+'\"' +':'+ '\"\",';
			    		}
			   	}
		   	}
		   	console.log(survey.data);
		   	return item.slice(0,-1);
		}
		
		$( document ).ready(function() {
	
			var myCss = {
				    matrix: {root: "table table-striped matrixtable"},
			};
			
			survey = new Survey.Model(surveyJSON);
			survey.data = {${srvyRec.srvyPpt[0].srvyCntn}};
		

				//Create showdown mardown converter
				var converter = new showdown.Converter();
				survey
				    .onTextMarkdown
				    .add(function (survey, options) {
				        //convert the mardown text to html
				        var str = converter.makeHtml(options.text);
				        //remove root paragraphs <p></p>
				        str = str.substring(3);
				        str = str.substring(0, str.length - 4);
				        //set html
				        options.html = str;
				    });
			survey.onAfterRenderPage.add(function(result, options) {
				try {
					try {
						for(var i=0;i<survey.getAllQuestions().length;i++){
							try {
								if(survey.getAllQuestions()[i].visibleIf){
									var criteria = survey.getAllQuestions()[i].visibleIf;
									var test1 = survey.getQuestionByName(criteria.split('{').pop().split('}').shift()).value+"";
									var test2 = criteria.split('["').pop().split('"]').shift();
									
									if(test1==test2){
										survey.getAllQuestions()[i].visible=true;
									}
								}
							}catch(ex){}
						}
					}catch(ex){}
				}catch(ex){}
			});
			survey.onCurrentPageChanged.add(function() {
				$.ajax({
					type: "POST",
					url: "${basePath}/refreshSession",
					success: function(data){
						try{
							console.log("clearInterval(interval2);");
							clearInterval(interval2);
						}catch(ex){console.log(ex);}
						$('#timeoutModal').modal("hide");
					}
				});
			});
			<c:if test="${not empty srvyRec.srvyPpt[0].sbmtSts}">
				survey.mode = 'display';
			</c:if>
			
			$("#surveyContainer").Survey({
			    model: survey,
			    css: myCss
			});
			
			var elements = document.querySelectorAll('.sticky');
			Stickyfill.add(elements);
			
			$('#closeButton').on( 'click', function () {
				confirmModal('System message','Any unsaved data will be lost.<br/>Do you want to close this page?',function(){					
					parent.history.back();
				});
			});
			
			$('#submitButton').on( 'click', function () {
				if(!survey.currentPage.hasErrors(true, true)){
					confirmModal('System message','The survey form cannot be modified once submitted.<br/>Do you want to submit the response?',function(){
						$.ajax({
							  type: "POST",
							  url: "${basePath}/response/submitResponse",
							  data: {response : getSurveyAnswer(survey), SrvyPptId:"${srvyRec.srvyPpt[0].srvyPptId}"},
							  success: function(data){
								data=jQuery.parseJSON(data);					
								modal("System message",data.message,function(){
									window.location.href = "${basePath}/";
								});					
							  	$("#createButton").prop("disabled", false);
							  },error: function(data){
								modal("System message","System is busy, please try again.");
							  	$("#createButton").prop("disabled", false);
							  }, timeout: 10000    
							});
					});
				}
		    });
			
			$('#printButton').on( 'click', function () {
				$(".breadcrumb-holder").css("visibility","hidden");
				window.print();
				$(".breadcrumb-holder").css("visibility","visible");
		    });
			
			
			$('#saveButton').on( 'click', function () {
				console.log(survey.data);
			   	$.ajax({
				  type: "POST",
				  url: "${basePath}/response/saveResponse",
				  data: {response : getSurveyAnswer(survey), SrvyPptId:"${srvyRec.srvyPpt[0].srvyPptId}"},
				  success: function(data){
					data=jQuery.parseJSON(data);					
					modal("System message",data.message,function(){
						
					});					
				  	$("#createButton").prop("disabled", false);
				  },error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#createButton").prop("disabled", false);
				  }, timeout: 10000    
				});			   	
		    });
			
			$(".card-footer").removeClass("card-footer");
		});
	</script>
</body>
</html>