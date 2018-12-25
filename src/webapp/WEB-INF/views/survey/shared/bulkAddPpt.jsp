<div class="card border-primary" id="bulkAddPptCard" style="display: none;">
	<div class="card-header bg-primary text-white" style="padding: 5px !important;">Bulk Add Participant</div>
	<div class="card-body">

		<div class="form-group row">
			<div class="col-sm-12">
				<input id="excelFile" type="file" name="excelFile" />
			</div>
		</div>

		<div class="form-group row ">
			<div class="col-sm-12">
				<button type="button" id="cancelButton" class="btn btn-seciondary btn-sm" style="margin-right: 10px;">
					<i class="fas fa-window-close"></i> Cancel
				</button>
				<button type="button" class="btn btn-primary btn-sm" id="uploadButton" style="margin-right: 10px;">
					<i class="fas fa-user-plus"></i> Upload
				</button>
				<br /> <br /> <a href="${pageContext.request.contextPath}/resources/Bulk_Add_Participant_Template.xlsx">Upload File Template</a>
			</div>
		</div>

		<div class="form-group row">
			<div class="col-sm-12">
				<!-- Alert -->
				<div id="returnMsg" style="color: black; font-size: 18px;"></div>
				<!-- 				<h1>Upload Status</h1> -->
				<%-- 				<h2>Message : ${message}</h2> --%>
			</div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery.fileupload.js"></script>

<script>
	$("#cancelButton")
			.click(
					function() {
						$("#excelFile").val("");
						$('#returnMsg').text("");
						$("#file").attr('disabled', false);
						$('#progress .progress-bar').css('width', '0%');
						$(".hideForBulkAddPpt").css("display", "");
						$("#bulkAddPptCard").hide();
						if($('#srvyPptCatg\\.srvyPptCatgId').val()=='3'){
							$("#addParticipantButton").css("display", "none");
							$("#bulkAddPptButton").css("display", "");
						} else {
							$("#bulkAddPptButton").css("display", "none");
							$("#addParticipantButton").css("display", "");
						}
					});
	$("#uploadButton").click(function() {
		if ($("#excelFile").prop("files")[0]) {
			var file_data = $("#excelFile").prop("files")[0]; // Getting the properties of file from file field
			var form_data = new FormData(); // Creating object of FormData class
			form_data.append("file", file_data); // Appending parameter named file with properties of file_field to form_data
			form_data.append("srvyRecId", $("#srvyRecId").val());

			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/survey/upload",
				data : form_data,
				processData : false,
				contentType : false,
				async : false,
				success : function(data) {
					data = jQuery.parseJSON(data);
					$("#excelFile").val("");
					$('#returnMsg').text(data.message);
					participantTable.draw();
				},
				error : function(data) {
					data = jQuery.parseJSON(data);
					$('#returnMsg').text(data.message);
				},
				timeout : 10000
			});
		} else {
			$('#returnMsg').text("Please select a Excel file to upload!");
		}
	});
</script>
