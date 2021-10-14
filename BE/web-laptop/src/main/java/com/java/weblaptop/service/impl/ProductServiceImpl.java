package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.*;
import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.BadRequestException;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.payload.ProductWithName;
import com.rookie.webwatch.repository.*;
import com.rookie.webwatch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Productrepository productrepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository imageRepository;

    @Autowired
    private ProductRatingRepository ratingRepository;

    @Autowired
    private BrandRepository brandRepository;

//    @Autowired
//    private ProductSpecification productSpecification;
//
//    @Value("${pagination.page.size.default}")
//    private Integer defaultPageSize;

    @Override
    public List<ProductResponseDTO> retrieveProducts() {
        List<Product> products = productrepository.findAll();
        return new ProductResponseDTO().toListDto(products);
    }

    @Override
    public Optional<ProductResponseDTO> getProduct(Long productId) throws ResourceNotFoundException {
        Product product = productrepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        return Optional.of(new ProductResponseDTO().convertToDto(product));
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws ResourceNotFoundException, BadRequestException {
        Product product = new ProductDTO().convertToEti(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() ->
                new ResourceNotFoundException(""+ErrorCode.FIND_CATEGORY_ERROR));
        product.setCategory(category);

        Brand brand = brandRepository.findById(productDTO.getBrand_id()).orElseThrow(()->
                new ResourceNotFoundException(""+ErrorCode.FIND_BRAND_ERROR));
        product.setBrand(brand);

        product.setProductQty(0);
        product.setRatingTB(0);
        try {
            product = productrepository.save(product);

//        List<ProductRating> ratings = new RatingDTO().toListEntity(productDTO.getRatingDTOS());
        List<ProductRating> ratings = new ArrayList<>();
        Product productRa = product;
            ratings.forEach(r -> {
                r.setRatingNumber(0);
                r.setProduct(productRa);
                ratingRepository.save(r);
            });


            List<ProductImage> images = new ImageDTO().toListEntity(productDTO.getImageDTOS());
            Product productFinal = product;
            images.forEach(i -> {
                i.setProduct(productFinal);
                //System.out.println("--------------------------"+ i.getImageLink()+"___"+i.getProduct().getProduct_id());
                imageRepository.save(i);
            });
        } catch (Exception e){
            throw new BadRequestException("invalid request "+e.getMessage());
        }
        System.out.println("aaaaa " + productDTO);
        return new ProductDTO().convertToDto(productrepository.findById(product.getProduct_id()).orElseThrow(()-> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR)));
    }

    @Override
    public Boolean deleteProduct(Long productId) throws ResourceNotFoundException {
        Product product = productrepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        this.productrepository.delete(product);
        return true;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ResourceNotFoundException {
        Product proExist = productrepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() ->
                new ResourceNotFoundException(""+ErrorCode.FIND_CATEGORY_ERROR));

        Brand brand = brandRepository.findById(productDTO.getBrand_id()).orElseThrow(()->
                new ResourceNotFoundException(""+ErrorCode.FIND_BRAND_ERROR));
        proExist.setProductName(productDTO.getProductName());
        proExist.setProductPrice(productDTO.getProductPrice());
        proExist.setProductDescription(productDTO.getProductDescription());
        proExist.setProductQty(proExist.getProductQty());

        proExist.setCategory(category);
        proExist.setBrand(brand);
        proExist.setRatingTB(proExist.getRatingTB());
        proExist.setProductRatings(proExist.getProductRatings());

        imageRepository.deleteAllByProduct(proExist);

        proExist = productrepository.save(proExist);
        Product proUpdate = proExist;

        List<ProductImage> images = new ImageDTO().toListEntity(productDTO.getImageDTOS());
        images.forEach(i -> {
            i.setProduct(proUpdate);
            imageRepository.save(i);
        });
        //proUpdate.setProductImages(images);
        //proExist.setRatingTB(proExist.getRatingTB());

        Product product = new Product();
        product = productrepository.save(proUpdate);
       // Product product = productrepository.findById(proExist.getProduct_id()).orElseThrow(()-> new ResourceNotFoundException("Product not found for this id"));
        return new ProductDTO().convertToDto(product);
    }

    @Override
    public Optional<ProductDTO> getProductInAdmin(Long productId) throws ResourceNotFoundException {
        Product product = productrepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        return Optional.of(new ProductDTO().convertToDto(product));
    }


    @Override
    public List<ProductDTO> sortProduct(PageDTO pageDTO) {
        List<Product> list = null;
        Page<Product> page = null;

        Sort sort = Sort.by(pageDTO.getSort(), pageDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), sort);

        list = productrepository.findAll(pageable).getContent();
        page = new PageImpl(list);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS = new ProductDTO().toListDto(page.getContent());

        return productDTOS;

        //return productrepository.findAll(pageable);

    }

    @Override
    public List<ProductDTO> findProductByCate(Long categoryId, PageDTO pageDTO) throws ResourceNotFoundException {
        Optional<Category> cateExist = categoryRepository.findById(categoryId);
        if(!cateExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR);
        }
        Category category = cateExist.get();

        Sort sort = Sort.by(pageDTO.getSort(), pageDTO.getSortBy());
        Pageable pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), sort);

        List<Product> list = null;
        Page<Product> page = null;
        list = productrepository.getProductsByCategory(category, pageRequest);
        page = new PageImpl(list);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS = new ProductDTO().toListDto(page.getContent());
        return productDTOS;
    }

    @Override
    public List<ProductDTO> findProductByCategory(Long categoryId) throws ResourceNotFoundException {
        Optional<Category> cateExist = categoryRepository.findById(categoryId);
        if(!cateExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR);
        }
        Category category = cateExist.get();

        List<Product> list = null;
        list = productrepository.findProductByCategory(category);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS = new ProductDTO().toListDto(list);
        return productDTOS;
    }

    @Override
    public List<ProductDTO> findProductByBrand(Long brandId) throws ResourceNotFoundException {
        Optional<Brand> brandExist = brandRepository.findById(brandId);
        if(!brandExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR);
        }
        Brand brand = brandExist.get();

        List<Product> list = null;
        list = productrepository.findProductByBrand(brand);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS = new ProductDTO().toListDto(list);
        return productDTOS;
    }

    @Override
    public List<ProductDTO> searchProduct(String productName, Pageable pageable) {
        Specification<Product> spec = Specification.where(new ProductWithName(productName));

        List<Product> list = null;
        Page<Product> products = productrepository.findAll(spec, pageable);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS = new ProductDTO().toListDto(products.getContent());
        return productDTOS;
    }

    @Override
    public List<ProductDTO> searchProductByName(String name, Pageable pageable) {
        List<Product> products = new ArrayList<>();
        Page<Product> page;

        page = productrepository.findByProductNameContaining(name, pageable);
        products = page.getContent();

        return new ProductDTO().toListDto(products);
    }

    @Override
    public Page<Product> filterProduct(String search, Long cateId, Long brandId, Pageable paging) {
        Page<Product> pagePros = null;
        Optional<Brand> brandExist = brandRepository.findById(brandId);
        Optional<Category> cateExist = categoryRepository.findById(cateId);

        if (search == null && cateId == -1 && brandId == -1){
            pagePros  = productrepository.findAllOrderByProduct_idDesc(paging);
        } else if(search != null && cateId == -1 && brandId == -1){
            pagePros = productrepository.findByProductNameContaining(search, paging);
        } else if(search != null && cateId != -1 && brandId != -1){
            pagePros = productrepository.findAllByProductNameContainingAndCategoryAndBrand(search, cateExist.get(), brandExist.get(), paging);
        }else if(search == null && brandId == -1  ){
            pagePros = productrepository.findAllByCategory(cateExist.get(), paging);
        } else if(search == null && cateId == -1){
            pagePros = productrepository.findAllByBrand(brandExist.get(), paging);
        } else if(search == null && cateId != -1 && brandId != -1 ) {
            pagePros = productrepository.findAllByCategoryAndBrand(cateExist.get(), brandExist.get(), paging);
        }else if(search != null && cateId != -1 && brandId == -1 ) {
            pagePros = productrepository.findAllByProductNameContainingAndCategory(search, cateExist.get(), paging);
        }else if(search != null && cateId == -1 && brandId != -1 ) {
            pagePros = productrepository.findAllByProductNameContainingAndBrand(search, brandExist.get(), paging);
        }
        return pagePros;
    }

    @Override
    public Page<Product> sortProductByPriceAsc(Pageable pageable) {
        Page<Product> pagePros = null;
        pagePros = productrepository.findAllByOrderByProductPriceAsc(pageable);
        return pagePros;
    }

    @Override
    public Page<Product> sortProductByPriceDesc(Pageable pageable) {
        Page<Product> pagePros = null;
        pagePros = productrepository.findAllByOrderByProductPriceDesc(pageable);
        return pagePros;
    }

    @Override
    public Page<Product> sortProductByRatingDesc(Pageable pageable) {
        Page<Product> pagePros = null;
        pagePros = productrepository.findAllByOrderByRatingTBDesc(pageable);
        return pagePros;
    }

    @Override
    public Page<Product> sortProductByName(String sort, Pageable pageable) {
        Page<Product> pagePros = null;
        if(sort.equals("asc")){
            pagePros = productrepository.findAllByOrderByProductNameAsc(pageable);
        } else if (sort.equals("desc")){
            pagePros = productrepository.findAllByOrderByProductNameDesc(pageable);
        } else if(sort.equals("hot")){
            pagePros = productrepository.findAllByOrderByRatingTBDesc(pageable);
        }
        return pagePros;
    }

}
