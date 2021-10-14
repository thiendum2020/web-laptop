package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
//r lòi ra lỗi nảy :v lỗi này fix s, clone lại ;v vcc v, để tên như cũ đi ;v cũng được,nảy chạy được tới đây, mới đổi cái nảy mới cahyj lỗi m clone về lại đi éo biết lỗi do sửa cate hay đổi tên o
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private long category_id;

    @NotBlank
    private String categoryName;
    private long parent_id;


    public CategoryDTO convertToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategory_id(category.getCategory_id());
        categoryDTO.setCategoryName(category.getCategoryName());
      //  categoryDTO.setParent_id(category.getParent().getCategory_id());

        return categoryDTO;
    }

    public CategoryDTO(long category_id, String categoryName) {
        this.category_id = category_id;
        this.categoryName = categoryName;
    }

    public Category convertToEti(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setCategory_id(categoryDTO.getCategory_id());
        category.setCategoryName(categoryDTO.getCategoryName());

        return category;
    }


    public List<CategoryDTO> toListDto(List<Category> listEntity) {
        List<CategoryDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

}
