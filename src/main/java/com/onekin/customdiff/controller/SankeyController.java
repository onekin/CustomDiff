package com.onekin.customdiff.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.model.SankeyLink;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.service.EntityService;
import com.onekin.customdiff.service.SankeyFilterService;
import com.onekin.customdiff.service.SankeyService;

@Controller
public class SankeyController {

	private final String PRODUCT_PREFIX = "pr-";
	private final String PACKAGE_PREFIX = "pck-";
	private final String ASSET_PREFIX = "ca-";

	@Autowired
	private SankeyService mainService;

	@Autowired
	private SankeyFilterService sankeyFilterService;

	@Autowired
	private EntityService entityService;

	@GetMapping("/")
	public String loadInitialData(Model model) {
		model.addAttribute("features", entityService.getCustomizedFeatures());
		List<SankeyLink> sankeyInitialLinks = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();
		mainService.setInitialSankeyNodesAndLinks(sankeyInitialLinks, nodes);
		model.addAttribute("sankeyData", sankeyInitialLinks);
		model.addAttribute("nodes", nodes);
		return "index";
	}

	/*
	 * @GetMapping("/") public String loadInitialData(Model model) {
	 * model.addAttribute("features", entityService.getCustomizedFeatures());
	 * List<SankeyItem> sankeyInitialLinks = new ArrayList<>(); Set<SankeyNode>
	 * nodes = new HashSet<>();
	 * mainService.setInitialSankeyNodesAndLinks(sankeyInitialLinks, nodes);
	 * model.addAttribute("sankeyData", sankeyInitialLinks);
	 * model.addAttribute("nodes", nodes); return "index"; }
	 */

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/product/{expandId}")
	public SankeyResponse expandProduct(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

		if (expandId.equalsIgnoreCase("ALL-PR")) {
			return mainService.expandAggregatedPackage(sankeyResponse);
		} else {
		
		}

		return new SankeyResponse(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());

	}
	
	
	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/expand/product/{expandId}")
	public SankeyResponse expandParentFeature(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

		if (expandId.equalsIgnoreCase("ALL-F")) {
			return mainService.expandAggregatedParentFeature(sankeyResponse);
		} else {
		
		}

		return new SankeyResponse(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());

	}
	
	

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/nodes/{expandId}")
	public SankeyResponse expandNode(@PathVariable(name = "expandId") String expandId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

		sankeyResponse.getNodes().removeIf(x -> x.getId().equals(expandId));
		sankeyResponse.removeLinksById(expandId);
		// TODO implementar segun tipo de artefacto ???

		String packageId = expandId.split("-")[1];
		if (packageId.contains("'")) {
			if (featureIds.size() <= 0) {
				mainService.expandLeftPackage(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes(),
						packageId.substring(0, packageId.length() - 1));
			} else {
				mainService.expandLeftPackageFiltered(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes(),
						packageId.substring(0, packageId.length() - 1), featureIds);
			}
		} else {
			if (featureIds.size() <= 0) {

				mainService.expandRightPackage(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes(), packageId);
			} else {
				mainService.expandRightPackageFiltered(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes(),
						packageId, featureIds);

			}

		}

		return new SankeyResponse(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());

	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/{collapseId}")
	public SankeyResponse collapseNodes(@PathVariable(name = "collapseId") String collapseId,
			@RequestBody SankeyResponse sankeyResponse, @RequestParam(name = "features") List<String> featureIds) {

		List<SankeyNode> nodesToRemove = sankeyResponse.getNodes().stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(collapseId))
				.collect(Collectors.toList());
		sankeyResponse.getNodes().removeAll(nodesToRemove);
		sankeyResponse.removeLinksByParentId(nodesToRemove);

		// TODO implementar segun tipo de artefacto
		String packageId = collapseId.split("-")[1];

		if (packageId.contains("'")) {
			if (featureIds.size() <= 0) {

				mainService.collapseLeftFilesIntoPackage(sankeyResponse.getNodes(), sankeyResponse.getSankeyItems(),
						packageId.substring(0, packageId.length() - 1));
			} else {
				mainService.collapseLeftFilesIntoPackageFiltered(sankeyResponse.getNodes(),
						sankeyResponse.getSankeyItems(), packageId.substring(0, packageId.length() - 1), featureIds);
			}
		} else {
			if (featureIds.size() <= 0) {

				mainService.collapseRightFilesIntoPackage(sankeyResponse.getNodes(), sankeyResponse.getSankeyItems(),
						packageId);
			} else {
				mainService.collapseRightFilesIntoPackageFiltered(sankeyResponse.getNodes(),
						sankeyResponse.getSankeyItems(), packageId, featureIds);
			}

		}

		return new SankeyResponse(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());
	}

	@ResponseBody
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/filter-features")
	public SankeyResponse filterSankeyByFeatures(@RequestParam(name = "features") List<String> featureIds,
			HttpSession session, @RequestBody List<SankeyLink> currentSankeyLinks) {
		List<SankeyLink> sankeyData = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();

		SankeyLink sankeyLink;

		// Calculate left package ids to maintain the expand and collapse filters
		Set<String> leftPackages = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.PACKAGEPRODUCT).map(SankeyLink::getFrom)
				.collect(Collectors.toSet());

		// Calculate all the package ids to find possible new packages and maintain
		// expand and collapse filters
		Set<String> allLeftPackages = new HashSet<String>();
		allLeftPackages.addAll(leftPackages);

		// Calculate left assets ids to maintain the expand and collapse filters
		Set<String> leftAssets = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.ASSETPRODUCT).map(SankeyLink::getFrom)
				.collect(Collectors.toSet());

		// Asset - Product
		if (leftAssets.size() > 0) {
			List<ChurnCoreAssetsAndFeaturesByProduct> it4 = sankeyFilterService
					.getAssetsAndProductsChurnInFeaturesAndInPackages(featureIds, leftAssets);
			SankeyNode node;
			for (ChurnCoreAssetsAndFeaturesByProduct churnCoreAssetsAndFeaturesByProduct : it4) {

				sankeyLink = new SankeyLink(ASSET_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdcoreasset() + "'",
						PRODUCT_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdproductrelease(),
						churnCoreAssetsAndFeaturesByProduct.getChurn(), SankeyLinkType.ASSETPRODUCT);
				sankeyData.add(sankeyLink);
				node = new SankeyNode(ASSET_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdcoreasset() + "'",
						churnCoreAssetsAndFeaturesByProduct.getCa_path(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnCoreAssetsAndFeaturesByProduct.getPackageId() + "'");
				nodes.add(new SankeyNode(PRODUCT_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdproductrelease(),
						churnCoreAssetsAndFeaturesByProduct.getPr_name(), false, false, SankeyNodeType.PRODUCT));
				allLeftPackages.add(PACKAGE_PREFIX + churnCoreAssetsAndFeaturesByProduct.getPackageId() + "'");
				nodes.add(node);
			}
		}

		// PAckage - Product
		if (allLeftPackages.size() > 0) {
			List<ChurnPackageAndProduct> it3 = new ArrayList<>();
			if (leftPackages.size() > 0) {
				it3.addAll(sankeyFilterService.getPackagesAndProductsChurnInFeaturesAndInPackages(featureIds,
						leftPackages));
			}
			it3.addAll(sankeyFilterService.getPackagesAndProductsChurnInFeaturesAndNotInExistingPackages(featureIds,
					allLeftPackages));

			for (ChurnPackageAndProduct churnAssetsProducts : it3) {

				sankeyLink = new SankeyLink(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(), churnAssetsProducts.getChurn(),
						SankeyLinkType.PACKAGEPRODUCT);
				sankeyData.add(sankeyLink);

				nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
				nodes.add(new SankeyNode(PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
						churnAssetsProducts.getPrName(), false, false, SankeyNodeType.PRODUCT));

			}
		}

		// Product - Feature
		Iterator<ChurnProductPortfolioAndFeatures> it = sankeyFilterService
				.getProductAndFeaturesChurnInFeatures(featureIds);
		ChurnProductPortfolioAndFeatures churnProdFeature;
		while (it.hasNext()) {
			churnProdFeature = it.next();
			if (!churnProdFeature.getIdFeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(PRODUCT_PREFIX + churnProdFeature.getId_pr(),
						churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyData.add(sankeyLink);
				nodes.add(new SankeyNode(churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), false,
						false, SankeyNodeType.FEATURE));
			}
		}

		Set<String> rightPackages = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.FEATUREPACKAGE).map(SankeyLink::getTo)
				.collect(Collectors.toSet());
		// Calculate all the package ids to find possible new packages and maintain
		// expand and collapse filters
		Set<String> allRightPackages = new HashSet<String>();
		allRightPackages.addAll(rightPackages);

		// Feature - Assets
		Set<String> rightAssets = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.FEATUREASSET).map(SankeyLink::getTo)
				.collect(Collectors.toSet());
		if (rightAssets.size() > 0) {
			SankeyNode node;

			List<ChurnFeaturesPackageAssets> featuresAndAssetsChurns = sankeyFilterService
					.getFeaturesAndAssetsChurnInFeaturesAndInAssets(featureIds, rightAssets);
			for (ChurnFeaturesPackageAssets churnFeaturesAssets : featuresAndAssetsChurns) {
				sankeyLink = new SankeyLink(churnFeaturesAssets.getFeatureId(),
						ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyData.add(sankeyLink);
				node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				allRightPackages.add(node.getParentId());
				nodes.add(node);

			}
		}

		// Feature - PAckages
		if (allRightPackages.size() > 0) {
			List<ChurnFeaturesAndPackagesGrouped> it2 = new ArrayList<>();
			if (rightPackages.size() > 0) {
				it2.addAll(sankeyFilterService.getFeaturesAndPackagesChurnInFeaturesAndInPackages(featureIds,
						rightPackages));
			}
			it2.addAll(sankeyFilterService.getFeaturesAndPackagesChurnInFeaturesAndNotInPackages(featureIds,
					allRightPackages));
			for (ChurnFeaturesAndPackagesGrouped churnFeaturesPackages : it2) {
				sankeyLink = new SankeyLink(churnFeaturesPackages.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesPackages.getIdpackage(), churnFeaturesPackages.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyData.add(sankeyLink);
				nodes.add(new SankeyNode(PACKAGE_PREFIX + churnFeaturesPackages.getIdpackage(),
						churnFeaturesPackages.getPackage_name(), true, false, SankeyNodeType.PACKAGE));

			}
		}

		return new SankeyResponse(sankeyData, nodes);
	}

	@ResponseBody
	@GetMapping(path = "/clear-feature-filters", produces = { MediaType.APPLICATION_JSON_VALUE })
	public SankeyResponse clearFeatureFilters(Model model) {
		List<SankeyLink> sankeyInitialLinks = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();
		mainService.setInitialSankeyNodesAndLinks(sankeyInitialLinks, nodes);
		return new SankeyResponse(sankeyInitialLinks, nodes);
	}
}
