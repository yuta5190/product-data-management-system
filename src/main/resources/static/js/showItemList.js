"use strict"
var totalPage;
var page;
var pagingLink = $('.paging');
var prevLink = $('.previous');
var nextLink = $('.next');
var selectBox = $("select.form-control");
var maxWidth = 200;
var parentCategory = null
var childCategory = null
var grandChildCategory = null
var search = ""
var brand = ""
var orderBy = ""
var itemId;
page = 1
	if (page === 1) {
		prevLink.addClass('hidden');
	}
		if (page >= totalPage) {
			nextLink.addClass('hidden');
		} else { nextLink.removeClass('hidden'); }
	$('#pagejump').val(page);
/** 初回アクセス時に商品表示*/
$(document).ready(function() {
	
	parentCategory = $("#parentCategory").val();
	childCategory = $("#childCategory").val();
	grandChildCategory = $("#grandChildCategory").val();
	search = $('#name').val();
	orderBy = $('#sort').val();
	brand = $('#brand').val();
	$.ajax({
		url: 'http://localhost:8080/showitemlist/viewlist',
		type: 'GET',
		data: { search: search, parentCategory: parentCategory, childCategory: childCategory, grandChildCategory: grandChildCategory, brand: brand },
		dataType: "json",
		success: function(data) {
			totalPage = data.totalPage;
			$('.input-group-addon').text(totalPage);
		}
	});
	// 選択肢の幅を設定する
	selectBox.width(maxWidth);
});


/** 前ページ表示*/
$(function() {
	prevLink.on('click', function(e) {
		var rowsToDelete = document.querySelectorAll("#itemList");
				for (var i = 0; i < rowsToDelete.length; i++) {
					rowsToDelete[i].parentNode.removeChild(rowsToDelete[i]);
				}
		page = page - 1;
		$('#pagejump').val(page);
		if (page <= 1) {
			prevLink.addClass('hidden');
		} else { prevLink.removeClass('hidden'); }
		if (page >= totalPage) {
			nextLink.addClass('hidden');
		} else { nextLink.removeClass('hidden'); }
		e.preventDefault();
		$.ajax({
			url: 'http://localhost:8080/showitemlist/paging',
			type: 'GET',
			data: { page: page, search: search, parentCategory: parentCategory, childCategory: childCategory, grandChildCategory: grandChildCategory, brand: brand, totalPage: totalPage, orderBy: orderBy },
			dataType: 'json',
			success: function(data) {
				console.log(page)
				var itemList = data.itemList;
				var itemsHtml = '';
				
				for (var i = 0; i < itemList.length; i++) {
					itemId = itemList[i].id;
					itemsHtml += '<tr id=itemList">';
					itemsHtml += '<td class="item-name"><a th:href="@{/showItemDetail?ItemId=' + itemList[i].id + '}" th:text="' + itemList[i].name + '">' + itemList[i].name + '</a></td>';
					itemsHtml += '<td class="item-price">' + itemList[i].price + '</td>';
					itemsHtml += '<td class="item-category">';
					for (var j = 0; j < itemList[i].categoryDetail.length; j++) {
						itemsHtml += '<span><a href="" th:text="' + itemList[i].categoryDetail[j].categoryName + '">' + itemList[i].categoryDetail[j].categoryName + '</a></span>';
						if (j < itemList[i].categoryDetail.length - 1) {
							itemsHtml += '/';
						}
					}
					itemsHtml += '</td>';
					itemsHtml += '<td class="item-brand"><a href="" th:text="' + itemList[i].brand + '">' + itemList[i].brand + '</a></td>';
					itemsHtml += '<td class="item-condition">' + itemList[i].condition + '</td>';
					itemsHtml += '</tr>';
				}
				$('.items').html(itemsHtml);

			},
			error: function(jqXHR, textStatus, errorThrown) {
				// エラー処理
			}
		});
	});

	/** 次ページ表示*/
	nextLink.on('click', function(e) {
		var rowsToDelete = document.querySelectorAll("#itemList");
				for (var i = 0; i < rowsToDelete.length; i++) {
					rowsToDelete[i].parentNode.removeChild(rowsToDelete[i]);
				}
		page = page + 1;
		$('#pagejump').val(page);
		if (page <= 1) {
			page = 1;
			prevLink.addClass('hidden');
		} else { prevLink.removeClass('hidden'); }
		if (page >= totalPage) {
			nextLink.addClass('hidden');
		} else { nextLink.removeClass('hidden'); }
		e.preventDefault();
		$.ajax({
			url: 'http://localhost:8080/showitemlist/paging',
			type: 'GET',
			data: { page: page, search: search, parentCategory: parentCategory, childCategory: childCategory, grandChildCategory: grandChildCategory, brand: brand, totalPage: totalPage, orderBy: orderBy },
			dataType: 'json',
			success: function(data) {

				var itemList = data.itemList;
				var itemsHtml = '';
				var rowsToDelete = document.querySelectorAll("#itemList");
				
				for (var i = 0; i < itemList.length; i++) {
					itemId = itemList[i].id;
					itemsHtml += '<tr id="itemList">';
					itemsHtml += '<td class="item-name"><a th:href="@{/showItemDetail?ItemId=' + itemList[i].id + '}" th:text="' + itemList[i].name + '">' + itemList[i].name + '</a></td>';
					itemsHtml += '<td class="item-price">' + itemList[i].stringPrice + '</td>';
					itemsHtml += '<td class="item-category">';
					for (var j = 0; j < itemList[i].categoryDetail.length; j++) {
						itemsHtml += '<span><a href="" th:text="' + itemList[i].categoryDetail[j].categoryName + '">' + itemList[i].categoryDetail[j].categoryName + '</a></span>';
						if (j < itemList[i].categoryDetail.length - 1) {
							itemsHtml += '/';
						}
					}
					itemsHtml += '</td>';
					itemsHtml += '<td class="item-brand"><a href="" th:text="' + itemList[i].brand + '">' + itemList[i].brand + '</a></td>';
					itemsHtml += '<td class="item-condition">' + itemList[i].condition + '</td>';
					itemsHtml += '</tr>';
				}
				$('.items').html(itemsHtml);

			},
			error: function(jqXHR, textStatus, errorThrown) {
				// エラー処理
			}
		});
	});

	/** ページジャンプ*/
	$('#totalpage').on('click', function(e) {
		e.preventDefault();
		var rowsToDelete = document.querySelectorAll("#itemList");
				for (var i = 0; i < rowsToDelete.length; i++) {
					rowsToDelete[i].parentNode.removeChild(rowsToDelete[i]);
				}
		if (isNaN($('#pagejump').val())) {
			$('#select-error').text('数字を入力してください').css('color', 'red');

		} else {
			$('#select-error').text('');
			page = $('#pagejump').val();
			if (page <= 1) {
				prevLink.addClass('hidden');
			} else { prevLink.removeClass('hidden'); }
			if (page >= totalPage) {
				nextLink.addClass('hidden');
			} else { nextLink.removeClass('hidden'); }
			if (page > totalPage) {
				page = totalPage;

			}
			if ($('#pagejump').val() < 1) { page = 1; }
			$.ajax({
				url: 'http://localhost:8080/showitemlist/paging',
				type: 'GET',
				data: { page: page, search: search, parentCategory: parentCategory, childCategory: childCategory, grandChildCategory: grandChildCategory, brand: brand, totalPage: totalPage, orderBy: orderBy },
				dataType: 'json',
				success: function(data) {
					
					var itemList = data.itemList;
					var itemsHtml = '';
					for (var i = 0; i < itemList.length; i++) {
						itemId = itemList[i].id;
						itemsHtml += '<tr>';
						itemsHtml += '<td class="item-name"><a th:href="@{/showItemDetail?id=' + itemList[i].id + '}" th:text="' + itemList[i].name + '">' + itemList[i].name + '</a></td>';
						itemsHtml += '<td class="item-price">' + itemList[i].stringPrice + '</td>';
						itemsHtml += '<td class="item-category">';
						for (var j = 0; j < itemList[i].categoryDetail.length; j++) {
							itemsHtml += '<span><a href="" th:text="' + itemList[i].categoryDetail[j].categoryName + '">' + itemList[i].categoryDetail[j].categoryName + '</a></span>';
							if (j < itemList[i].categoryDetail.length - 1) {
								itemsHtml += '/';
							}
						}
						itemsHtml += '</td>';
						itemsHtml += '<td class="item-brand"><a href="" th:text="' + itemList[i].brand + '">' + itemList[i].brand + '</a></td>';
						itemsHtml += '<td class="item-condition">' + itemList[i].condition + '</td>';
						itemsHtml += '</tr>';
					}
					$('#pagejump').val(page);
					$('.items').html(itemsHtml);
					if (page <= 1) {
						prevLink.addClass('hidden');
					} else { prevLink.removeClass('hidden'); }
					if (page >= totalPage) {
						nextLink.addClass('hidden');
					} else { nextLink.removeClass('hidden'); }
					window.scrollTo({
						top: 0,
						behavior: 'smooth' // スムーススクロールを有効にする
					});
				},
				error: function(jqXHR, textStatus, errorThrown) {
					// エラー処理
				}
			});
		}
	});

	/** 商品名を押したときにhrefが動作するように調整*/
	$(document).on('click', '.item-name a', function(e) {
		e.preventDefault();
		window.location.href = $(this).attr('href');
	});
})