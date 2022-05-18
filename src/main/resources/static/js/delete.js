function confirmFunction() {
//確認ダイアログを表示する
	const ans = confirm( "削除してもよろしいですか？" );
	if ( !ans){
			event.preventDefault();
	}
}
