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
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesComponentPackages;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.model.SankeyItem;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.service.SankeyService;
import com.onekin.customdiff.service.EntityService;

@Controller
public class SankeyController {

	private final String PRODUCT_PREFIX = "pr-";
	private final String PACKAGE_PREFIX = "pck-";
	private final String ASSET_PREFIX = "ca-";

	@Autowired
	private SankeyService mainService;

	@Autowired
	private EntityService entityService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("products", entityService.getProducts());
		model.addAttribute("features", entityService.getFeatures());
		model.addAttribute("componentPackages", entityService.getPackages());

		List<SankeyItem> productToFeatureSankey = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();

		SankeyItem sankeyItem;

		ChurnPackageAndProduct churnAssetsProducts;
		Iterator<ChurnPackageAndProduct> it3 = mainService.getPackagesAndProductsChurn();

		while (it3.hasNext()) {
			churnAssetsProducts = it3.next();
			sankeyItem = new SankeyItem(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(), churnAssetsProducts.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			productToFeatureSankey.add(sankeyItem);

			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage(),
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
					churnAssetsProducts.getPrName(), false, false, SankeyNodeType.PRODUCT));

		}

		Iterator<ChurnProductPortfolioAndFeatures> it = mainService.getProductAndFeaturesChurn();
		ChurnProductPortfolioAndFeatures churnProdFeature;
		while (it.hasNext()) {
			churnProdFeature = it.next();
			if (!churnProdFeature.getId_feature().equals("No Feature")) {
				sankeyItem = new SankeyItem(PRODUCT_PREFIX + churnProdFeature.getId_pr(),
						churnProdFeature.getId_feature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				productToFeatureSankey.add(sankeyItem);
				nodes.add(new SankeyNode(churnProdFeature.getId_feature(), churnProdFeature.getFeaturemodified(), false,
						false, SankeyNodeType.FEATURE));
			}
		}

		ChurnFeaturesAndPackagesGrouped churnFeaturesAssets;
		Iterator<ChurnFeaturesAndPackagesGrouped> it2 = mainService.getFeaturesAndPackagesChurn();

		while (it2.hasNext()) {
			churnFeaturesAssets = it2.next();
			if (!churnFeaturesAssets.getIdfeature().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAssets.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				productToFeatureSankey.add(sankeyItem);
			}
		}

		session.setAttribute("sankeyData", productToFeatureSankey);
		session.setAttribute("nodes", nodes);
		model.addAttribute("sankeyData", productToFeatureSankey);
		model.addAttribute("nodes", nodes);
		return "index";
	}

	@ResponseBody
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, path = "/nodes/{expandId}")
	public SankeyResponse expandNode(@PathVariable(name = "expandId") String expandId, HttpSession session) {

		List<SankeyItem> sankeyData = (List<SankeyItem>) session.getAttribute("sankeyData");
		Set<SankeyNode> nodes = (Set<SankeyNode>) session.getAttribute("nodes");
		nodes.removeIf(x -> x.getId().equals(expandId));
		sankeyData = SankeyItem.deleteFromListById(sankeyData, expandId);
		// TODO implementar segun tipo de artefacto
		String artefactId = expandId.split("-")[1];
		SankeyItem sankeyItem;
		if (artefactId.contains("'")) {
			Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = mainService
					.getAssetsAndProductChurnByPackage(artefactId.substring(0, artefactId.length() - 1));
			ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
			SankeyNode node;

			while (it.hasNext()) {
				assetsAndFeatureChurn = it.next();
				sankeyItem = new SankeyItem(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(), assetsAndFeatureChurn.getChurn(),
						SankeyLinkType.ASSETPRODUCT);
				sankeyData.add(sankeyItem);
				node = new SankeyNode(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
				nodes.add(node);
			}
		} else {
			Iterator<ChurnFeaturesPackageAssets> it = mainService.getAssetsAndFeaturesChurnByPackage(artefactId);
			ChurnFeaturesPackageAssets churnFeaturesAssets;
			SankeyNode node;
			while (it.hasNext()) {
				churnFeaturesAssets = it.next();
				if (!churnFeaturesAssets.getFeatureId().equals("No Feature")) {
					sankeyItem = new SankeyItem(churnFeaturesAssets.getFeatureId(),
							ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
							SankeyLinkType.FEATUREASSET);
					sankeyData.add(sankeyItem);
					node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
							churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
					node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
					nodes.add(node);
				}
			}
		}

		return new SankeyResponse(sankeyData, nodes);

	}

	@ResponseBody
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, path = "/collapse/{collapseId}")
	public SankeyResponse collapseNodes(@PathVariable(name = "collapseId") String collapseId, HttpSession session) {

		List<SankeyItem> sankeyData = (List<SankeyItem>) session.getAttribute("sankeyData");
		Set<SankeyNode> nodes = (Set<SankeyNode>) session.getAttribute("nodes");
		List<SankeyNode> nodesToRemove = nodes.stream()
				.filter(x -> x.getParentId() != null && x.getParentId().equals(collapseId))
				.collect(Collectors.toList());
		nodes.removeAll(nodesToRemove);
		sankeyData = SankeyItem.deleteFromListByParentId(sankeyData, nodesToRemove);

		// TODO implementar segun tipo de artefacto
		String artefactId = collapseId.split("-")[1];
		SankeyItem sankeyItem;

		if (artefactId.contains("'")) {
			List<ChurnPackageAndProduct> churnPackageAndProductList = mainService
					.getPackagesAndProductsChurnByPackage(artefactId.substring(0, artefactId.length() - 1));
			SankeyNode node;

			for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
				sankeyItem = new SankeyItem(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
						PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(),
						packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
				sankeyData.add(sankeyItem);
				node = new SankeyNode(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
						packageAndProductChurn.getPackageName(), true, false, SankeyNodeType.PACKAGE);
				nodes.add(node);
			}
		} else {
			List<ChurnFeaturesComponentPackages> churnFeaturesPackagesList = mainService
					.getFeaturesAndPackagesChurnByPackage(artefactId);
			SankeyNode node;

			for (ChurnFeaturesComponentPackages churnFeaturesAndPackage : churnFeaturesPackagesList) {
				if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
					sankeyItem = new SankeyItem(churnFeaturesAndPackage.getIdfeature(),
							PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(), churnFeaturesAndPackage.getChurn(),
							SankeyLinkType.FEATUREPACKAGE);
					sankeyData.add(sankeyItem);
					node = new SankeyNode(PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(),
							churnFeaturesAndPackage.getPackage_name(), true, false, SankeyNodeType.PACKAGE);
					nodes.add(node);
				}
			}

		}

		return new SankeyResponse(sankeyData, nodes);

	}

}
