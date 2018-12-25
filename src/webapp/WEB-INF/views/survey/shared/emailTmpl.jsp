<div class="form-group row hideForPartipipant mb-10">
	<div class="col-sm-12">
		<button type="button" id="updateEmailTmplButton" class="btn btn-primary btn-sm" style="margin-right: 10px;">
			<i class="fas fa-edit"></i> Update
		</button>
		<button class="btn btn-info btn-sm dropdown-toggle" type="button" id="dropdowButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Insert Custom Data
		</button>
		<div class="dropdown-menu" aria-labelledby="dropdowButton">
			<a class="dropdown-item" onclick="dropdowButton(1)" href="#">#PARTICIPANT_NAME#</a>
			<a class="dropdown-item" onclick="dropdowButton(2)" href="#">#PARTICIPANT_DESIGNATION#</a>
			<a class="dropdown-item" onclick="dropdowButton(3)" href="#">#PARTICIPANT_DIVISION#</a>
			<a class="dropdown-item" onclick="dropdowButton(4)" href="#">#SURVEY_URL#</a>
			<a class="dropdown-item" onclick="dropdowButton(5)" href="#">#SURVEY_TITLE#</a>
		</div>
		<c:if test="${not empty sessionScope.SSN_USER_EMAIL}">
			<button type="button" id="testEmailTmplButton" class="btn btn-warning btn-sm float-right" >
				<i class="fas fa-envelope"></i> Send Test Email 
			</button>
		</c:if>
	</div>
</div>
<form:hidden path="rmdrTmpl.srvyEmailTmplId" />
<form:hidden path="rmdrTmpl.emailType" />
<form:hidden path="rmdrTmpl.emailSubj" />
<form:hidden path="rmdrTmpl.emailCntn" />
<form:hidden path="invtTmpl.srvyEmailTmplId" />
<form:hidden path="invtTmpl.emailType" />
<form:hidden path="invtTmpl.emailSubj" />
<form:hidden path="invtTmpl.emailCntn" />
<div class="form-group row">
	<form:label path="tmpTmpl.emailType" class="col-sm-2 col-form-label col-form-label-sm">Template Type</form:label>
	<div class="col-sm-10">
		<form:select path="tmpTmpl.emailType" class="form-control form-control-sm">
			<form:options items="${emailTypeHashMap}" />
		</form:select>
	</div>
</div>
<div class="form-group row">
	<form:label path="tmpTmpl.emailSubj" class="col-sm-2 col-form-label col-form-label-sm">Subject*</form:label>
	<div class="col-sm-10">
		<form:input path="tmpTmpl.emailSubj" class="form-control form-control-sm" />
	</div>
</div>
<div class="form-group row">
	<div class="col-sm-12">
		<form:textarea path="tmpTmpl.emailCntn" rows="5" cols="30" />
	</div>
</div>
<script>
var editor = null;
function dropdowButton(index){
	try{
		pasteHTMLAtCaret(" ");
		var sel = window.getSelection();
		if(sel.anchorNode.parentNode.getAttribute("class").indexOf("richText-editor") != -1){
			if(index==1){
				pasteHTMLAtCaret("#PARTICIPANT_NAME#");
			}else if(index==2){
				pasteHTMLAtCaret("#PARTICIPANT_DESIGNATION#");
			}else if(index==3){
				pasteHTMLAtCaret("#PARTICIPANT_DIVISION#");
			}else if(index==4){
				pasteHTMLAtCaret("#SURVEY_URL#");
			}else{
				pasteHTMLAtCaret("#SURVEY_TITLE#");	
			}
		}
		pasteHTMLAtCaret(" ");
	}catch(ex){}
}

function pasteHTMLAtCaret(html) {
    // add HTML code for Internet Explorer
    var sel, range;
    if (window.getSelection) {
        // IE9 and non-IE
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            range.deleteContents();

            // Range.createContextualFragment() would be useful here but is
            // only relatively recently standardized and is not supported in
            // some browsers (IE9, for one)
            var el = document.createElement("div");
            el.innerHTML = html;
            var frag = document.createDocumentFragment(), node, lastNode;
            while ( (node = el.firstChild) ) {
                lastNode = frag.appendChild(node);
            }
            range.insertNode(frag);

            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    } else if (document.selection && document.selection.type !== "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(html);
    }
}

function changeEmailTmplType(type){
	console.log(type);
	if(type=="I"){
		$("#tmpTmpl\\.emailSubj").val($("#invtTmpl\\.emailSubj").val());
		$("#tmpTmpl\\.emailCntn").val($("#invtTmpl\\.emailCntn").val());
	}else{
		console.log($("#rmdrTmpl\\.emailCntn").val());
		$("#tmpTmpl\\.emailSubj").val($("#rmdrTmpl\\.emailSubj").val());
		$("#tmpTmpl\\.emailCntn").val($("#rmdrTmpl\\.emailCntn").val());		
	}
	try{document.getElementById("richText").innerHTML=$("#tmpTmpl\\.emailCntn").val();}catch(ex){}
}

$(window).on('load', function() {
	changeEmailTmplType("I");
	(function ($, undefined) {
	    $.fn.getCursorPosition = function() {
	        var el = $(this).get(0);
	        var pos = 0;
	        if('selectionStart' in el) {
	            pos = el.selectionStart;
	        } else if('selection' in document) {
	            el.focus();
	            var Sel = document.selection.createRange();
	            var SelLength = document.selection.createRange().text.length;
	            Sel.moveStart('character', -el.value.length);
	            pos = Sel.text.length - SelLength;
	        }
	        return pos;
	    }
	})(jQuery);
	
	$('textarea').richText();
	
	$("#tmpTmpl\\.emailType").change(function() {
		changeEmailTmplType($(this).val())
	});
	
	$("#updateEmailTmplButton").click(function() {
		$("#updateEmailTmplButton").prop("disabled", true);
		$.ajax({
			  type: "POST",
			  url: "${basePath}/survey/updateEmailTmpl",
			  data: {
				  srvyRecId:$("#srvyRecId").val(),
				  emailType:$("#tmpTmpl\\.emailType").val(),			  
				  emailSubj:$("#tmpTmpl\\.emailSubj").val(),
				  emailCntn:$("#tmpTmpl\\.emailCntn").val()
			  },
			  success: function(data){
				data=jQuery.parseJSON(data);
				modal("System message",data.message,function(){
					$("#updateEmailTmplButton").prop("disabled", false);
				});
			  },error: function(data){
				modal("System message","System is busy, please try again.");
			  	$("#updateEmailTmplButton").prop("disabled", false);
			  }, timeout: 10000    
			});
	});
	
	$("#testEmailTmplButton").click(function() {
		$("#testEmailTmplButton").prop("disabled", true);
		$.ajax({
			  type: "POST",
			  url: "${basePath}/testEmailTmpl",
			  data: {
				  srvyRecId:$("#srvyRecId").val(),
				  emailType:$("#tmpTmpl\\.emailType").val(),			  
				  emailSubj:$("#tmpTmpl\\.emailSubj").val(),
				  emailCntn:$("#tmpTmpl\\.emailCntn").val()
			  },
			  success: function(data){
				data=jQuery.parseJSON(data);
				modal("System message",data.message,function(){
					$("#testEmailTmplButton").prop("disabled", false);
				});
			  },error: function(data){
				modal("System message","System is busy, please try again.");
			  	$("#testEmailTmplButton").prop("disabled", false);
			  }, timeout: 10000    
			});
	});
});

</script>