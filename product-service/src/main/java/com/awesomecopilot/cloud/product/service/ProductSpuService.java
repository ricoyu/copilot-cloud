package com.awesomecopilot.cloud.product.service;

import com.awesomecopilot.cloud.common.enums.CommonStatusEnum;
import com.awesomecopilot.cloud.product.entity.ProductCategory;
import com.awesomecopilot.cloud.product.entity.ProductSpu;
import com.awesomecopilot.cloud.product.vo.AppProductSpuPageReqVO;
import com.awesomecopilot.cloud.product.vo.ProductCategoryListReqVO;
import com.awesomecopilot.cloud.product.vo.ProductSpuVO;
import com.awesomecopilot.common.lang.utils.CollectionUtils;
import com.awesomecopilot.common.lang.vo.Page;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
import com.awesomecopilot.orm.dao.SQLOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductSpuService {

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Autowired
	private SQLOperations sqlOperations;

	public List<ProductSpuVO> getSpuPage(AppProductSpuPageReqVO pageReqVO) {
		// 查找时，如果查找某个分类编号，则包含它的子分类。因为顶级分类不包含商品
		Set<Long> categoryIds = new HashSet<>();
		if (pageReqVO.getCategoryId() != null && pageReqVO.getCategoryId() > 0) {
			categoryIds.add(pageReqVO.getCategoryId());
			//List<ProductCategory> categoryChildren = categoryService.getCategoryList(new ProductCategoryListReqVO()
			//		.setStatus(CommonStatusEnum.ENABLE.getStatus()).setParentId(pageReqVO.getCategoryId()));

			ProductCategoryListReqVO productCategoryListReqVO = new ProductCategoryListReqVO();
			productCategoryListReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
			productCategoryListReqVO.setParentId(pageReqVO.getCategoryId());
			/*
			 * 下面的查询等价于MybatisPlus的查询:
			 * return selectList(new LambdaQueryWrapperX<ProductCategoryDO>()
			 * .likeIfPresent(ProductCategoryDO::getName, listReqVO.getName())
			 * .eqIfPresent(ProductCategoryDO::getParentId, listReqVO.getParentId())
			 * .inIfPresent(ProductCategoryDO::getId, listReqVO.getParentIds())
			 * .eqIfPresent(ProductCategoryDO::getStatus, listReqVO.getStatus())
			 * .orderByDesc(ProductCategoryDO::getId));
			 */
			List<ProductCategory> categoryChildren = criteriaOperations.query(ProductCategory.class)
					.like("name", productCategoryListReqVO.getName())
					.eq("parentId", productCategoryListReqVO.getParentId())
					.in("id", productCategoryListReqVO.getParentIds())
					.eq("status", productCategoryListReqVO.getStatus())
					.desc("id")
					.findList();

			categoryIds.addAll(categoryChildren.stream().map(ProductCategory::getId).collect(Collectors.toSet()));
		}

		if (CollectionUtils.isNotEmpty(pageReqVO.getCategoryIds())) {
			categoryIds.addAll(pageReqVO.getCategoryIds());

			/*
			 * 下面的查询等价于MybatisPlus的查询:
			 * return selectList(new LambdaQueryWrapperX<ProductCategoryDO>()
			 * .likeIfPresent(ProductCategoryDO::getName, listReqVO.getName())
			 * .eqIfPresent(ProductCategoryDO::getParentId, listReqVO.getParentId())
			 * .inIfPresent(ProductCategoryDO::getId, listReqVO.getParentIds())
			 * .eqIfPresent(ProductCategoryDO::getStatus, listReqVO.getStatus())
			 * .orderByDesc(ProductCategoryDO::getId));
			 */
			ProductCategoryListReqVO productCategoryListReqVO = new ProductCategoryListReqVO();
			productCategoryListReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
			productCategoryListReqVO.setParentIds(pageReqVO.getCategoryIds());

			List<ProductCategory> categoryChildrens = criteriaOperations.query(ProductCategory.class)
					.like("name", productCategoryListReqVO.getName())
					.eq("parentId", productCategoryListReqVO.getParentId())
					.in("id", productCategoryListReqVO.getParentIds())
					.eq("status", productCategoryListReqVO.getStatus())
					.desc("id")
					.findList();

			categoryIds.addAll(categoryChildrens.stream().map(ProductCategory::getId).collect(Collectors.toSet()));
		}
		// 分页查询 return productSpuMapper.selectPage(pageReqVO, categoryIds);
		//分页查询逻辑
		/*LambdaQueryWrapperX<ProductSpuDO> query = new LambdaQueryWrapperX<ProductSpuDO>()
				// 关键字匹配，目前只匹配商品名
				.likeIfPresent(ProductSpuDO::getName, pageReqVO.getKeyword())
				// 分类
				.inIfPresent(ProductSpuDO::getCategoryId, categoryIds);
		// 上架状态 且有库存
		query.eq(ProductSpuDO::getStatus, ProductSpuStatusEnum.ENABLE.getStatus());

		// 排序逻辑
		if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_SALES_COUNT)) {
			query.last(String.format(" ORDER BY (sales_count + virtual_sales_count) %s, sort DESC, id DESC",
					pageReqVO.getSortAsc() ? "ASC" : "DESC"));
		} else if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_PRICE)) {
			query.orderBy(true, pageReqVO.getSortAsc(), ProductSpuDO::getPrice)
					.orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
		} else if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_CREATE_TIME)) {
			query.orderBy(true, pageReqVO.getSortAsc(), ProductSpuDO::getCreateTime)
					.orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
		} else {
			query.orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
		}
		return selectPage(pageReqVO, query);*/

		/*
		 * 准备查询参数
		 */
		Map<String, Object> params = new HashMap<>();
		params.put("categoryIds", categoryIds);
		if (pageReqVO.getKeyword() != null) {
			params.put("keyword", "%" + pageReqVO.getKeyword() + "%");
		}
		params.put("sortField", pageReqVO.getSortField());
		params.put("sortAsc", pageReqVO.getSortAsc());

		Page page = pageReqVO.getPage();
		List<ProductSpuVO> productSpus = sqlOperations.query("queryProductSpu")
				.addParams(params)
				.page(page)
				.resultClass(ProductSpuVO.class)
				.findPage();
		return productSpus;
	}

	public ProductSpu getSpu(Long id) {
		return entityOperations.ensureEntityExists(ProductSpu.class, id);
	}

	public void updateBrowseCount(Long spuId) {
		sqlOperations.execute("update product_spu set browse_count = browse_count + 1 where id = :spuId", "spuId", spuId);
	}
}
