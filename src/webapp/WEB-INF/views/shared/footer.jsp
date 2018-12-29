<footer class="main-footer">
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-6">
				<p>e-Survey System</p>
			</div>
			<div class="col-sm-6 text-right"></div>
		</div>
	</div>
</footer>
<div class="modal fade" id="messageModal" role="dialog" style="z-index:1999;">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" style="padding:0 1rem 0 1rem;">
			</div>
			<div class="modal-footer">
				<button type="button" id="messageModalCloseButton" class="btn btn-secondary btn-sm"  data-dismiss="modal"><i class="fas fa-window-close"></i> Close</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="confirmModal" role="dialog" style="z-index:1999;">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" style="padding:0 1rem 0 1rem;">
			</div>
			<div class="modal-footer">
				<button type="button" id="confirmModalOkButton" class="btn btn-primary btn-sm"  data-dismiss="modal"><i class="fas fa-check"></i> OK</button>
				<button type="button" id="confirmModalCancelButton" class="btn btn-secondary btn-sm"  data-dismiss="modal"><i class="fas fa-ban"></i> Cancel</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="yesNoModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" style="padding:0 1rem 0 1rem;">
			</div>
			<div class="modal-footer">
				<button type="button" id="yesNoModalYesButton" class="btn btn-primary btn-sm"  data-dismiss="modal"><i class="fas fa-check"></i> YES</button>
				<button type="button" id="yesNoModalNoButton" class="btn btn-primary btn-sm"  data-dismiss="modal"><i class="fas fa-times"></i> NO</button>
				<button type="button" id="yesNoModalCencelButton" class="btn btn-secondary btn-sm"  data-dismiss="modal"><i class="fas fa-ban"></i> Cancel</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="timeoutModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Session Expire Warning</h4>
			</div>
			<div class="modal-body" style="padding:0 1rem 0 1rem;">
				<b>Your session will expire soon. Please click OK to extend the session, or any unsaved changes will be lost.</b><br/><br/>
				<center>Time Remaining: <span id="expiryTimer"></span></center>
				<br/>
			</div>
			<div class="modal-footer">
				<button type="button" id="timeoutModalOKButton" class="btn btn-primary btn-sm"  data-dismiss="modal"><i class="fas sync-alt"></i> OK</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="expiryModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Session Expired</h4>
			</div>
			<div class="modal-body" style="padding:0 1rem 0 1rem;">
				Your session has been expired. Please login again.
			</div>
			<div class="modal-footer">
				<button type="button" id="expiryModalLoginButton" class="btn btn-primary btn-sm"  data-dismiss="modal"><i class="fas fa-user"></i> Log in</button>
			</div>
		</div>
	</div>
</div>
<script>
var okCallback;
var canelCallback;
var yesCallback;
var noCallback;

var called= false;
String.prototype.endsWith = function(pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.lastIndexOf(pattern) === d;
};
var interval = null;
var interval2 = null;
function isActive(){
	$.ajax({
		type: "POST",
		url: "${basePath}/isActive",
	    xhrFields: {withCredentials: true},
		success: function(data){
			data=jQuery.parseJSON(data);
			if(data.message=="warning"){
				timeoutModal(data.timeLeft);
			}else if(data.message=="expiry"){
				try{					
					clearInterval(interval);
				}catch(ex){console.log(ex);}
				try{					
					clearInterval(interval2);
				}catch(ex){console.log(ex);}				
				expiryModal();
			}else{
				try{					
					clearInterval(interval2);
				}catch(ex){}
				$('#timeoutModal').modal("hide");
			}			
		}
	});
}
$(document).ready(function() {
	interval = setInterval(function(){isActive();}, 18000);/* start checking if idea more than 5 seconds */
});
$(document).bind("ajaxSuccess", function( event, xhr, settings ) {
	if(!settings.url.endsWith("isActive")){/* if ajax called reset timer */		
		if(interval!=null){
			clearInterval(interval);
			interval = setInterval(function(){isActive();}, 18000);
		}
		called=false;
	}	
});
function logout(callback){
	/*
	$.ajax({
		type: "GET",
		url: "${sessionScope.URL_LOGOUT}",
		complete: function(data){
			if(callback!=""){
				callback();
			}
		}
	});
	*/
	if(callback!=""){
		callback();
	}
}
function modal(title,message,callback){
	title = title || '';
	message = message || '';
	callback = callback || '';
	$("#messageModal .modal-title").text(title);
	$("#messageModal .modal-body").text("");
	$("#messageModal .modal-body").append(message);
	$("#messageModal").modal({
        show: true,
        backdrop: 'static'
    });
	$('#messageModal').on('hidden.bs.modal', function (e) {
		if(callback!=""){
			callback();
		}
	});
}
function secondsTimeSpanToMS(s) {
    var h = Math.floor(s/3600); 
    s -= h*3600;
    var m = Math.floor(s/60); 
    s -= m*60;
    return (m < 10 ? '0'+m : m)+":"+(s < 10 ? '0'+s : s); //zero padding on minutes and seconds
}
function timeoutModal(expiryTimerVal){
	$("#expiryTimer").html(secondsTimeSpanToMS(expiryTimerVal));
	if(interval2!=null){
		clearInterval(interval2);		
	}
	interval2 = setInterval(function(){
		console.log(expiryTimerVal);
		expiryTimerVal--;
		$("#expiryTimer").html(secondsTimeSpanToMS(expiryTimerVal));
	}, 1000);
	$("#timeoutModal").modal({
		backdrop : 'static',
		keyboard : false
	});
	$('#timeoutModalOKButton').on('click', function (e) {
		$.ajax({
			type: "POST",
			url: "${basePath}/refreshSession",
			success: function(data){
				try{
					console.log("clearInterval(interval2);");
					clearInterval(interval2);
				}catch(ex){console.log(ex);}
				$('#timeoutModal').modal("hide");
			}
		});
	});
}
function expiryModal(){
	$("#timeoutModal").modal().hide();
	$("#expiryModal").modal({
		backdrop : 'static',
		keyboard : false
	});
	$('#expiryModal').on('hide.bs.modal', function (e) {
		logout(function(){			
			window.location.assign("${sessionScope.URL_LOGIN}");
		});	
	});	
}

function confirmModal(title,message,thisOkCallback,thisCanelCallback){
	title = title || '';
	message = message || '';
	okCallback = thisOkCallback || '';
	canelCallback = thisCanelCallback || '';
	$("#confirmModal .modal-title").text(title);
	$("#confirmModal .modal-body").text("");
	$("#confirmModal .modal-body").append(message);
	$("#confirmModal").modal('show');	
}

$('#confirmModalOkButton').on('click', function (e) {
	$("#confirmModalOkButton").prop("disabled", true);
	if(typeof okCallback === 'function' ){
		okCallback();
		setTimeout(function(){$("#confirmModalOkButton").prop("disabled", false);}, 500);
		$('#confirmModal').modal('hide');
	}
});

$('#confirmModalCancelButton').on('click', function (e) {
	if(typeof canelCallback === 'function' ){
		canelCallback();
	}
});
function yesNoModal(title,message,thisYesCallback,thisNoCallback,thisCanelCallback){
	title = title || '';
	message = message || '';
	yesCallback = thisYesCallback || '';
	noCallback = thisNoCallback || '';
	canelCallback = thisCanelCallback || '';
	$("#yesNoModal .modal-title").text(title);
	$("#yesNoModal .modal-body").text("");
	$("#yesNoModal .modal-body").append(message);
	$("#yesNoModal").modal('show');	
}
$('#yesNoModalYesButton').on('click', function (e) {
	if(typeof yesCallback === 'function' ){
		yesCallback();
	}
});
$('#yesNoModalNoButton').on('click', function (e) {
	if(typeof noCallback === 'function' ){
		noCallback();
	}
});
$('#yesNoModal').on('hidden.bs.modal', function (e) {
	if(typeof canelCallback === 'function' ){
		canelCallback();
	}
});
function mlogout(){
	confirmModal("System Message","Are you sure you want to logout?",
	function () {
		window.location.assign("${sessionScope.URL_LOGOUT}");
	},function () {}
	);
}
</script>