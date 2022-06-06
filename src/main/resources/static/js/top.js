function confirmFunction() {
//確認ダイアログを表示する
	const ans = confirm( "削除してもよろしいですか？" );
	if ( !ans){
			event.preventDefault();
	}
}
//基準日が入力された時点で未入力エラーを消す
window.addEventListener('DOMContentLoaded', function(){
  
  // input要素を取得
  var inputDate = document.getElementById("inputDate");
  var inputError = document.getElementById("inputError");

  // イベントリスナーでイベント「input」を登録
  inputDate.addEventListener("input",function(){
  		inputError.parentNode.removeChild(inputError);
  });
});
   