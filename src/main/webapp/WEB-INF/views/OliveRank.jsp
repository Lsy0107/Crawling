<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<link
	href="${pageContext.request.contextPath}/resources/SBAdmin/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Dashboard</title>

<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/resources/SBAdmin/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="${pageContext.request.contextPath}/resources/SBAdmin/css/sb-admin-2.min.css"
	rel="stylesheet">

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/include/sidebar.jsp"%>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<%@ include file="/WEB-INF/views/include/topbar.jsp"%>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">OliveRank.jsp 컨텐츠영역</h1>
					</div>

					<!-- 컨텐츠 시작 -->

					<!-- 컨텐츠1 시작 -->
					<div class="row">
						<div class="col">
							<table id="dataTable">
								<thead>
									<tr>
										<th>랭킹</th>
										<th>이미지</th>
										<th>브랜드</th>
										<th>이름</th>
										<th>가격</th>
										<th>리뷰</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${prdList}" var="prd" varStatus="status">
										<tr>
											<td>${status.index+1}</td>
											<td><img alt="" src="${prd.prdImg}" width="30px;"></td>
											<td>${prd.prdBrd}</td>
											<td>${prd.prdName}</td>
											<td>${prd.prdPrice}</td>
											<td>${prd.prdRev}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
					<!-- 컨텐츠1 끝-->


					<!-- 컨텐츠 끝 -->

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<%@ include file="/WEB-INF/views/include/footer.jsp"%>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>



	<!-- Bootstrap core JavaScript-->
	<script
		src="${pageContext.request.contextPath}/resources/SBAdmin/vendor/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/SBAdmin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="${pageContext.request.contextPath}/resources/SBAdmin/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script
		src="${pageContext.request.contextPath}/resources/SBAdmin/js/sb-admin-2.min.js"></script>
	<!-- Page level plugins -->
	<script src="${pageContext.request.contextPath}/resources/SBAdmin/vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/SBAdmin/vendor/datatables/dataTables.bootstrap4.min.js"></script>

	<!-- Page level custom scripts -->
	<script src="${pageContext.request.contextPath}/resources/SBAdmin/js/demo/datatables-demo.js"></script>


</body>

</html>