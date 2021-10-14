package com.java.weblaptop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageDTO {
    private int pageNumber = 0;
    private int pageSize = 8;
    private Sort.Direction sort;
    private String sortBy;

}
