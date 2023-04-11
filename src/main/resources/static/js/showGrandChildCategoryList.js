"use strict"
$(document).ready(function() {
 var maxWidth = 160;
var grandChildvalue =$("#grandChildCategory").val();
var grandChildtext = $("#grandChildCategory option:selected").text();
setTimeout(function() {
	 let selectedOption = $('#childCategory').val();
      // Javaにデータを送信
      $.ajax({
        url:  'http://localhost:8080/additem/viewgrandchildcategory',
        type: 'GET',
        dataType: "json",
        data: {id: selectedOption },
        success: function(granddata) {
          // Javaからの応答を処理
          var grandChildCategoryList =granddata.grandChildCategoryList;
          var optgroupHtml = '';
          optgroupHtml +='<option th:value=null>-- grandChildCategory --</option>';
      $.each(grandChildCategoryList, function(_,grandChildCategory) {
        optgroupHtml += '<option value="' +grandChildCategory.id+'">' + grandChildCategory.categoryName + '</option>';
      });
      $('#grandChildCategory').html(optgroupHtml);
      
       $("#grandChildCategory").val(grandChildvalue);
       $("#grandChildCategory").find('option:selected').text(grandChildtext);
       
   selectBox.width(maxWidth);
    }
 });
  // ここに処理を記述
}, 100); 

  $(function() {
      $("#childCategory").on('click change', function () {
      // 選択されたオプションの値を取得
      let selectedOption = $('#childCategory').val();
      // Javaにデータを送信
      $.ajax({
        url:  'http://localhost:8080/additem/viewgrandchildcategory',
        type: 'GET',
        dataType: "json",
        data: {id: selectedOption },
        success: function(granddata) {
          // Javaからの応答を処理
          var grandChildCategoryList =granddata.grandChildCategoryList;
          var optgroupHtml = '<option th:value=null>-- grandChildCategory --</option>';
      $.each(grandChildCategoryList, function(_,grandChildCategory) {
        optgroupHtml += '<option value="' +grandChildCategory.id+'">' + grandChildCategory.categoryName + '</option>';
      });
      $('#grandChildCategory').html(optgroupHtml);
      selectBox.width(maxWidth);
    }
  });
      });
    });
  
   })