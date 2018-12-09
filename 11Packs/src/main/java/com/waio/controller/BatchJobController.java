package com.waio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.NewMatchesData;
import com.waio.service.IBatchJobService;

@RestController
@RequestMapping({"/api/job"})
public class BatchJobController {

	@Autowired
	private IBatchJobService batchJobService;
	
	@GetMapping("/1.0/matchesAPI")
	public NewMatchesData matchesAPI() {
		return batchJobService.insertNewMatches();
	}
}
