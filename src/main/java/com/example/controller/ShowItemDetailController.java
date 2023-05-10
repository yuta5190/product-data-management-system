package com.example.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.SortItemService;

/**
 * 商品詳細表示のコントローラー
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/showitemdetail")
public class ShowItemDetailController {
	@Autowired
	private SortItemService sortItemService;

/**
 * "list"でクリックした商品を詳細表示するコントローラー
 * @param model　モデル
 * @param id　クリックした商品のid
 * @return 商品詳細表示画面
 */
@GetMapping("")
public String index(Model model,Integer id) {
	Objects.requireNonNull(id, "/showitemdetail: id must not be null");
	Optional<List<Item>> optitem =sortItemService.showItemDetail(id);
	if(optitem.isPresent()){
	Item item=optitem.get().get(0);
	if(item.getItemImage()==null||item.getItemImage().equals("")) {item.setItemImage("noimage-760x460.png");}
	model.addAttribute("Item",item);
	return "detail";
}else {return "redirect:/showitemlist";
	}
}}
