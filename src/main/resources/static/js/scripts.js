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
}

function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userId, data.formattedCreateData, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").prepend(template);
	
	$("textarea[name=contents]").val("");
}

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();
	
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url : " + url);
	
	$.ajax({
		type : 'delete', 
		url : url, 
		dataType : 'json', 
		error : function (xhr, status) {
			console.log("error");
		}, 
		success : function (data, status) {
			console.log(data);
			if (data.valid) {
				deleteBtn.closest("article").remove();
			}else {
				alert(data.errorMessage);
			}
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined'
			? args[number]
		: match
		;
	});
};