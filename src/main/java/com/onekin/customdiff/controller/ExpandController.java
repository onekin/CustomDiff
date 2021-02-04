package com.onekin.customdiff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.service.ExpandService;

@Controller
public class ExpandController {

	@Autowired
	private ExpandService expandService;

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/product/{expandId}")
	public SankeyResponse expandProduct(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {
			return expandService.expandAggregatedProduct(sankeyResponse);

	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/parent-feature/{expandId}")
	public SankeyResponse expandParentFeature(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

		if (expandId.equalsIgnoreCase("ALL-F")) {
			return expandService.expandAggregatedParentFeature(sankeyResponse);
		} else {
			return expandService.expandParentFeature(sankeyResponse, expandId);
		}

	}


	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/feature/{expandId}")
	public SankeyResponse expandFeature(@PathVariable(name = "expandId") String expandId,
											  @RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

			return expandService.expandFeature(sankeyResponse, expandId);

	}


	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/left-package/{expandId}")
	public SankeyResponse expanLeftPackage(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse) {
		sankeyResponse.getNodes().removeIf(x -> x.getId().equals(expandId));
		sankeyResponse.removeLinksById(expandId);

		String packageId = expandId.split("-")[1];
		expandService.expandLeftPackage(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes(),
				packageId.substring(0, packageId.length() - 1));

		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());

	}
	
	
	
	
	

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/right-package/{expandId}")
	public SankeyResponse expanRighttPackage(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse) {
		sankeyResponse.deleteNodesAndLinksById(expandId);

		String packageId = expandId.split("-")[1];
		expandService.expandRightPackage(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes(),
				Integer.parseInt(packageId));

		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());

	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/left-packages/show")
	public SankeyResponse showLeftPackages(@RequestBody SankeyResponse sankeyResponse) {
		return expandService.showLeftPackages(sankeyResponse);
	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/right-packages/show")
	public SankeyResponse showRightPackages(@RequestBody SankeyResponse sankeyResponse) {
		return expandService.showRightPackages(sankeyResponse);
	}

}
