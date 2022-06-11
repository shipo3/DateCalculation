 //登録名、説明が入力された時点で未入力エラーを消す
window.addEventListener('DOMContentLoaded', function(){
  
  // input要素を取得
  var name = document.getElementById("name");
  var detail = document.getElementById("detail");
  var nameError = document.getElementById("nameError");
  var detailError = document.getElementById("detailError");

  // イベントリスナーでイベント「input」を登録
  name.addEventListener("input",function(){
  		nameError.parentNode.removeChild(nameError);
  });
  detail.addEventListener("input",function(){
  		detailError.parentNode.removeChild(detailError);
  });
  
});
  