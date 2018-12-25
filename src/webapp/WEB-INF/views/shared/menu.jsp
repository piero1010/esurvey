<!-- Side Navbar -->
<nav class="side-navbar mCustomScrollbar _mCS_1 shrink ">
	<div class="side-navbar-wrapper">
		<!-- Sidebar Header    -->
		<div>
			<h5 class="sidenav-heading">Menu</h5>
			<ul id="side-menu" class="side-menu list-unstyled">
				<li><a href="${basePath}"><i class="fas fa-edit"></i>My Survey</a></li>
				<c:if test="${sessionScope.SSN_IS_SRVY_COOR}">
					<li><a href="${basePath}\dashboard"><i class=" fas fa-tachometer-alt"></i>Dashboard</a></li>
					<li><a href="${basePath}\template"><i class="fas fa-file-invoice"></i>Create Template</a></li>
					<li><a href="${basePath}\template\list"><i class="fas fa-list-ul"></i>List Templates</a></li>
					<li><a href="${basePath}\survey"><i class="fas fa-plus-square"></i>Create Survey</a></li>
					<li><a href="${basePath}\survey\list"><i class="fas fa-list"></i>List Surveys</a></li>
				</c:if>
				<c:if test="${fn:containsIgnoreCase(sessionScope.SSN_USER_SRVY_GRP_ARR, '1')}">
					<li><a href="${basePath}\coordinator"><i class="fas fa-user-edit"></i>Coordinator Maintenance</a></li>
				</c:if>
				<c:if test="${fn:containsIgnoreCase(sessionScope.SSN_USER_SRVY_GRP_ARR, '1')}">
					<li><a href="${basePath}\code"><i class="fas fa-cogs"></i>Code Maintenance</a></li>
				</c:if>
				<li><a href="#" onclick="mlogout()"><i class="fas fa-sign-out-alt"></i>Logout</a></li>		
			</ul>
		</div>
	</div>
</nav>