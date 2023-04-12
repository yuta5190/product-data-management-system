"use strict"
// 読み込み時$("#parentCategory")に紐づくカテゴリーを取得する際に、コントローラーからchildCategory"を退避し処理後に追加するコード
$(document).ready(function() {
	var grandChildvalue = $("#grandChildCategory").val();
	var grandChildtext = $("#grandChildCategory option:selected").text();

	//フォームの大きさが更新と共に変更されるため設定
	var maxWidth = 200;
	selectBox.width(maxWidth);

	//初期の文字表示だとvalue=0となり、chld,grandchildがnullになってしまうため適応外にする
	if (grandChildvalue !== '0') {
		setTimeout(function() {
			let selectedOption = $('#childCategory').val();
			// Javaにデータを送信
			$.ajax({
				url: 'http://localhost:8080/additem/viewgrandchildcategory',
				type: 'GET',
				dataType: "json",
				data: { id: selectedOption },
				success: function(granddata) {
					// Javaからの応答を処理
					var grandChildCategoryList = granddata.grandChildCategoryList;

					//データに基づきhtmlを作成
					var optgroupHtml = '';
					optgroupHtml += '<option th:value=0>-- grandChildCategory --</option>';
					$.each(grandChildCategoryList, function(_, grandChildCategory) {
						optgroupHtml += '<option value="' + grandChildCategory.id + '">' + grandChildCategory.categoryName + '</option>';
					});
					$('#grandChildCategory').html(optgroupHtml);

					//option更新により値が初期化されるため、再度設定仕直す
					$("#grandChildCategory").val(grandChildvalue);
					$("#grandChildCategory").find('option:selected').text(grandChildtext);
				}
			});
			//動作のもととなるvalの読み込みまで時間がかかり、時間を指定しないと取得できないため設定
		}, 200);
	}

	//前のカテゴリーが変更時に機能するメソッド
	$(function() {
		$("#childCategory").on('click change', function() {

			// 選択されたオプションの値を取得
			let selectedOption = $('#childCategory').val();
			// Javaにデータを送信
			$.ajax({
				url: 'http://localhost:8080/additem/viewgrandchildcategory',
				type: 'GET',
				dataType: "json",
				data: { id: selectedOption },
				success: function(granddata) {

					//データに基づきhtmlを作成
					var grandChildCategoryList = granddata.grandChildCategoryList;
					var optgroupHtml = '<option th:value=0 value=0>-- grandChildCategory --</option>';
					$.each(grandChildCategoryList, function(_, grandChildCategory) {
						optgroupHtml += '<option value="' + grandChildCategory.id + '">' + grandChildCategory.categoryName + '</option>';
					});
					$('#grandChildCategory').html(optgroupHtml);

				}
			});
		});
	});
})