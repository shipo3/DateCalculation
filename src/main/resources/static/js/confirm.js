//「登録する」「更新する」の連打を防止する
function delayExec() {
	setTimeout('double();', 100);
}
 function double(){
	var  submit = document.getElementById("submit_button");
	submit.disabled=true;
}
