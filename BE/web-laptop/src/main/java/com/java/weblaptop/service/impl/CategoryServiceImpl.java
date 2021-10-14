package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.CategoryDTO;

import com.rookie.webwatch.dto.ErrorCode;
import com.rookie.webwatch.entity.Category;

import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.CategoryRepository;
import com.rookie.webwatch.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> retrieveCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new CategoryDTO().toListDto(categories);
    }

    @Override
    public Optional<CategoryDTO> getCate(Long cateId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(cateId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        return Optional.of(new CategoryDTO().convertToDto(category));
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) throws ResourceNotFoundException {
        Category category = new CategoryDTO().convertToEti(categoryDTO);
//        Category parent = categoryRepository.findById(categoryDTO.getParent_id()).orElseThrow(()-> new ResourceNotFoundException(ErrorCode.FIND_CATEGORY_ERROR+""));
//        category.setParent(parent);
        return new CategoryDTO().convertToDto(categoryRepository.save(category));
    }

    @Override
    public Boolean deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        this.categoryRepository.delete(category);
        return true;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws ResourceNotFoundException {
        Category cateExist = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        cateExist.setCategoryName(categoryDTO.getCategoryName());

//        Category parent = categoryRepository.findById(categoryDTO.getParent_id()).orElseThrow(()-> new ResourceNotFoundException(ErrorCode.FIND_CATEGORY_ERROR+""));
//        cateExist.setParent(parent);

        Category category = new Category();
        category = categoryRepository.save(cateExist);
        return new CategoryDTO().convertToDto(category);
    }

//    @Override
//    public List<CategoryDTO> getCategoryByParent(Long categoryId) throws ResourceNotFoundException {
//        Optional<Category> cateExist = categoryRepository.findById(categoryId);
//        if(!cateExist.isPresent()){
//            throw new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR);
//        }
//        Category category = cateExist.get();
//
//        List<Category> list = null;
//        list = categoryRepository.getCategoriesByParent(category);
//
//        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//        categoryDTOS = new CategoryDTO().toListDto(list);
//        return categoryDTOS;
//    }

//    @Override
//    public CategoryDTO createParent(CategoryDTO categoryDTO) {
//        Category category = new CategoryDTO().convertToEti(categoryDTO);
////        category.setParent(category);
//        return new CategoryDTO().convertToDto(categoryRepository.save(category));
//    }

//    @Override
//    public List<CategoryDTO> getParent() {
//        List<Category> list = null;
//        list = categoryRepository.findAllParentCategory();
//
//        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//        categoryDTOS = new CategoryDTO().toListDto(list);
//        return categoryDTOS;
//    }

}
