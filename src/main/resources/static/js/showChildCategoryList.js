"use strict"
// 読み込み時$("#parentCategory")に紐づくカテゴリーを取得する際に、コントローラーからchildCategory"を退避し処理後に追加するコード
$(document).ready(function() {
	var childvalue = $("#childCategory").val();
	var childtext = $("#childCategory option:selected").text();

	//フォームの大きさが更新と共に変更されるため設定
	var maxWidth = 200;
	selectBox.width(maxWidth);

	//初期の文字表示だとvalue=0となり、chld,grandchildがnullになってしまうため適応外にする
	if (childvalue !== '0') {
		setTimeout(function() {
			let selectedOption = $('#parentCategory').val();
			// Javaにデータを送信
			$.ajax({
				url: 'http://localhost:8080/additem/viewchildcategory',
				type: 'GET',
				dataType: "json",
				data: { id: selectedOption },
				success: function(data) {
					// Javaからの応答を処理
					var CategoryList = data.childCategoryList;
					
					//データに基づきhtmlを作成
					var optgroupHtml = '';
					optgroupHtml += '<option th:value=null>-- childCategory --</option>'
					$.each(CategoryList, function(_, childCategory) {
						optgroupHtml += '<option value="' + childCategory.id + '" name=>' + childCategory.categoryName + '</option>';
					});
					$('#childCategory').html(optgroupHtml);
					
					//option更新により値が初期化されるため、再度設定仕直す
					$("#childCategory").val(childvalue);
					$("#childCategory").find('option:selected').text(childtext);
				}
			});
		}, 
	//動作のもととなるvalの読み込みまで時間がかかり、時間を指定しないと取得できないため設定
	100);
	}

   //前のカテゴリーが変更時に機能するメソッド
	$(function() {
		$("#parentCategory").on('click change', function() {
			
			// 選択されたオプションの値を取得
			let selectedOption = $('#parentCategory').val();
			
			// Javaにデータを送信
			$.ajax({
				url: 'http://localhost:8080/additem/viewchildcategory',
				type: 'GET',
				dataType: "json",
				data: { id: selectedOption },
				success: function(data) {
					//option変更後に紐づかない値が次のselectに残ってしまうため一度消去する。
　　　　　　　　　　　		$("#grandChildCategoryValue").remove();
					
					//データに基づきhtmlを作成
					var CategoryList = data.childCategoryList;
					var optgroupHtml = '<option th:value=null>-- childCategory --</option>';
					$.each(CategoryList, function(_, childCategory) {
						optgroupHtml += '<option value="' + childCategory.id + '">' + childCategory.categoryName + '</option>';
					});
					$('#childCategory').html(optgroupHtml);
				}
			});
		});
	});
});