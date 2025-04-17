package com.awesomecopilot.cloud.statistic.controller;

import com.awesomecopilot.cloud.statistic.bo.MemberAreaStatisticsRespBO;
import com.awesomecopilot.cloud.statistic.service.MemberStatisticService;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "管理后台 - 会员统计")
@Slf4j
@Validated
@RestController
@RequestMapping("/statistic/member")

public class MemberStatisticController {

	@Autowired
	private MemberStatisticService memberStatisticService;
	@GetMapping("/area-statistics-list")
	@Operation(summary = "按照省份，获得会员统计列表")
	//@PreAuthorize("@ss.hasPermission('statistics:member:query')")
	public Result<Map<String, List<MemberAreaStatisticsRespBO>>> getMemberAreaStatisticsList() {
		Map<String, List<MemberAreaStatisticsRespBO>> memberAreaStatisticsListMap =
				memberStatisticService.getMemberAreaStatisticsList();
		return Results.<Map<String, List<MemberAreaStatisticsRespBO>>>success().data(memberAreaStatisticsListMap).build();
	}
}
