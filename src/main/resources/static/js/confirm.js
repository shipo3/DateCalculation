//登録ボタン2重クリックを防止する
const submit_button = document.getElementById("submit_button");

submit_button.onclick = () => {
    //２度押し防止の実装
    submit_button.disabled = true;
};
