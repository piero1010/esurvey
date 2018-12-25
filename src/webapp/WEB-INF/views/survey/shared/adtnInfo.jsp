<div class="form-group row hideForPartipipant mb-0">
	<div class="col-sm-12">
		<button type="button" id="createAdtnInfoButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
			<i class="fas fa-plus-square"></i> Add
		</button>
		<button type="button" id="editAdtnInfoButton" disabled="true" class="btn btn-primary btn-sm" style="margin-right: 10px;">
			<i class="fas fa-edit"></i> Edit
		</button>
		<button type="button" id="deleteAdtnInfoButton" disabled="true" class="btn btn-danger btn-sm " style="margin-right: 10px;">
			<i class="fas fa-trash"></i> Delete
		</button>
	</div>
</div>
<table id="adtnInfoTable" class="table-hover table table-sm table-striped table-bordered" style="width: 100%">
	<thead>
		<tr>
			<th class="dt-body-center">ID</th> 
			<th class="dt-body-center">Code</th>
			<th class="dt-body-center">Description</th>
		</tr>
	</thead>
</table>
<div id="adtnInfoModal" class="modal" tabindex="-1" role="dialog" aria-hidden="false">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Create Additional Information</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<input type="hidden" id="adtnInfoId" />
				<div class="form-group row">
					<form:label path="" class="col-sm-3 col-form-label col-form-label-sm">Code*</form:label>
					<div class="col-sm-9">
						<input type="text" id="adtnInfoName" class="form-control form-control-sm typeahead" />
					</div>
				</div>
				<div class="form-group row">
					<form:label path="" class="col-sm-3 col-form-label col-form-label-sm">Description</form:label>
					<div class="col-sm-9">
						<input type="text" id="adtnInfoDesc" class="form-control form-control-sm typeahead" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="adtnInfoModalAddButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
					<i class="fas fa-plus"></i> Create
				</button>
				<button type="button" id="adtnInfoModalUpdateButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
					<i class="fas fa-edit"></i> Update
				</button>
				
				<button type="button" id="adtnInfoModalCloseButton" class="btn btn-secondary btn-sm" data-dismiss="modal">
					<i class="fas fa-window-close"></i> Close
				</button>
			</div>
		</div>
	</div>
</div>
<script>
	var adtnInfoTable = null;
	$(window).on('load', function() {		
		$("#createAdtnInfoButton").click(function() {
			$("#adtnInfoModal .modal-title").text("Create Additional Information");
			$("#adtnInfoModalAddButton").show();
			$("#adtnInfoModalUpdateButton").hide();
			$("#adtnInfoId").val("");
			$("#adtnInfoName").prop("disabled", false);
			$("#adtnInfoName").val("");
			$("#adtnInfoDesc").val("");
			$('#adtnInfoModal').modal({
				backdrop : 'static',
				keyboard : false
			});
		});
		
		$("#editAdtnInfoButton").click(function() {
			var idx = adtnInfoTable.cell('.selected', 0).index();
			var d = adtnInfoTable.row( idx.row ).data();
			editButtonClicked(d);
		});
		
		$("#deleteAdtnInfoButton").click(function() {
			var idx = adtnInfoTable.cell('.selected', 0).index();
			var d = adtnInfoTable.row( idx.row ).data();
			confirmModal("System message","Do you want to delete the Additional Information?",function(){
				$.ajax({
					  type: "POST",
					  url: "${basePath}/survey/deleteAdtnInfo",
					  data: {adtnInfoId:d[0],srvyRecId:$("#srvyRecId").val()},
					  success: function(data){
						data=jQuery.parseJSON(data);
						if(data.success=="true"){
							$('#adtnInfoModal').modal("hide");
							adtnInfoTable.draw();
						}
						modal("System message",data.message,function(){
							
						});					
					  	$("#createButton").prop("disabled", false);
					  },error: function(data){
						modal("System message","System is busy, please try again.");
					  	$("#createButton").prop("disabled", false);
					  }, timeout: 10000    
					});
			},function(){
				
			});
		});
		
		$("#adtnInfoModalAddButton").click(function() {
			$("#adtnInfoModalAddButton").prop("disabled", true);
			$.ajax({
			  type: "POST",
			  url: "${basePath}/survey/addAdtnInfo",
			  data: {adtnInfoName:$("#adtnInfoName").val(),adtnInfoDesc:$("#adtnInfoDesc").val(),srvyRecId:$("#srvyRecId").val()},
			  success: function(data){
				data=jQuery.parseJSON(data);
				if(data.success=="true"){
					$('#adtnInfoModal').modal("hide");
					adtnInfoTable.draw();
				}
				modal("System message",data.message,function(){
					
				});					
			  	$("#adtnInfoModalAddButton").prop("disabled", false);
			  },error: function(data){
				modal("System message","System is busy, please try again.");
			  	$("#adtnInfoModalAddButton").prop("disabled", false);
			  }, timeout: 10000    
			});
		});
		
		$("#adtnInfoModalUpdateButton").click(function() {
			$("#adtnInfoModalUpdateButton").prop("disabled", true);
			$.ajax({
			  type: "POST",
			  url: "${basePath}/survey/editAdtnInfo",
			  data: {adtnInfoId: $("#adtnInfoId").val(),adtnInfoDesc:$("#adtnInfoDesc").val()},
			  success: function(data){
				data=jQuery.parseJSON(data);
				if(data.success=="true"){
					$('#adtnInfoModal').modal("hide");
					adtnInfoTable.draw();
				}
				modal("System message",data.message,function(){
					
				});					
			  	$("#adtnInfoModalUpdateButton").prop("disabled", false);
			  },error: function(data){
				modal("System message","System is busy, please try again.");
			  	$("#adtnInfoModalUpdateButton").prop("disabled", false);
			  }, timeout: 10000    
			});
		});
		
	});
	
	function editButtonClicked(d){
		$("#adtnInfoModal .modal-title").text("Edit Additional Information");		
		$("#adtnInfoModalAddButton").hide();
		$("#adtnInfoModalUpdateButton").show();
		$("#adtnInfoId").val(d[0]);
		$("#adtnInfoName").prop("disabled", true);
		$("#adtnInfoName").val(d[1]);
		$("#adtnInfoDesc").val(d[2]);
		$('#adtnInfoModal').modal({
			backdrop : 'static',
			keyboard : false
		});
	}
	
	function initialAdtnInfoTable(){
		adtnInfoTable = $('#adtnInfoTable').DataTable({
			"ajax": {
			    "url": "${basePath}/survey/listAdtnInfo/",
			    "type": "POST",
			    "data": function(d) {
			    	d.srvyRecId = $("#srvyRecId").val();
				}
			},
			"columns": [{"visible": false},null,null],
		    "serverSide" : true,
			"processing": true, 
			"destroy" : true,
			"pagingType": "full_numbers",
		    "bPaginate": true,
		    "bLengthChange": false,
	        "scrollCollapse": true,
		    "pageLength": 500,
		    "bFilter": false,
		    "bInfo": true,
		    "bAutoWidth": true});
		
		$('#adtnInfoTable tbody').on( 'click', 'tr', function () {			
			if ($(this).hasClass('selected') ) {
				$('#adtnInfoTable tbody tr').removeClass('selected');
	            $("#editAdtnInfoButton").prop("disabled", true);
	            $("#deleteAdtnInfoButton").prop("disabled", true);
	        } else {
	        	$('#adtnInfoTable tbody tr').removeClass('selected');
	        	if (adtnInfoTable.data().count()) {
	            	$(this).addClass('selected');
	            	$("#editAdtnInfoButton").prop("disabled", false);
	            	$("#deleteAdtnInfoButton").prop("disabled", false);
	        	}
	        }
	    });

		$("#adtnInfoTable tbody").on("dblclick", "tr", function () {
			var d = adtnInfoTable.row(this).data();
			editButtonClicked(d);
		});
		
		adtnInfoTable.on( 'draw', function () {
			$("#deleteAdtnInfoButton").prop("disabled", true);
			$("#editAdtnInfoButton").prop("disabled", true);
		});	
	}
	
</script>