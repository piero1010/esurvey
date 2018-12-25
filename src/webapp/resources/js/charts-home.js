/*global $, document, Chart, LINECHART, data, options, window*/
$(document).ready(function() {

	'use strict';

	// Main Template Color
	var brandPrimary = '#33b35a';

	// ------------------------------------------------------- //
	// Line Chart
	// ------------------------------------------------------ //
	var LINECHART = $('#lineCahrt');
	var myLineChart = new Chart(LINECHART, {
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
				}
				]
			}
		},
		data : {
			labels : [ "01 May", "02 May", "03 May", "04 May", "05 May", "06 May", "07 May"
			],
			datasets : [ {
				label : "My First dataset",
				fill : true,
				steppedLine: true,
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
				data : [ 100, 50, 40, 30, 10, 10, 10
				],
				spanGaps : false
			}
			]
		}
	});
	myLineChart.height = 100;

	// ------------------------------------------------------- //
	// Pie Chart
	// ------------------------------------------------------ //
	var PIECHART = $('#pieChart');
	var pieOptions = {
			  events: false,
			  animation: {
			    duration: 500,
			    easing: "easeOutQuart",
			    onComplete: function () {
			      var ctx = this.chart.ctx;
			      ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontFamily, 'normal', Chart.defaults.global.defaultFontFamily);
			      ctx.textAlign = 'center';
			      ctx.textBaseline = 'bottom';

			      this.data.datasets.forEach(function (dataset) {

			        for (var i = 0; i < dataset.data.length; i++) {
			          var model = dataset._meta[Object.keys(dataset._meta)[0]].data[i]._model,
			              total = dataset._meta[Object.keys(dataset._meta)[0]].total,
			              mid_radius = model.innerRadius + (model.outerRadius - model.innerRadius)/2,
			              start_angle = model.startAngle,
			              end_angle = model.endAngle,
			              mid_angle = start_angle + (end_angle - start_angle)/2;

			          var x = mid_radius * Math.cos(mid_angle);
			          var y = mid_radius * Math.sin(mid_angle);

			          ctx.fillStyle = '#fff';
			          if (i == 3){ // Darker text color for lighter background
			            ctx.fillStyle = '#444';
			          }
			          var percent = String(Math.round(dataset.data[i]/total*100)) + "%";
			          // Display percent in another line, line break doesn't work for fillText
			          ctx.fillText(percent, model.x + x, model.y + y + 15);
			        }
			      });               
			    }
			  }
			};
	var myPieChart = new Chart(PIECHART, {
		type : 'pie',
		options: pieOptions,
		data : {
			labels : [ "Submitted", "Not submitted"
			],
			datasets : [ {
				data : [ 90, 10
				],
				borderWidth : [ 1, 1
				],
				backgroundColor : [  "rgba(36, 166, 71, 0.8)","rgba(219, 53, 68, 0.8)"
				],
				hoverBackgroundColor : [ "rgba(36, 166, 71, 1)","rgba(219, 53, 68, 1)"
				]
			}
			]
		}
	});

});
