/**
 * 
 */
function IsFileSizeOk() {
	alert("Error is :" + e);
	var fileid = document.getElementById("fileupload").value;
    try {
        var fileSize = 0;
        //for IE
        if (navigator.userAgent.match(/msie/i)) {
            //before making an object of ActiveXObject, 
            //please make sure ActiveX is enabled in your IE browser
            var objFSO = new ActiveXObject("Scripting.FileSystemObject");
            var filePath = $("#" + fileid)[0].value;
            var objFile = objFSO.getFile(filePath);
            var fileSize = objFile.size; //size in b
            fileSize = fileSize / 1048576; //size in mb 
            alert("Size exceeded");
        }
        //for FF, Safari, Opera and Others
        else {
            fileSize = $("#" + fileid)[0].files[0].size //size in b
            fileSize = fileSize / 1048576; //size in mb 
            alert("Size exceeded");
        }
        return (fileSize < 2.0);
    }
    catch (e) {
        alert("Error is :" + e);
    }
}

function validate() {
	$("#file_error").html("");
	$(".inputBox").css("border-color","#F0F0F0");
	var file_size = $('#fileupload')[0].files[0].size;
	if(file_size>1048576) {
		$("#file_error").html("File size is greater than 2MB");
		$(".inputBox").css("border-color","#FF0000");
		return false;
	} 
	return true;
}

function alertNow(){
	alert("HEY");
}