package com.awesomecopilot.cloud.statistic.service;

import com.awesomecopilot.cloud.statistic.bo.MemberAreaStatisticsRespBO;
import com.awesomecopilot.orm.dao.SQLOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberStatisticService {

	@Autowired
	private SQLOperations sqlOperations;

	public Map<String, List<MemberAreaStatisticsRespBO>> getMemberAreaStatisticsList() {
		// 统计用户
		// TODO @疯狂：可能得把每个省的用户，都查询出来，然后去 order 那边 in；因为要按照这些人为基础来计算；；用户规模量大可能不太好，但是暂时就先这样搞吧 = =
		List<MemberAreaStatisticsRespBO> selectSummaryList =
				sqlOperations.findList("selectSummaryListByAreaId", MemberAreaStatisticsRespBO.class);

		List<MemberAreaStatisticsRespBO> selectSummaryList2 =
				sqlOperations.findList("selectSummaryListByAreaId2", MemberAreaStatisticsRespBO.class);

		Map<String, List<MemberAreaStatisticsRespBO>> map = new HashMap<>();
		map.put("selectSummaryList", selectSummaryList);
		map.put("selectSummaryList2", selectSummaryList2);
		return map;
	}
}

