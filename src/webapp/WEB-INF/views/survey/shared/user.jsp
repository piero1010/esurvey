<div class="card border-primary" id="userSearchingCard" style="display:none;">
	<div class="card-header bg-primary text-white" style="padding: 5px !important;">User Searching</div>
	<div class="card-body">
		<div class="form-group row ">
			<div class="col-sm-12">
				<button type="button" id="addUserButton" class="btn btn-primary btn-sm" disabled="true" style="margin-right: 10px;">
					<i class="fas fa-edit"></i> Add User To Participant List
				</button>
				<button type="button" id="closeUserSearchingButton" class="btn btn-seciondary btn-sm" style="margin-right: 10px;">
					<i class="fas fa-window-close"></i> Close User Searching
				</button>
				<button type="button" id="searchUserButton" class="btn btn-primary btn-sm float-right">
					<i class="fas fa-search"></i> Search User
				</button>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 col-form-label col-form-label-sm">Division</label>
			<div class="col-sm-4">
				<form:select path="" id="searchUserDivision" class="form-control form-control-sm">
					<form:options items="${pptDivCodeHashMap}" />
				</form:select>
			</div>
			<label class="col-sm-2 col-form-label col-form-label-sm">Rank</label>
			<div class="col-sm-4">
				<input type="text" id="searchUserRank" class="form-control form-control-sm typeahead" />
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 col-form-label col-form-label-sm">Designation</label>
			<div class="col-sm-4">
				<input type="text" id="searchUserDesignation" class="form-control form-control-sm" />
			</div>
			<label class="col-sm-2 col-form-label col-form-label-sm">Name</label>
			<div class="col-sm-4">
				<input type="text" id="searchUserName" class="form-control form-control-sm" />
			</div>
		</div>
		<div class="form-group row">
			<div class="col-sm-12">
				<table id="userTable" class="table-hover table table-sm table-striped table-borderejd" style="width: 100%">
					<thead>
						<tr>
							<th class="dt-body-center sorting_disabled" style="padding: 0;"><input id="selectAllUser" value="" type="checkbox" /></th>
							<th>Division</th>
							<th>Rank</th>
							<th>Designation</th>
							<th>Name</th>
							<th>HousingId</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<script>

var countries_suggestion = new Bloodhound({
	datumTokenizer: Bloodhound.tokenizers.whitespace,
	queryTokenizer: Bloodhound.tokenizers.whitespace,
	local: [${rankList}]
});

$('.typeahead').typeahead(
	{ minLength: 1 },
	{ source: countries_suggestion }
);

var userTable = null;
$("#selectAllUser").change(function(){
	if($('#selectAllUser').is(':checked') && userTable.data().count()) {
		$("#userTable tr").addClass('selected');
	}else{
		$("#userTable tr").removeClass('selected');
	}
	$('#userTable input:checkbox').prop('checked', $('#selectAllUser').is(':checked'));
	changeAddUserButtonStatus(null);
});
$("#searchUserButton").click(function() {
	$("#searchUserButton").prop("disabled", true);
	userTable.draw();
});
$("#addUserButton").click(function() {
	confirmModal("System Message","Are you sure you want to add the selected users to participant list?",function(){
		if(called==false){
			called=true;
			var allVals = [];
			$("#userTable input:checkbox:checked").each(function(){
				if($(this).val()!=""){
					allVals.push($(this).val());
				}
			});
			$.ajax({
				type: "POST",
				url: "${basePath}/survey/addParticipant",
				data: {users:"'"+allVals.join("','")+"'",srvyRecId:$("#srvyRecId").val()},
				async: false, 
				success: function(data){
					modal("System message","Survey participant was added.");
					userTable.draw();
					participantTable.draw();
					called=false;
				},error: function(data){
					modal("System message","System is busy, please try again.");
				  	$("#addUserButton").prop("disabled", false);
				  	called=false;
				}, timeout: 10000
			});
		}
	});
});
$("#closeUserSearchingButton").click(function() {
	$("#clearUserTable").val("true");
	userTable.draw();
	$(".hideForParticipant").css("display","");
	$("#userSearchingCard").hide();
	if($('#srvyPptCatg\\.srvyPptCatgId').val()=='3'){
		$("#addParticipantButton").css("display","none");
		$("#bulkAddPptButton").css("display","");
	}else{
		$("#bulkAddPptButton").css("display","none");
		$("#addParticipantButton").css("display","");
	}
});
function changeAddUserButtonStatus(obj){
	if(obj!=null){
		if($(obj).is(':checked')){
			$(obj).closest('tr').addClass('selected');
		}else{
			$(obj).closest('tr').removeClass('selected');
		}
	}
	if($('#userTable input[type=checkbox]:checked').length && userTable.data().count()) {
		$("#addUserButton").prop("disabled", false);				
	}else{
		$("#addUserButton").prop("disabled", true);
	}
}
function initialUserTable(){
	userTable = $('#userTable').DataTable({
		"ajax": {
		    "url": "${basePath}/user/listUser/",
		    "type": "POST",
		    "data": function(d) {
		    	d.searchUserDivision = $("#searchUserDivision").val();
		    	d.searchUserRank = $("#searchUserRank").val();
		    	d.searchUserDesignation = $("#searchUserDesignation").val();
		    	d.searchUserName = $("#searchUserName").val();
		    	d.srvyRecId = $("#srvyRecId").val();
		    	d.clearUserTable = $("#clearUserTable").val();
			}
		},
		"columns": [null,null,null,null,null,{"visible": false}],
		"columnDefs": [{
	         "targets": 0, 
	         "orderable": false,
	         "className": "dt-body-center ",
	         "render": function (data, type, full, meta){
	             return '<input onclick="changeAddUserButtonStatus(this)" type="checkbox" name="id[]" value="' + full[5] + '">';
	         }
	     }],"order": [[1, 'asc'],[2, 'asc'],[3, 'asc']],
	    "deferLoading": 0,
	    "serverSide" : true,
		"processing": true, 
		"destroy" : true,
		"pagingType": "full_numbers",
	    "bPaginate": true,
	    "bLengthChange": false,
	    "scrollY":"30vh",
        "scrollCollapse": true,
	    "pageLength": 500,
	    "bFilter": false,
	    "bInfo": true,
	    "bAutoWidth": true});
	
	userTable.on( 'draw', function () {
		setTimeout(function(){$("#searchUserButton").prop("disabled", false);}, 500)
		$('#selectAllUser').prop('checked', false);
		$("#addUserButton").prop("disabled", true);
		$("#clearUserTable").val("false");
	});					
	
	$('#userTable tbody').on('click', 'tr', function () {
		if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });
}
</script>