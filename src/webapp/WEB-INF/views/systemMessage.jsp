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
.sv_main.sv_default_css .sv_p_root>.sv_row:nth-child(2n) {
	background-color: black;
}
/*
#example .table th, .table td {
    padding: 0.5rem;
}*/
#toggle-btn {
	display: none;
}

.page {
	position: relative;
}

.page.active {
	width: 100%;
}
</style>
</head>
<body>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
		<section>
			<div class="container-fluid" style="padding: 0 228px 0 228px;">
					<br/><br/><br/>
					<div class="card mx-auto" > 
						<div class="card-body">
							<h5 class="card-title">System Message</h5>
							<p class="card-text">${message}</p>
						</div>
				</div>
			</div>
		</section>
		<%@ include file="/WEB-INF/views/shared/footer.jsp"%>
	</div>
</body>
</html>