"use strict"
$(document).ready(function() {
var childvalue = $("#childCategory").val();
var childtext = $("#childCategory option:selected").text();
var maxWidth = 100;

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
			var optgroupHtml = '';
			optgroupHtml += '<option th:value=null>-- childCategory --</option>'
			$.each(CategoryList, function(_, childCategory) {

				optgroupHtml += '<option value="' + childCategory.id + '" name=>' + childCategory.categoryName + '</option>';
			});
			$('#childCategory').html(optgroupHtml);
			$("#childCategory").val(childvalue);
			$("#childCategory").find('option:selected').text(childtext);
			selectBox.width(maxWidth);
		}
	});
	// ここに処理を記述
}, 50);

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
				
				$("#grandChildCategoryValue").remove();
				// Javaからの応答を処理
				var CategoryList = data.childCategoryList;
				var optgroupHtml = '<option th:value=null>-- childCategory --</option>';
				$.each(CategoryList, function(_, childCategory) {
					optgroupHtml += '<option value="' + childCategory.id + '">' + childCategory.categoryName + '</option>';
				});
				$('#childCategory').html(optgroupHtml);
				selectBox.width(maxWidth);
			}
		});
	});
});
});