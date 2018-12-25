<div class="form-group row mb-10">
	<div class="col-sm-12">		
		<button type="button" id="searchHistoryButton" class="btn btn-primary btn-sm float-right"><i class="fas fa-search"></i> Search</button> 
	</div>
</div>
<div class="form-group row">
	<label for="srvyEmailHist.emailSendDateFrom" class="col-sm-2 col-form-label col-form-label-sm">Send Date From</label>
	<div class="col-sm-4">
		<form:input path="srvyEmailHist.emailSendDateFrom" class="form-control form-control-sm" placeholder="dd-mm-yyyy"/>
	</div>
	<label for="srvyEmailHist.emailSendDateTo" class="col-sm-2 col-form-label col-form-label-sm">Send Date To</label>
	<div class="col-sm-4">
		<form:input path="srvyEmailHist.emailSendDateTo" class="form-control form-control-sm" placeholder="dd-mm-yyyy"/>
	</div>
</div>
<div class="form-group row">
	<form:label path="srvyEmailHist.srvyEmailType.srvyEmailTypeId" class="col-sm-2 col-form-label col-form-label-sm">Template Type</form:label>
	<div class="col-sm-4">
		<form:select path="srvyEmailHist.srvyEmailType.srvyEmailTypeId" class="form-control form-control-sm">
			<form:option value=""></form:option>
			<form:options items="${emailTypeHashMap}" />
		</form:select>
	</div>
	<form:label path="srvyEmailHist.emailTo" class="col-sm-2 col-form-label col-form-label-sm">Send To</form:label>
	<div class="col-sm-4">
		<form:input path="srvyEmailHist.emailTo" class="form-control form-control-sm" />
	</div>
</div>
<div class="form-group row">
	<form:label path="srvyEmailHist.srvyEmailSts.srvyEmailStsId" class="col-sm-2 col-form-label col-form-label-sm">Send Status</form:label>
	<div class="col-sm-4">
		<form:select path="srvyEmailHist.srvyEmailSts.srvyEmailStsId" class="form-control form-control-sm">
			<form:option value=""></form:option>
			<form:options items="${emailStsHashMap}" />
		</form:select>
	</div>
</div>
<div class="form-group row">	
	<div class="col-sm-12">													
		<table id="historyTable"  class="table-hover table table-sm table-striped table-bordered" style="width: 100%">
		   <thead>
		      <tr>
		         <th class="dt-body-center">Send Date</th>
		         <th class="dt-body-center">Email Type</th>
		         <th class="dt-body-center">Send To</th>
		         <th class="dt-body-center">Send Status</th>
		         <th class="dt-body-center">Try Count</th>
		      </tr>
		   </thead>
		</table>
	</div>
</div>
<script>
var historyTable = null;
$("#searchHistoryButton").click(function() {
	$("#searchHistoryButton").prop("disabled", true);
	historyTable.draw();
});
function initialHistoryTable(){
	historyTable = $('#historyTable').DataTable({
		"ajax": {
		    "url": "${basePath}/survey/listEmailHistory/",
		    "type": "POST",
		    "data": function(d) {
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
</script>