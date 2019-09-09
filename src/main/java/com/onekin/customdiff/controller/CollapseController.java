package com.onekin.customdiff.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.service.CollapseService;

@Controller
public class CollapseController {

	@Autowired
	private CollapseService collapseService;

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/{collapseId}")
	public SankeyResponse collapseNodes(@PathVariable(name = "collapseId") String collapseId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") Set<String> featureIds) {

		List<SankeyNode> nodesToRemove = sankeyResponse.getNodes().stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(collapseId))
				.collect(Collectors.toList());
		sankeyResponse.getNodes().removeAll(nodesToRemove);
		sankeyResponse.removeLinksByParentId(nodesToRemove);

		// TODO implementar segun tipo de artefacto
		String packageId = collapseId.split("-")[1];

		if (packageId.contains("'")) {
			if (featureIds.size() <= 0) {

				collapseService.collapseLeftFilesIntoPackage(sankeyResponse.getNodes(), sankeyResponse.getSankeyLinks(),
						packageId.substring(0, packageId.length() - 1));
			} else {
				collapseService.collapseLeftFilesIntoPackageFiltered(sankeyResponse.getNodes(),
						sankeyResponse.getSankeyLinks(), packageId.substring(0, packageId.length() - 1), featureIds);
			}
		} else {
			if (featureIds.size() <= 0) {

				collapseService.collapseRightFilesIntoPackage(sankeyResponse.getNodes(),
						sankeyResponse.getSankeyLinks(), packageId);
			} else {
				collapseService.collapseLeftFilesIntoPackageFiltered(sankeyResponse.getNodes(),
						sankeyResponse.getSankeyLinks(), packageId, featureIds);
			}

		}

		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());
	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/left-asset/{collapseId}")
	public SankeyResponse collapseLeftAsset(@PathVariable(name = "collapseId") String collapseId,
			@RequestBody SankeyResponse sankeyResponse) {

		List<SankeyNode> nodesToRemove = sankeyResponse.getNodes().stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(collapseId))
				.collect(Collectors.toList());
		sankeyResponse.getNodes().removeAll(nodesToRemove);
		sankeyResponse.removeLinksByParentId(nodesToRemove);

		String packageId = collapseId.split("-")[1];
		collapseService.collapseLeftFilesIntoPackage(sankeyResponse.getNodes(), sankeyResponse.getSankeyLinks(),
				packageId.substring(0, packageId.length() - 1));

		// collapseService.collapseLeftFilesIntoPackageFiltered(sankeyResponse.getNodes(),
		// sankeyResponse.getSankeyLinks(), packageId.substring(0, packageId.length() -
		// 1), featureIds);

		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());
	}
	
	
	
	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/right-asset/{collapseId}")
	public SankeyResponse collapseRightAsset(@PathVariable(name = "collapseId") String collapseId,
			@RequestBody SankeyResponse sankeyResponse) {

		List<SankeyNode> nodesToRemove = sankeyResponse.getNodes().stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(collapseId))
				.collect(Collectors.toList());
		sankeyResponse.getNodes().removeAll(nodesToRemove);
		sankeyResponse.removeLinksByParentId(nodesToRemove);

		String packageId = collapseId.split("-")[1];
		collapseService.collapseRightFilesIntoPackage(sankeyResponse.getNodes(), sankeyResponse.getSankeyLinks(),
				packageId);



		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());
	}
	
	
	
	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/feature/{collapseId}")
	public SankeyResponse collapseFeature(@PathVariable(name = "collapseId") String parentFeatureId,
			@RequestBody SankeyResponse sankeyResponse) {

		List<SankeyNode> nodesToRemove = sankeyResponse.getNodes().stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(parentFeatureId))
				.collect(Collectors.toList());
		sankeyResponse.getNodes().removeAll(nodesToRemove);
		sankeyResponse.removeLinksByParentId(nodesToRemove);

		collapseService.collapseFeature(sankeyResponse,
				parentFeatureId);



		return new SankeyResponse(sankeyResponse.getSankeyLinks(), sankeyResponse.getNodes());
	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/left-packages/collapse")
	public SankeyResponse collapseLeftPackages(@RequestBody SankeyResponse sankeyResponse) {
		sankeyResponse.getNodes().removeIf(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTPACKAGE
				|| x.getSankeyNodeType() == SankeyNodeType.LEFTASSET);
		sankeyResponse.getSankeyLinks().removeIf(x -> x.getSankeyLinkType() == SankeyLinkType.ASSETPRODUCT
				|| x.getSankeyLinkType() == SankeyLinkType.PACKAGEPRODUCT);
		return sankeyResponse;
	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/right-packages/collapse")
	public SankeyResponse collapseRightPackages(@RequestBody SankeyResponse sankeyResponse) {
		sankeyResponse.getNodes().removeIf(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE
				|| x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET);
		sankeyResponse.getSankeyLinks()
				.removeIf(x -> x.getSankeyLinkType() == SankeyLinkType.FEATUREASSET
						|| x.getSankeyLinkType() == SankeyLinkType.FEATUREPACKAGE
						|| x.getSankeyLinkType() == SankeyLinkType.PARENTFEATUREASSET
						|| x.getSankeyLinkType() == SankeyLinkType.PARENTFEATUREPACKAGE);
		return sankeyResponse;
	}
}
