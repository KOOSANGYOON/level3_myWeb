$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();		// input contents 내용이 server로 바로 가는것을 막아두는 방법.
	console.log("click me");

	var queryString = $(".answer-write").serialize();
	console.log("query : " + queryString);		// server로 넘어가려는 정보 확인(query 확인)

	var url = $(".answer-write").attr("action");
	console.log("url : " + url);

	$.ajax({
		type : 'post', 
		url : url, 
		data : queryString, 
		dataType : 'json', 
		error : onError, 
		success : onSuccess});
}

function onError(request,status,error) {
	console.log("error" + error);
//	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
//	alert("code: "+request.status+"\n"+"error: "+error);
}

function onSuccess(data, status) {
	console.log(data);
}