<div class="form-group row">
	<form:label path="srvyRecId" class="col-sm-2 col-form-label col-form-label-sm">Survey ID</form:label>
	<div class="col-sm-4">
		<form:input path="srvyRecId" readonly="true" class="form-control form-control-sm" />
	</div>
	<form:label path="creDate" class="col-sm-2 col-form-label col-form-label-sm">Create Date</form:label>
	<div class="col-sm-4">
		<form:input path="creDate" readonly="true" class="form-control form-control-sm" />
	</div>
</div>
<div class="form-group row">
	<form:label path="coor.userId" class="col-sm-2 col-form-label col-form-label-sm">Coordinator</form:label>
	<div class="col-sm-4">
		<form:input path="coor.userId" readonly="true" class="form-control form-control-sm" />
	</div>
	<form:label path="srvyYear" class="col-sm-2 col-form-label col-form-label-sm">Year*</form:label>
	<div class="col-sm-4">
		<form:input path="srvyYear" class="form-control form-control-sm" placeholder="yyyy" />
	</div>
</div>
<div class="form-group row">
	<form:label path="srvyTmpl.srvyTmplId" class="col-sm-2 col-form-label col-form-label-sm">Template Name*</form:label>
	<div class="col-sm-4">
		<form:select path="srvyTmpl.srvyTmplId" class="form-control form-control-sm">
			<form:options items="${srvyTmplHashMap}" />
		</form:select>
	</div>
	<form:label path="srvyTtl" class="col-sm-2 col-form-label col-form-label-sm">Survey Title*</form:label>
	<div class="col-sm-4">
		<form:input path="srvyTtl" class="form-control form-control-sm" />
	</div>
</div>
<div class="form-group row">
	<form:label path="srvySts.srvyStsId" class="col-sm-2 col-form-label col-form-label-sm">Status</form:label>
	<div class="col-sm-4">
		<form:select path="srvySts.srvyStsId" class="form-control form-control-sm">
			<form:options items="${srvyStsHashMap}" />
		</form:select>
	</div>
	<form:label path="autoRmdrDate" class="col-sm-2 col-form-label col-form-label-sm">Auto Reminder Date</form:label>
	<div class="col-sm-4">
		<form:input path="autoRmdrDate" class="form-control form-control-sm" placeholder="dd-mm-yyyy" />
	</div>
</div>
<div class="form-group row">
	<form:label path="bgnDate" class="col-sm-2 col-form-label col-form-label-sm">Start Date*</form:label>
	<div class="col-sm-4">
		<form:input path="bgnDate" class="form-control form-control-sm" placeholder="dd-mm-yyyy" />
	</div>
	<form:label path="endDate" class="col-sm-2 col-form-label col-form-label-sm">End Date*</form:label>
	<div class="col-sm-4">
		<form:input path="endDate" class="form-control form-control-sm" placeholder="dd-mm-yyyy" />
	</div>
</div>
<div class="form-group row">
	<form:label path="srvyPptCatg.srvyPptCatgId" class="col-sm-2 col-form-label col-form-label-sm">Participant Category*</form:label>
	<div class="col-sm-4">
		<form:select path="srvyPptCatg.srvyPptCatgId" class="form-control form-control-sm">
			<form:options items="${pptCatgHashMap}" />
		</form:select>
	</div>
	<form:label path="pptDivCode" class="col-sm-2 col-form-label col-form-label-sm">Division of Participants*</form:label>
	<div class="col-sm-4">
		<form:select path="pptDivCode" class="form-control form-control-sm">
			<form:options items="${pptDivCodeHashMap}" />
		</form:select>
	</div>
</div>
<div class="form-group row">
	<form:label path="srvyRmk" class="col-sm-2 col-form-label col-form-label-sm">Survey Remark</form:label>
	<div class="col-sm-10">
		<form:input path="srvyRmk" class="form-control form-control-sm" />
	</div>
</div>
<div class="form-group row">
	<form:label path="numOfPtcl" class="col-sm-2 col-form-label col-form-label-sm">No. of Participants</form:label>
	<div class="col-sm-4">
		<form:input path="numOfPtcl" class="form-control form-control-sm" readonly="true" />
	</div>
	<form:label path="numOfRespd" class="col-sm-2 col-form-label col-form-label-sm">No. of Respondents</form:label>
	<div class="col-sm-4">
		<form:input path="numOfRespd" class="form-control form-control-sm" readonly="true" />
	</div>
</div>
<script>
	$(document).ready(function() {
		$("#clearButton").click(function() {
			$("#clearButton").prop("disabled", true);
			confirmModal("System message","Are you sure to clear Trial-Run Data?",function(){
				$.ajax({
				  type: "POST",
				  url: "${basePath}/survey/clearTrialRun",
				  data: {srvyRecId:$("#srvyRecId").val()},
				  success: function(data){
						data=jQuery.parseJSON(data);
						modal("System message",data.message,function(){
							$.redirect('${basePath}/survey/modify/', {'id': $("#srvyRecId").val()});
						});
						$("#clearButton").prop("disabled", false);
				  },error: function(data){
						modal("System message","System is busy, please try again.");
				  		$("#clearButton").prop("disabled", false);
				  }, timeout: 10000
				});
			},function(){
				$("#clearButton").prop("disabled", false);
			});
		});
	});
	
	$("#autoRmdrDate").change(function(){
		modal("System message","The \"Auto-Reminder Date\" was set, please review the email contents of reminder !");
	})
</script>