<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Process Termination Portal</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		/* For zebra striping */
		$("table tr:nth-child(odd)").addClass("odd-row");
		/* For cell text alignment */
		$("table td:first-child, table th:first-child").addClass("first");
		/* For removing the last border */
		$("table td:last-child, table th:last-child").addClass("last");
	});
</script>

<style type="text/css">
html,body,div,span,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,abbr,address,cite,code,del,dfn,em,img,ins,kbd,q,samp,small,strong,sub,sup,var,b,i,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td
	{
	margin: 0;
	padding: 0;
	border: 0;
	outline: 0;
	font-size: 100%;
	vertical-align: baseline;
	background: transparent;
}

body {
	margin: 0;
	padding: 0;
	font: 12px/15px "Helvetica Neue", Arial, Helvetica, sans-serif;
	color: #555;
	background: #f5f5f5 url(bg.jpg);
}

a {
	color: #666;
}

#content {
	width: 75%;
	max-width: 890px;
	margin: 6% auto 0;
}

/*
	Pretty Table Styling
	CSS Tricks also has a nice writeup: http://css-tricks.com/feature-table-design/
	*/
table {
	overflow: hidden;
	border: 1px solid #d3d3d3;
	background: #fefefe;
	width: 100%;
	margin: 5% auto 0;
	-moz-border-radius: 5px; /* FF1+ */
	-webkit-border-radius: 5px; /* Saf3-4 */
	border-radius: 5px;
	-moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
	-webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
}

th,td {
	padding: 18px 28px 18px;
	text-align: center;
}

th {
	padding-top: 22px;
	text-shadow: 1px 1px 1px #fff;
	background: #e8eaeb;
}

td {
	border-top: 1px solid #e0e0e0;
	border-right: 1px solid #e0e0e0;
}

tr.odd-row td {
	background: #f6f6f6;
}

td.first,th.first {
	text-align: left
}

td.last {
	border-right: none;
}

/*
	Background gradients are completely unnecessary but a neat effect.
	*/
td {
	background: -moz-linear-gradient(100% 25% 90deg, #fefefe, #f9f9f9);
	background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f9f9f9),
		to(#fefefe) );
}

tr.odd-row td {
	background: -moz-linear-gradient(100% 25% 90deg, #f6f6f6, #f1f1f1);
	background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f1f1f1),
		to(#f6f6f6) );
}

th {
	background: -moz-linear-gradient(100% 20% 90deg, #e8eaeb, #ededed);
	background: -webkit-gradient(linear, 0% 0%, 0% 20%, from(#ededed),
		to(#e8eaeb) );
}

/*
	I know this is annoying, but we need additional styling so webkit will recognize rounded corners on background elements.
	Nice write up of this issue: http://www.onenaught.com/posts/266/css-inner-elements-breaking-border-radius
	
	And, since we've applied the background colors to td/th element because of IE, Gecko browsers also need it.
	*/
tr:first-child th.first {
	-moz-border-radius-topleft: 5px;
	-webkit-border-top-left-radius: 5px; /* Saf3-4 */
}

tr:first-child th.last {
	-moz-border-radius-topright: 5px;
	-webkit-border-top-right-radius: 5px; /* Saf3-4 */
}

tr:last-child td.first {
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-bottom-left-radius: 5px; /* Saf3-4 */
}

tr:last-child td.last {
	-moz-border-radius-bottomright: 5px;
	-webkit-border-bottom-right-radius: 5px; /* Saf3-4 */
}

.myButton {
	-moz-box-shadow: 0px 3px 24px 3px #d1dbce;
	-webkit-box-shadow: 0px 3px 24px 3px #d1dbce;
	box-shadow: 0px 3px 24px 3px #d1dbce;
	background-color:#aaadab;
	-moz-border-radius:28px;
	-webkit-border-radius:28px;
	border-radius:28px;
	border:1px solid #969996;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Georgia;
	font-size:17px;
	font-weight:bold;
	padding:16px 31px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.myButton:hover {
	background-color:#000000;
}
.myButton:active {
	position:relative;
	top:1px;
}

</style>
<script type="text/javascript">
function getid(){
	var lines = document.getElementById("wqmsidmul").value.split('\n');
	//alert(lines.length);
	var wqmsidlen= lines.length - 1 ;
	document.getElementById("totalwqmsid").innerHTML = "Total Number of WQMS ID = "+wqmsidlen;
	//var wqmsid = '';
	//for(var g= 1;g<lines.length;g++){
	//	wqmsid += ":" + lines[g] ;
	//}
	//wqmsid = wqmsid.replace("undefined:","");
	//alert(wqmsid);
	//document.getElementById("wqmshidden").vaule = wqmsid;
	//alert(document.getElementById("wqmshidden").vaule);
}
function changeid(value)	{
	//alert(value);
	if(value == "single"){
		document.getElementById("wqmsdis").innerHTML="<input type='text' name='wqmsidsin' id='wqmsidsin'/>";
	}
	if(value == "multiple")
		document.getElementById("wqmsdis").innerHTML="<textarea rows='4' cols='30' id='wqmsidmul' name='wqmsidmul' onkeyup='getid()''></textarea>";
	}
	function validate(){
		
	}
</script>
</head>
<body style="background: url("images/repeat_bg.gif") 0px 0px
	repeat;margin: 0px;display:block;">
	<img alt="IBMPortal" src="logo-header-process-admin.png">

	<div align="center" id="content">
		<form action="takeaction.jsp" method="post" onsubmit="validate()">
		<table>
		<tr>
		<td>
			<table border="1">
				<tr>
					<th colspan="3" align="center" ><span style="margin-left: 150px;"><b>Process Termination Portal</b></span></th>
				</tr>
				<tr>
					<th>TATA ID :</th>
					<td><input type="text" id="tataid" name="tataid" /></td>
				</tr>
				<tr>
					<th>TICKET NUMBER :</th>
					<td><input type="text" id="ticketno" name="ticketno" /></td>
				</tr>
				<tr>
					<th>ID TYPE</th>
					<td>
					<input type="radio" value="single" checked name="choosetype" onclick="changeid(this.value)">ONE WQMS ID</option>
					<input type="radio" value="multiple" name="choosetype" onclick="changeid(this.value)">Multiple WQMS ID's</option>
					</td>
				</tr>
				<tr>
				<input type="hidden" id="wqmshidden" name="wqmshidden" />
					<th valign="top" style="vertical-align: middle;">WQMS ID(s) :<div id="totalwqmsid" style="color: red"></div></th>
					<td id="wqmsdis"><input type="text" name="wqmsidsin" id="wqmsidsin"/>
					</td>
				</tr>
				<tr><td colspan="2" ><input type="submit" class="myButton" style="margin-left: 150px"/></td></tr>
			</table>
			<td>
			<td>
			
			<%
			if(request.getParameter("responseHTML") !=null){
				String responseHTML =request.getParameter("responseHTML");
				%>
				<table><tr><th>Response</th><tr><tr><td><%=responseHTML %></td></tr></table>
				<%
			}
			%>
			</td>
			</tr>
			
			</table>
		</form>
	</div>

</body>
</html>