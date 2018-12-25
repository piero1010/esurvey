<div class="form-group row hideForPartipipant mb-0">
	<div class="col-sm-12">
		<button type="button" id="addParticipantButton" class="float-left btn btn-primary btn-sm hideForParticipant hideForBulkAddPpt" style="margin-right: 10px;">
			<i class="fas fa-user-plus"></i> Add Participant
		</button>
		<button type="button" id="bulkAddPptButton" class="float-left btn btn-primary btn-sm hideForParticipant hideForBulkAddPpt" style="margin-right: 10px;">
			<i class="fas fa-user-plus"></i> Bulk Add Participant
		</button>
		<button type="button" id="deleteParticipantButton" disabled="true" class="float-left btn btn-danger btn-sm" style="margin-right: 10px;">
			<i class="fas fa-user-times"></i> Delete Participant
		</button>
		<div class="btn-group dropleft float-right">
		  <button class="btn btn-warning btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    <i class="fas fa-envelope"></i> Send Email
		  </button>
		  <div class="dropdown-menu">
		     <button class="dropdown-item" id="sendInvitationToSelected" type="button">Send Invitation To Selected Participants</button>
    		 <button class="dropdown-item" id="sendReminderToSelected" type="button">Send Reminder To Selected Participants</button>
    		 <button class="dropdown-item" id="sendInvitationToAll" type="button">Send Invitation To All Participants</button>
    		 <button class="dropdown-item" id="sendReminderToAll" type="button">Send Reminder To All Participants</button>
		  </div>
		</div>
	</div>
</div>
<div class="form-group row">
	<div class="col-sm-12">
		<table id="participantTable" class="table-hover table table-sm table-striped table-borderejd" style="width: 100%">
			<thead>
				<tr>
					<th class="dt-body-center sorting_disabled" style="padding: 0;"><input value="" id="selectAllParticipant" type="checkbox" /></th>
					<th>Division</th>
					<th>Name</th>
					<th>Designation</th>
					<th>Email Address</th>
					<th class="dt-body-center">Invitation Sent Date</th>
					<th class="dt-body-center">Last Reminder Date</th>
					<th class="dt-body-center">Submission Date</th>
					<th class="dt-body-center">Respond Status</th>
					<th class="dt-body-center">HousingId</th>
					<th class="dt-body-center">id</th>
					<th>IT Service Name</th>
					<th>IT Service Code</th>
					<th>Name of Contractor</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script>
var participantTable=null;

function changeDeleteParticipantButtonStatus(obj){
	if(obj!=null){
		if($(obj).is(':checked')){
			$(obj).closest('tr').addClass('selected text-white');
		}else{
			$(obj).closest('tr').removeClass('selected text-white');
		}
	}
	if($('#participantTable input[type=checkbox]:checked').length) {
		$("#deleteParticipantButton").prop("disabled", false);				
	}else{
		$("#deleteParticipantButton").prop("disabled", true);
	}
}	

$("#deleteParticipantButton").click(function() {
	confirmModal("System Message","Are you sure you want to delete the selected participants?",function(){
		if(called==false){
			called=true;
			var allVals = [];
			$("#participantTable input:checkbox:checked").each(function(){
				if($(this).val()!=""){
					allVals.push($(this).val());
				}
			});
			$.ajax({
				type: "POST",
				url: "${basePath}/survey/deleteParticipant",
				data: {srvyPptIds:"'"+allVals.join("','")+"'",srvyRecId:$("#srvyRecId").val()},
				async: false, 
				success: function(data){
					modal("System message","Survey participant was deleted.");
					try{userTable.draw();}catch(ex){}
					participantTable.draw();
					called=false;
				},error: function(data){
					modal("System message","System is busy, please try again.");
					$("#deleteParticipantButton").prop("disabled", false);
					called=false;
				}, timeout: 10000
			});
		}
	});
});
$("#addParticipantButton").click(function() {
	$(".hideForParticipant").css("display","none");
	$("#userSearchingCard").slideDown(400, function() {
		if(userTable==null){
			initialUserTable();
		}else{
			$("#clearUserTable").val("true");
			userTable.draw();
		}
	});
});
$("#bulkAddPptButton").click(function() {
	$(".hideForBulkAddPpt").css("display","none");
	$("#bulkAddPptCard").slideDown();
});
$("#sendInvitationToSelected").click(function(event) {
	$("#sendInvitationToSelected").prop("disabled", true);
	if($('#participantTable input[type=checkbox]:checked').length) {
		confirmModal("System Message","Are you sure you want to send invitation to selected participants?",function(){
			if(called==false){
				called=true;
				var allVals = [];
				$("#participantTable input:checkbox:checked").each(function(){
					if($(this).val()!=""){
						allVals.push($(this).val());
					}
				});
				$.ajax({
				  type: "POST",
				  url: "${basePath}/mail/sendInvitationToSelected",
				  data: {srvyPptIds:"'"+allVals.join("','")+"'",srvyRecId:$("#srvyRecId").val()},
				  success: function(data){
					  	called=false;
						data=jQuery.parseJSON(data);
						modal("System message",data.message);
						$("#sendInvitationToSelected").prop("disabled", false);
				  },error: function(data){
					    called=false;
						modal("System message","System is busy, please try again.");
						$("#sendInvitationToSelected").prop("disabled", false);
				  }, timeout: 10000
				});
			}
		});
	}else{
		modal("System Message","Please select a participant.");
		$("#sendInvitationToSelected").prop("disabled", false);
	}
});

$("#sendReminderToSelected").click(function(event) {
	$("#sendInvitationToSelected").prop("disabled", true);
	if($('#participantTable input[type=checkbox]:checked').length) {
		confirmModal("System Message","Are you sure you want to send reminder to selected participants who haven't response?",function(){
			if(called==false){
				called=true;
				var allVals = [];
				$("#participantTable input:checkbox:checked").each(function(){
					if($(this).val()!=""){
						allVals.push($(this).val());
					}
				});
				$.ajax({
				  type: "POST",
				  url: "${basePath}/mail/sendReminderToSelected",
				  data: {srvyPptIds:"'"+allVals.join("','")+"'",srvyRecId:$("#srvyRecId").val()},
				  success: function(data){
					    called=false;
						data=jQuery.parseJSON(data);
						modal("System message",data.message);
						$("#sendReminderToSelected").prop("disabled", false);
				  },error: function(data){
					    called=false;
						modal("System message","System is busy, please try again.");
						$("#sendReminderToSelected").prop("disabled", false);
				  }, timeout: 10000
				});
			}
		});
	}else{
		modal("System Message","Please select a participant.");
		$("#sendReminderToSelected").prop("disabled", false);
	}
});


$("#sendInvitationToAll").click(function(event) {
	if(!participantTable.data().count()) {
		modal("System message","Please add a participant to survey.");
		return;
	}
	confirmModal("System Message","Are you sure you want to send invitation to all participants?",function(){
		if(called==false){
			called=true;
			$.ajax({
			  type: "POST",
			  url: "${basePath}/mail/sendInvitationToAll",
			  data: {srvyRecId:$("#srvyRecId").val()},
			  success: function(data){
					data=jQuery.parseJSON(data);
					modal("System message",data.message);
					called=false;
			  },error: function(data){
					modal("System message","System is busy, please try again.");
					called=false;
			  }, timeout: 10000
			});
		}
	});
});

$("#sendReminderToAll").click(function(event) {
	if(!participantTable.data().count()) {
		modal("System message","Please add a participant to survey.");
		return;
	}
	confirmModal("System Message","Are you sure you want to send reminder to all participants who haven't response?",function(){
		if(called==false){
			called=true;
			$.ajax({
			  type: "POST",
			  url: "${basePath}/mail/sendReminderToAll",
			  data: {srvyRecId:$("#srvyRecId").val()},
			  success: function(data){
				   called=false;
				    data=jQuery.parseJSON(data);
					modal("System message",data.message);
			  },error: function(data){
				    called=false;
					modal("System message","System is busy, please try again.");
			  }, timeout: 10000
			});
		}
	});
});
$("#selectAllParticipant").change(function(){
	if($('#selectAllParticipant').is(':checked')){
		$("#participantTable tr").addClass('selected text-white');
	}else{
		$("#participantTable tr").removeClass('selected text-white');
	}
	$('#participantTable input:checkbox').prop('checked', $('#selectAllParticipant').is(':checked'));
	changeDeleteParticipantButtonStatus(null);
});
function initialParticipantTable(){
	participantTable = $('#participantTable').DataTable({
		"ajax": {
	    "url": "${basePath}/survey/listParticipant/",
	    "type": "POST",
	    "data": function(d) {
	    	d.filter = $('#srvyRecForm').serializeArray();
	    }
	},
	"columns": [null,null,null,null,null,null,null,null,null,{"visible": false},{"visible": false},null,null,null],
	"columnDefs": [{
         "targets": 0, 
         "orderable": false,
         "className": "dt-body-center ",
         "render": function (data, type, full, meta){
             return '<input onclick="changeDeleteParticipantButtonStatus(this)" type="checkbox" name="id[]" value="' + full[10] + '">';
         }
     }],"order": [[1, 'asc']],
     "createdRow": function ( row, data, index ) {
        if ( data[4]=="") {
            $('td', row).eq(0).addClass('highlight');
            $('td', row).eq(1).addClass('highlight');
            $('td', row).eq(2).addClass('highlight');
            $('td', row).eq(3).addClass('highlight');
            $('td', row).eq(4).addClass('highlight');
            $('td', row).eq(5).addClass('highlight');
            $('td', row).eq(6).addClass('highlight');
            $('td', row).eq(7).addClass('highlight');
            $('td', row).eq(8).addClass('highlight');
            $('td', row).eq(9).addClass('highlight');
            $('td', row).eq(10).addClass('highlight');
            $('td', row).eq(11).addClass('highlight');
        }
        $('td', row).eq(1).addClass('dt-body-center');
        $('td', row).eq(5).addClass('dt-body-center');
        $('td', row).eq(6).addClass('dt-body-center');
        $('td', row).eq(7).addClass('dt-body-center');
        $('td', row).eq(8).addClass('dt-body-center');
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
		
	participantTable.on( 'draw', function () {
		$('#selectAllParticipant').prop('checked', false);
		$("#deleteParticipantButton").prop("disabled", true);
		
	});
	
	$('#participantTable tbody').on('click', 'tr', function () {
		if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });
}
</script>