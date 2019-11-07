<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">
<link rel="stylesheet"
	href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>


<style>
th, td {
	padding: 0 15px;
}

th {
	color: #787878;
}

#style1::-webkit-scrollbar {
	width: 8px;
	background-color: rgba(255, 255, 255, 1);
}

#style1::-webkit-scrollbar-thumb {
	background-color: rgba(128, 128, 128, 0.7);
	border-radius: 5px;
}

.left-box {
	float: left;
	width: 49%;
}

.right-box {
	float: right;
	width: 49%;
}

.left-box2 {
	float: left;
	width: 70%;
}

.right-box2 {
	float: right;
	width: 30%;
}

ul, li {
	list-style: none;
}

.slide {
	height: 400px;
	overflow: hidden;
	position: relative;
	margin-top: 10px;
	margin-bottom: 50px; 
	border-radius: 30px;
}

.slide li {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	opacity: 0;
	transition: 1s;
}

.slide li:nth-child(1) {
	background: #ffa;
}

.slide li:nth-child(2) {
	background: #faa;
}

.slide li:nth-child(3) {
	background: #afa;
}

.slide li:nth-child(4) {
	background: #aaf;
}

.slide input {
	display: none;
}

.slide .bullet {
	position: absolute;
	bottom: 20px;
	left: 0;
	right: 0;
	text-align: center;
	z-index: 10;
}

.slide .bullet label {
	width: 10px;
	height: 10px;
	border-radius: 10px;
	border: 2px solid #666;
	display: inline-block;
	background: #fff;
	font-size: 0;
	transition: 0.5s;
	cursor: pointer;
}
/* �����̵� ���� */
#pos1:checked ~ ul li:nth-child(1), #pos2:checked ~ ul li:nth-child(2),
	#pos3:checked ~ ul li:nth-child(3), #pos4:checked ~ ul li:nth-child(4)
	{
	opacity: 1;
}
/* bullet ���� */
#pos1:checked ~ .bullet label:nth-child(1), #pos2:checked ~ .bullet label:nth-child(2),
	#pos3:checked ~ .bullet label:nth-child(3), #pos4:checked ~ .bullet label:nth-child(4)
	{
	background: #666;
}

.myfont {
	font-size: 23px;
	font-weight: 900;
}

.myfont2 {
	font-size: 18px;
	font-weight: 500;
}

.ipb {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 600px;
	height: 400px;
	border-radius: 20px;
}

.ipb2 {
	display: table-cell;
	vertical-align: middle;
	background-color: white;
	width: 3000px;
	height: 100px;
	border-radius: 20px;
	padding: 10px;
}

.ipb3 {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 80%;
	height: 80%;
	border-radius: 20px;
	padding: 10px;
}

#aHover{
color: blue;}
#aHover:hover{
color: #00008c;
font-weight: 900;}
</style>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
	</div>
	<div class="container">
		<div class="body-container">
			<div class="container-block">
				<div style="margin: 10px 10px">
					<div class='left-box' style="">
						<div>
							<span style="font-size: 30px; font-family: Webdings">2</span> <span
								style="font-size: 25px;">����ø</span>
						</div>
						<div class="slide">
							<input type="radio" name="pos" id="pos1" checked> <input
								type="radio" name="pos" id="pos2"> <input type="radio"
								name="pos" id="pos3"> <input type="radio" name="pos"
								id="pos4">
							<ul>
								<li style="background-image: url('a.jpeg')"></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
							<p class="bullet">
								<label for="pos1">1</label> <label for="pos2">2</label> <label
									for="pos3">3</label> <label for="pos4">4</label>
							</p>
						</div>


						<div
							style="margin-left: 35px; position: relative; width: 500px; bottom: -50px;">
<%-- 							<img alt="" src="<%=cp%>/resource/images/what.png" style="max-width: 300px; height: auto;"> --%>
						</div>



						<div class="ipb3">
							<div class="ipb2">
								<div class="ipb2" style="text-align: left; padding-left: 30px;">
									<div style="text-align: right;">
										<a id="aHover" onclick="scheduleModal('peapleList','show');">�����ο�����</a>
									</div>
									<div style="float: left; width: 30%">
										<div>
											<p class="myfont">�Ͻ�</p>
										</div>
										<p class="myfont2">2010-10-11</p>
									</div>

									<div style="float: left; width: 40%">
										<p class="myfont">�󼼼���</p>
										<div id="style1"
											style="overflow: scroll; overflow-x: hidden; width: 90%; height: 150px;">
											<p class="myfont2">������
												��������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������Ѥ��ٸ���~~~~dfadfasfasdfadsfasfasd~~~~~~~~~</p>
										</div>
									</div>

									<div style="float: left; width: 30%">
										<p class="myfont">���</p>
										<p class="myfont2">
											<span>ȫ���Ա��� 2�� �ⱸ �Ҹ���</span><span><button
													id="click_button" style="margin-left: 5px;">����</button></span>
										</p>
									</div>


								</div>


								<!-- ������ ǥ���� div �Դϴ� -->
								<div class="aa" id="staticMap"
									style="max-width: 100%; height: 500px; margin-top: 10px; display: none;"></div>

							</div>

						</div>


					</div>



					<div class='right-box'>
						<span style="font-size: 30px; font-family: Webdings">2</span> <span
							style="font-size: 25px;">�ı�</span> <span style="float: right">

							<button class="btnConfirm"
								onclick="scheduleModal('ReviewAdd','show');">�ı���</button>
						</span>


						<!--��¥�� ���� �κ�-->
						<table
							style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">

							<tr align="left" bgcolor="#eeeeee" height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<th colspan="5" width="60">�̰ܷ� ���� �ı�
									<div style="float: right; display: inline-block;">
										<button onclick="scheduleModal('ReviewAdd','show');">����</button>
										<button>����</button>
									</div>
								</th>
							</tr>


							<tr align="left" bgcolor="#ffffff" height="35"
								style="border-bottom: 1px solid #cccccc;">
								<td colspan="5">�ʹ� ����� ŰŰ ���ٸ��̶��� �� ���� ���� ? ���� �� �ð��� �����ΰ�
									�κ�ι� �ιٵι�~ ����� �����Դϴ� ����ĭ�� ǥ���ϱ� ���� ���~ ������</td>


							</tr>

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
		<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<!-- �����ο���� --> 
    <div class="modal show-modal" id="peapleList">

        <div class="modal-content">
        
        <div>
        	<table id="moo">
            	<tr>
	            	<th>�����ο����
	            	<span class="close-button" onclick="scheduleModal('peapleList', 'none');">��</span>
	            	</th>
            	</tr>
            </table>
        </div>
         

            <form action="#post.php" method="POST">
            
            
            <div style="overflow: scroll;"> 
            	<ol class="list">
            		<li>�ڼ���(sujin)</li>
            		<li>�̰ܷ�(gyeo)</li>
            		<li>���߰�(brother)</li>
            		<li>������(evaring)</li>
            		<li>������(evaring)</li>


            	</ol>	
            </div>
            

			</form>
        </div>
    </div>
	 <!-- �ı��� --> 
    <div class="modal show-modal" id = "ReviewAdd">

        <div class="modal-content">
        
        <div>
        	    <table id="moo">
            	<tr>
            	<th>�����ı���
            	<span class="close-button" onclick="scheduleModal('ReviewAdd', 'none');">��</span>
            	</th>
            	
            	</tr>
            </table>
        </div>
         
            
           
           
            <form class="modalForm" action="#post.php" method="POST">
            
            
			  <p>�̰ܷ����� �ı�</p>
			  
			  <textarea class="textwrite" placeholder="������"></textarea>
			  
			  
			  <div>
			  
			  <button class="shortbtn" type="button">�̹���1</button>
			  <input  class="middleinput" placeholder="�̹������ϸ�">
			  
			  <button class="shortbtn" type="button">�̹���2</button>
			  <input class="middleinput" placeholder="�̹������ϸ�">
			  
			  <button class="shortbtn" type="button">�̹���3</button>
			  <input class="middleinput" placeholder="�̹������ϸ�">
			  
			  <button class="shortbtn"  type="button">�̹���4</button>
			  <input class="middleinput" placeholder="�̹������ϸ�">
			  
			  <button class="shortbtn"  type="button">�̹���5</button>
			  <input class="middleinput" placeholder="�̹������ϸ�">
			  
			  
			  </div>
			
			<div>
			  <button class="shortbtn" type="button" onclick="scheduleModal('ReviewAdd', 'none');">����ϱ�</button>
			  <button class="graybtn" type="button">�ı���</button>
			</div>
			
			</form>
        </div>
    </div>
	<script type="text/javascript">
	window.onload = function () {
		var ReviewAdd = document.getElementById("ReviewAdd");
		ReviewAdd.style.display='none';
		
		var peapleList = document.getElementById("peapleList");
		peapleList.style.display='none';
	}
	function scheduleModal(name, state) {
		if(state=='show'){
			document.getElementById(name).style.display='block';
		}else if(state=='none'){
			document.getElementById(name).style.display='none';
		}
	};
	
	</script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e99194984cb0bb9f3ba4db6533fbba3c"></script>
	<script>
		// �̹��� �������� ��Ŀ�� ǥ�õ� ��ġ�Դϴ� 
		var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

		// �̹��� ������ ǥ���� ��Ŀ�Դϴ�
		// �̹��� ������ ǥ���� ��Ŀ�� Object �����Դϴ�
		var marker = {
			position : markerPosition
		};

		var staticMapContainer = document.getElementById('staticMap'), // �̹��� ������ ǥ���� div  
		staticMapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // �̹��� ������ �߽���ǥ
			level : 3, // �̹��� ������ Ȯ�� ����
			marker : marker
		// �̹��� ������ ǥ���� ��Ŀ 
		};

		//�̹��� ��� 
		$('#click_button').click(
				function() {

					$('.aa').slideToggle(
							'normal',
							function() {
								// ��ü�� �� ��ġ���ų� ������ ���� ���⿡ �� ������ ����ȴ�.
								var staticMap = new kakao.maps.StaticMap(
										staticMapContainer, staticMapOption);
							});

				});
	</script>
</body>
</html>