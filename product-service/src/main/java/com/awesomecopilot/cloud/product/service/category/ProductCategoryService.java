package com.awesomecopilot.cloud.product.service.category;

import com.awesomecopilot.cloud.product.vo.AppCategoryRespVO;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.SQLOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryService {

	@Autowired
	private SQLOperations sqlOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	public List<AppCategoryRespVO> getEnableCategoryList() {
		//return sqlOperations.findList("select * from product_category where deleted=0 and tenant_id=1 order by sort asc", AppCategoryRespVO.class);
		return sqlOperations.findList("select * from product_category order by sort asc", AppCategoryRespVO.class);
		//List<Object> catogories = criteriaOperations.query(ProductCategory.class)
		//		//.eq("deleted", false)
		//		.asc("sort")
		//		.findList();
		//List<AppCategoryRespVO> results = BeanUtils.copyProperties(catogories, AppCategoryRespVO.class);
		//return results;
	}
}
