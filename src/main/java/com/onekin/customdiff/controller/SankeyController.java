package com.onekin.customdiff.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.onekin.customdiff.utils.PrefixConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


	@Autowired
	private SankeyService mainService;

	@Autowired
	private SankeyFilterService sankeyFilterService;

	@Autowired
	private EntityService entityService;

	@GetMapping("/")
	public String loadInitialData(@RequestParam(name = "featureName",required = false)String featureName,Model model) {
		List<SankeyLink> sankeyInitialLinks = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();

		if(featureName!=null){
			SankeyLink sankeyLink;
			Iterator<ChurnProductPortfolioAndFeatures> it = sankeyFilterService
					.getProductAndFeaturesChurnInFeatures(new HashSet<String>(Arrays.asList(featureName)));
			ChurnProductPortfolioAndFeatures churnProdFeature;
			while (it.hasNext()) {
				churnProdFeature = it.next();
				if (!churnProdFeature.getIdFeature().equals("No Feature")) {
					sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
							PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
					sankeyInitialLinks.add(sankeyLink);
					SankeyNode node=new SankeyNode(PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
							true, SankeyNodeType.FEATURE);
					node.setParentId(entityService.getFeatureParentId(featureName));
					nodes.add(node);
					nodes.add(new SankeyNode(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
							churnProdFeature.getProductName(), false, false, SankeyNodeType.PRODUCT));
				}
			}

		}else {
			mainService.setInitialSankeyNodesAndLinks(sankeyInitialLinks, nodes);
		}
		model.addAttribute("features", entityService.getCustomizedFeatures());
		model.addAttribute("sankeyData", sankeyInitialLinks);
		model.addAttribute("nodes", nodes);
		model.addAttribute("products", entityService.getProducts());
		model.addAttribute("componentPackages", entityService.getPackages());

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
			MediaType.APPLICATION_JSON_VALUE }, path = "/filter-features")
	public SankeyResponse filterSankeyByFeatures(@RequestParam(name = "features") Set<String> featureIds,
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

				sankeyLink = new SankeyLink(PrefixConstants.ASSET_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdcoreasset() + "'",
						PrefixConstants.PRODUCT_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdproductrelease(),
						churnCoreAssetsAndFeaturesByProduct.getChurn(), SankeyLinkType.ASSETPRODUCT);
				sankeyData.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdcoreasset() + "'",
						churnCoreAssetsAndFeaturesByProduct.getCa_path(), false, true, SankeyNodeType.LEFTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnCoreAssetsAndFeaturesByProduct.getPackageId() + "'");
				nodes.add(new SankeyNode(PrefixConstants.PRODUCT_PREFIX + churnCoreAssetsAndFeaturesByProduct.getIdproductrelease(),
						churnCoreAssetsAndFeaturesByProduct.getPr_name(), false, false, SankeyNodeType.PRODUCT));
				allLeftPackages.add(PrefixConstants.PACKAGE_PREFIX + churnCoreAssetsAndFeaturesByProduct.getPackageId() + "'");
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

				sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(), churnAssetsProducts.getChurn(),
						SankeyLinkType.PACKAGEPRODUCT);
				sankeyData.add(sankeyLink);

				nodes.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE));
				nodes.add(new SankeyNode(PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
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
				sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
						PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyData.add(sankeyLink);
				nodes.add(new SankeyNode(PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
						true, SankeyNodeType.FEATURE));
				nodes.add(new SankeyNode(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
						churnProdFeature.getProductName(), false, false, SankeyNodeType.PRODUCT));
			}
		}

		Set<String> rightPackages = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.FEATUREPACKAGE).map(SankeyLink::getTo)
				.collect(Collectors.toSet());
		rightPackages.addAll(currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.PARENTFEATUREPACKAGE).map(SankeyLink::getTo)
				.collect(Collectors.toSet()));
		// Calculate all the package ids to find possible new packages and maintain
		// expand and collapse filters
		Set<String> allRightPackages = new HashSet<String>();
		allRightPackages.addAll(rightPackages);

		// Feature - Assets
		Set<String> rightAssets = currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.FEATUREASSET).map(SankeyLink::getTo)
				.collect(Collectors.toSet());
		rightAssets.addAll(currentSankeyLinks.stream()
				.filter(x -> x.getSankeyLinkType() == SankeyLinkType.PARENTFEATUREASSET).map(SankeyLink::getTo)
				.collect(Collectors.toSet()));
		if (rightAssets.size() > 0) {
			SankeyNode node;

			List<ChurnFeaturesPackageAssets> featuresAndAssetsChurns = sankeyFilterService
					.getFeaturesAndAssetsChurnInFeaturesAndInAssets(featureIds, rightAssets);
			for (ChurnFeaturesPackageAssets churnFeaturesAssets : featuresAndAssetsChurns) {
				sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX+churnFeaturesAssets.getFeatureId(),
						PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyData.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.RIGHTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
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
				sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX + churnFeaturesPackages.getIdfeature(),
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesPackages.getIdpackage(), churnFeaturesPackages.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyData.add(sankeyLink);
				nodes.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesPackages.getIdpackage(),
						churnFeaturesPackages.getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE));

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
