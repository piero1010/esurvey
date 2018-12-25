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
<link rel="stylesheet" href="${basePath}/resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/survey.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/surveyeditor.css" />
<link rel="stylesheet" href="${basePath}/resources/vendor/survey-jquery/select2.min.css" />
<link rel="shortcut icon" href="${basePath}/resources/img/favicon.ico">
<link rel="stylesheet" href="${basePath}/resources/vendor/vis/vis.min.css" />
<!-- Favicon-->
<style>
.sv_main.sv_default_css .sv_p_root>.sv_row:nth-child(2n) {
	background-color: black;
}

.svd_editor_control {
	height: 34px !important;
}

.svd_commercial_container, .svd_save_btn {
	display: none;
}

.svd_editors, .svd_toolbox {
	padding: 0px;
}

.svd_container .svd_toolbox {
	font-size: 1em;
}

.svd_container .svd_content .svd_survey_designer .svd_editors .svd_questions_editor .panel-body .svd_question,
	.svd_container .svd_content .svd_survey_designer .svd_editors .svd_questions_editor .card-block .svd_question
	{
	margin-bottom: 28px;
}

.svd_questions_editor {
	padding: 10px;
}

.svd_container .editor-tabs {
	padding: 0;
}

.fas {
	font-size: 50px;
}
section{min-height:800px;}
</style>
<script type="text/javascript" src="${basePath}/resources/vendor/google/loader.js"></script>

<script>
/*
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

 function drawChart() {

      // Create the data table.
      var data1 = new google.visualization.DataTable();
      data1.addColumn('string', 'Topping');
      data1.addColumn('number', 'Slices');
      data1.addRows([
        ['Mushrooms', 3],
        ['Onions', 1],
        ['Olives', 1],
        ['Zucchini', 1],
        ['Pepperoni', 2]
      ]);

      // Set chart options
      var options1 = {'title':'How Much Pizza I Ate Last Night',
                     'width':400,
                     'height':300};

      // Instantiate and draw our chart, passing in some options.
      var chart1 = new google.visualization.PieChart(document.getElementById('pieChart'));
      chart1.draw(data1, options1);
 }
*/
</script>


</head>
<body>
	<%@ include file="/WEB-INF/views/shared/menu.jsp"%>
	<div class="page active">
		<!-- navbar-->
		<%@ include file="/WEB-INF/views/shared/navbar.jsp"%>
		<!-- Header Section-->
		<div class="breadcrumb-holder">
			<div class="container-fluid" style="padding: 5px 15px 5px 15px;">
				<h5>Dashboard</h5>
			</div>
		</div>
		<section>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row">
					<div class="col-lg-12" style="margin: 10px 0 0 0; padding: 0;">
						<div class="row d-flex">
							<div class="col-lg-3 col-sm-6 ">
								<div class="card text-white bg-primary mb-0">
									<div class="card-header">Create Template</div>
									<div class="card-body text-center">
										<!--user icon in two different styles-->
										<p>
											<a href="${basePath}\template"><i class="fas fa-file-invoice"></i></a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6">
								<div class="card text-white bg-primary mb-0">
									<div class="card-header">List Templates</div>
									<div class="card-body text-center">
										<!--user icon in two different styles-->
										<p>
											<a href="${basePath}\template\list"><i class="fas fa-list-ul"></i></a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6">
								<div class="card text-white bg-primary mb-0">
									<div class="card-header">Create Survey</div>
									<div class="card-body text-center">
										<!--user icon in two different styles-->
										<p>
											<a href="${basePath}\survey"><i class="fas fa-plus-square "></i></a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-sm-6">
								<div class="card text-white bg-primary mb-0">
									<div class="card-header">List Surveys</div>
									<div class="card-body text-center">
										<!--user icon in two different styles-->
										<p>
											<a href="${basePath}\survey\list"><i class="fas fa-list"></i></a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row">
					<div class="col-lg-12" style="margin: 10px 0 10px 0; padding: 0;">
						<div class="card" class="nopadding">
						<div class="card-header">Survey Timeline</div>
							<div id="visualization"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid" style="padding: 0 28px 0 28px;">
				<div class="row">
					<div class="col-lg-12" style="margin: 10px 0 10px 0; padding: 0;">
						<div class="card" class="nopadding">
							<div class="card-header">Survey Progress</div>
							<div class="card-body">
								<div class="row">
									<div class="col-sm-12">
										<div id="SurveyName"></div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-3">
										<h2 class="display h4">Progress</h2>
										<div class="pie-chart">
											<canvas id="pieChart" width="300" height="300"> </canvas>
										</div>
									</div>
									<!-- Line Chart -->
									<div class="col-sm-9">
										<h2 class="display h4">Numbers of participants have not be submitted</h2>
										<div class="line-chart">
											<canvas id="lineChart" height="100"></canvas>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<footer class="main-footer">
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-6">
						<p>E-Survey System</p>
					</div>
					<div class="col-sm-6 text-right"></div>
				</div>
			</div>
		</footer>
	</div>
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
	<script src="${basePath}/resources/vendor/chart.js/Chart.min.js"></script>
	<script src="${basePath}/resources/vendor/vis/vis.min.js"></script>
	<script>
		var myPieChart;
		var myLineChart;
		var pieOptions = {
			events : false,
			animation : {
				duration : 500,
				easing : "easeOutQuart",
				onComplete : function() {
					var ctx = this.chart.ctx;
					ctx.font = Chart.helpers.fontString(
							Chart.defaults.global.defaultFontFamily, 'normal',
							Chart.defaults.global.defaultFontFamily);
					ctx.textAlign = 'center';
					ctx.textBaseline = 'bottom';

					this.data.datasets.forEach(function(dataset) {
						for (var i = 0; i < dataset.data.length; i++) {
							var model = dataset._meta[Object
									.keys(dataset._meta)[0]].data[i]._model, total = dataset._meta[Object
									.keys(dataset._meta)[0]].total, mid_radius = model.innerRadius
									+ (model.outerRadius - model.innerRadius)
									/ 2, start_angle = model.startAngle, end_angle = model.endAngle, mid_angle = start_angle
									+ (end_angle - start_angle) / 2;

							var x = mid_radius * Math.cos(mid_angle);
							var y = mid_radius * Math.sin(mid_angle);

							ctx.fillStyle = '#fff';
							if (i == 3) { // Darker text color for lighter background
								ctx.fillStyle = '#444';
							}
							var percent = String(Math.round(dataset.data[i] / total	* 100))	+ "%";
									// Display percent in another line, line break doesn't work for fillText
							ctx.fillText(percent, model.x + x, model.y	+ y + 15);
						}
					});
				}
			}
		};

		$(document).ready(function() {
					$.ajax({url : "${basePath}/dashboard/getData/",
					type : "GET",
					cache : false,
					success : function(data) {
						var container = document.getElementById('visualization');
						var jsonArr = JSON.parse(data);
						var items = new vis.DataSet(jsonArr);
						var options = {stack: true,
							    verticalScroll: true,
							    zoomKey: 'ctrlKey',
							    maxHeight: 300,
							    start: new Date()};

						// Configuration for the Timeline
						options1 = jQuery.extend(options,{});
						// Create a Timeline
						var timeline = new vis.Timeline(container, items, options1);
						timeline.on('select',function(properties) {
							var myFound = {};
							if (properties.items.length > 0) {
								for (var i = 0; i < jsonArr.length; i++) {
									var element = jsonArr[i];
									if (element.id == properties.items) {
										myFound = element;
									}
								}
								selectSurveyChange(myFound);
							}
						});
				        
						// initial display
						var firstId = items.getIds()[1];

						if (firstId != undefined) {
							timeline.setSelection(items.get(firstId).id, {
								focus : true
							});
							selectSurveyChange(items.get(firstId));
						}
					},
					error : function(err) {
						console.log('Error', err);
						if (err.status === 0) {
							alert('Failed to load data/basic.json.\nPlease run this example on a server.');
						} else {
							alert('Failed to load data/basic.json.');
						}
					}
			});
					
		});

		function selectSurveyChange(resultDashboard) {
			$("#SurveyName")[0].innerHTML = resultDashboard.content;

			if (myPieChart){
				myPieChart.destroy();
			}

			var PIECHART = $('#pieChart');
			myPieChart = new Chart(PIECHART, {
				ID : 'Pabc',
				type : 'pie',
				options : pieOptions,
				data : {
					labels : [ "Submitted", "Not submitted" ],
					datasets : [ {
						data : [ resultDashboard.submit, resultDashboard.notSubmit ],
						borderWidth : [ 1, 1 ],
						backgroundColor : [ "rgba(36, 166, 71, 0.8)",
								"rgba(219, 53, 68, 0.8)" ],
						hoverBackgroundColor : [ "rgba(36, 166, 71, 1)",
								"rgba(219, 53, 68, 1)" ]
					} ]
				}
			});

			'use strict';
			// Main Template Color
			var brandPrimary = '#33b35a';

			// ------------------------------------------------------- //
			// Line Chart
			// ------------------------------------------------------ //
			if (myLineChart){
				myLineChart.destroy();
			}

			var LINECHART = $('#lineChart');
			myLineChart = new Chart(LINECHART, {
				type : 'line',
				options : {
					legend : {
						display : false
					},
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true
							}
						} ]
					}
				},
				data : {
					labels : resultDashboard.dateList,
					datasets : [ {
						label : "Survey Details",
						fill : true,
						steppedLine : true,
						lineTension : 0.3,
						backgroundColor : "rgba(23,161,183, 0.8)",
						borderColor : brandPrimary,
						borderCapStyle : 'butt',
						borderDash : [],
						borderDashOffset : 0.0,
						borderJoinStyle : 'miter',
						borderWidth : 1,
						pointBorderColor : brandPrimary,
						pointBackgroundColor : "#fff",
						pointBorderWidth : 1,
						pointHoverRadius : 5,
						pointHoverBackgroundColor : brandPrimary,
						pointHoverBorderColor : "rgba(23,161,183, 1)",
						pointHoverBorderWidth : 2,
						pointRadius : 1,
						pointHitRadius : 0,
						data : resultDashboard.cntList,
						spanGaps : false
					} ]
				}
			});
		}
	</script>

</body>
</html>