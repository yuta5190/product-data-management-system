package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

@Service
@Transactional
public class SelectCategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Category selectCategoryById(Integer id) {
		Category category = categoryRepository.findCategoryById(id);
		return category;
	}
}
