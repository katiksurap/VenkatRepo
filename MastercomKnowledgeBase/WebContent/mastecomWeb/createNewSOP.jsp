<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Knowledge Base Portal</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
	<link href="css/style.css" rel="stylesheet"/>
	 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="js/toolbox.js"></script>
  <style>
  .custom-combobox {
    position: relative;
    display: inline-block;
  }
  .custom-combobox-toggle {
    position: absolute;
    top: 0;
    bottom: 0;
    margin-left: -1px;
    padding: 0;
  }
  .custom-combobox-input {
    margin: 0;
    padding: 5px 10px;
  }
  </style>
<script type="text/javascript">
	$(function() {
		/* For zebra striping */
		$("table tr:nth-child(odd)").addClass("odd-row");
		/* For cell text alignment */
		$("table td:first-child, table th:first-child").addClass("first");
		/* For removing the last border */
		$("table td:last-child, table th:last-child").addClass("last");
	});
	
	function checkValidate(){
		var PrjType = document.getElementById("PrjType").value;
		var tagname = document.getElementById("tagname").value;
		var tagdesc = document.getElementById("tagdesc").value;
		
		if(PrjType == null || PrjType == "" || PrjType == "--Select--"){
			alert("Kindly select Project type");
			document.getElementById("PrjType").focus();
			return false;
		}
		if(tagname == null || tagname == ""){
			alert("Kindly enter TagName ");
			document.getElementById("tagname").focus();
			return false;
		}
		if(tagdesc == null || tagdesc == ""){
			alert("Kindly ente TagDescription");
			document.getElementById("tagdesc").focus();
			return false;
		}
	}
</script>

</head>
<body style="background: url("images/repeat_bg.gif") 0px 0px
	repeat;margin: 0px;display:block;">
	<img alt="IBMPortal" src="logo-header-process-admin.png">

	<div align="center" id="content">
		<form action="AddNewXml.jsp" method="post" onsubmit="return checkValidate()">
		<table>
		<tr>
		<td>
			<table border="1">
				<tr>
					<th colspan="3" align="center" ><span style="margin-left: 150px;">
					<b>Create New Knowledge Base SOP</b>
					<input type="button" align="right" class="myButton"  style ="margin-left: 260px;"value="<-- Go Back" onclick="javascript:window.location='index.html'"/>
					</th>
				</tr>
				<tr>
					<th>Project</th>
					<td><select   class="mySelect" name="PrjType" id="PrjType" >
					<option value="--Select--">Select</option>
					<option value="wqms">WQMS</option>
					<option value="ces">CES</option>
					
					
					</select></td>
				</tr>
				<tr>
				<td>Enter Tag Name</td>
				<td>
				<input type="text" id="tagname" name="tagname" style="width: 600px;"/>
				</td></tr>
				<tr><td>Enter Tag Description</td><td>
				<textarea  rows="10" cols="50"  style="width: 600px;" id="tagdesc" name="tagdesc"  width="100%"></textarea>
				</td></tr>
				<tr><td colspan="2" ><input type="submit" class="myButton" style="margin-left: 150px"/></td></tr>
			</table>
			</td>
			
			</tr>
			
			</table>
		</form>
	</div>

</body>
</html>