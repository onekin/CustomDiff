package com.onekin.customdiff.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.model.Churn_ParentFeatures_ProductComponents;
import com.onekin.customdiff.model.SankeyLink;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.repository.ChurnFeaturesPackageAssetsRepository;
import com.onekin.customdiff.repository.ChurnProductPortfolioAndFeaturesRepository;

@Service
public class SankeyService {

	private final String PRODUCT_PREFIX = "pr-";
	private final String PACKAGE_PREFIX = "pck-";
	private final String ASSET_PREFIX = "ca-";

	@Autowired
	private ChurnProductPortfolioAndFeaturesRepository productAndFeaturesRepo;

	@Autowired
	private ChurnFeaturesPackageAssetsRepository featuresAndAssetsRepo;

	@Autowired
	private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;

	@Autowired
	private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;
	
	
	@Autowired
	private Churn_ParentFeatures_ProductComponents

	public void setProductAndFeaturesChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		Iterator<ChurnProductPortfolioAndFeatures> it = productAndFeaturesRepo.findAll().iterator();
		ChurnProductPortfolioAndFeatures churnProdFeature;
		SankeyLink sankeyLink;

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

	}

	public Iterator<ChurnFeaturesPackageAssets> getFeaturesAndAssetsChurn() {
		return featuresAndAssetsRepo.findAll().iterator();
	}

	public Iterator<ChurnCoreAssetsAndFeaturesByProduct> getAssetsAndProductChurn() {
		return assetsAndProductRepo.findAll().iterator();
	}

	public void setFeaturesAndPackagesChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {

		SankeyLink sankeyLink;

		ChurnFeaturesAndPackagesGrouped churnFeaturesAssets;
		Iterator<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.getCustomsGroupByFeatures().iterator();

		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAssets.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyData.add(sankeyLink);
			}
		}
	}

	public void setPackagesAndProductsChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {

		Iterator<ChurnPackageAndProduct> it = assetsAndProductRepo.findGroupedByProductAndPackage().iterator();
		ChurnPackageAndProduct churnAssetsProducts;
		SankeyLink sankeyLink;

		while (it.hasNext()) {
			churnAssetsProducts = it.next();
			sankeyLink = new SankeyLink(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(), churnAssetsProducts.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyData.add(sankeyLink);
			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage(),
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
					churnAssetsProducts.getPrName(), false, false, SankeyNodeType.PRODUCT));

		}
	}

	public void setInitialSankeyNodesAndLinks(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		/*
		 * setPackagesAndProductsChurn(sankeyData, nodes);
		 * setProductAndFeaturesChurn(sankeyData, nodes);
		 * setFeaturesAndPackagesChurn(sankeyData, nodes);
		 */
		setAgregatedProductAndFeatureChurn(sankeyData, nodes);

	}

	private void setAgregatedProductAndFeatureChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		Integer agregatedChurn = productAndFeaturesRepo.getAgregatedChurn();
		SankeyLink sankeyLink = new SankeyLink("ALL-PR", "ALL-F", agregatedChurn, SankeyLinkType.PRODUCTFEATURE);
		sankeyData.add(sankeyLink);
		nodes.add(new SankeyNode("ALL-PR", "ALL PRODUCTS", true, false, SankeyNodeType.PRODUCT));
		nodes.add(new SankeyNode("ALL-F", "ALL FEATURES", true, false, SankeyNodeType.FEATURE));
	}

	public void expandLeftPackage(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, String idPackage) {
		Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = assetsAndProductRepo
				.findByPackageIdGroupByAssetId(Integer.parseInt(idPackage)).iterator();
		SankeyLink sankeyLink;

		ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
		SankeyNode node;

		while (it.hasNext()) {
			assetsAndFeatureChurn = it.next();
			sankeyLink = new SankeyLink(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(), assetsAndFeatureChurn.getChurn(),
					SankeyLinkType.ASSETPRODUCT);
			sankeyLinks.add(sankeyLink);
			node = new SankeyNode(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.ASSET);
			node.setParentId(PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
			nodes.add(node);
		}
	}

	public void expandRightPackage(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, String idPackage) {
		SankeyLink sankeyLink;

		Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
				.findByPackageIdGroupedByFeaturesAndAsset(Integer.parseInt(idPackage)).iterator();

		ChurnFeaturesPackageAssets churnFeaturesAssets;
		SankeyNode node;
		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getFeatureId().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAssets.getFeatureId(),
						ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		}
	}

	public void expandLeftPackageFiltered(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, String idPackage,
			List<String> featureIds) {
		Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = assetsAndProductRepo
				.findByPackageIdAndFeaturesInGroupByAssetId(Integer.parseInt(idPackage), featureIds).iterator();
		SankeyLink sankeyLink;

		ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
		SankeyNode node;

		while (it.hasNext()) {
			assetsAndFeatureChurn = it.next();
			sankeyLink = new SankeyLink(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(), assetsAndFeatureChurn.getChurn(),
					SankeyLinkType.ASSETPRODUCT);
			sankeyLinks.add(sankeyLink);
			node = new SankeyNode(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.ASSET);
			node.setParentId(PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
			nodes.add(node);
		}
	}

	public void expandRightPackageFiltered(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, String packageId,
			List<String> featureIds) {
		SankeyLink sankeyLink;

		Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
				.findByPackageIdAndFeaturesInGroupedByFeaturesAndAsset(Integer.parseInt(packageId), featureIds)
				.iterator();

		ChurnFeaturesPackageAssets churnFeaturesAssets;
		SankeyNode node;
		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getFeatureId().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAssets.getFeatureId(),
						ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		}
	}

	public void collapseLeftFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackage(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyLink = new SankeyLink(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(), packageAndProductChurn.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyLinks.add(sankeyLink);
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageGroupByFeatures(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAndPackage.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(), churnFeaturesAndPackage.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyLinks.add(sankeyLink);

			}
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseLeftFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks,
			String packageId, List<String> featureIds) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackageAndFeaturesIn(Integer.valueOf(packageId), featureIds);
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyLink = new SankeyLink(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(), packageAndProductChurn.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyLinks.add(sankeyLink);
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks,
			String packageId, List<String> featureIds) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageAndFeaturesInGroupByFeatures(Integer.valueOf(packageId), featureIds);
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAndPackage.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(), churnFeaturesAndPackage.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyLinks.add(sankeyLink);

			}
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public SankeyResponse expandAggregatedPackage(SankeyResponse sankeyResponse) {
		if (sankeyResponse.getNodes().stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE)
				.count() > 1) {
			setProductAndFeaturesChurn(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());
			return sankeyResponse;
		} else {
			List<ChurnProductPortfolioAndFeatures> churnProdAndFeatureList = productAndFeaturesRepo
					.findAllAggregatedInFeature();
			List<SankeyLink> sankeyLinks = new ArrayList<>();
			Set<SankeyNode> nodes = new HashSet<>();
			for (ChurnProductPortfolioAndFeatures churnProductAndFeature : churnProdAndFeatureList) {
				SankeyLink sankeyLink = new SankeyLink(PRODUCT_PREFIX + churnProductAndFeature.getId_pr(), "ALL-F",
						churnProductAndFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyLinks.add(sankeyLink);
				nodes.add(new SankeyNode(PRODUCT_PREFIX+churnProductAndFeature.getId_pr(), churnProductAndFeature.getPr_name(), false,
						false, SankeyNodeType.PRODUCT));
			}
			nodes.add(new SankeyNode("ALL-F", "ALL FEATURES", true, false, SankeyNodeType.FEATURE));
			return new SankeyResponse(sankeyLinks, nodes);
		}

	}

	public SankeyResponse expandAggregatedParentFeature(SankeyResponse sankeyResponse) {
		if (sankeyResponse.getNodes().stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.PRODUCT)
				.count() > 1) {
			setProductAndFeaturesChurn(sankeyResponse.getSankeyItems(), sankeyResponse.getNodes());
			return sankeyResponse;
		} else {
			List<ChurnProductPortfolioAndFeatures> churnProdAndFeatureList = productAndFeaturesRepo
					.findAllAggregatedInFeature();
			List<SankeyLink> sankeyLinks = new ArrayList<>();
			Set<SankeyNode> nodes = new HashSet<>();
			for (ChurnProductPortfolioAndFeatures churnProductAndFeature : churnProdAndFeatureList) {
				SankeyLink sankeyLink = new SankeyLink(PRODUCT_PREFIX + churnProductAndFeature.getId_pr(), "ALL-F",
						churnProductAndFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyLinks.add(sankeyLink);
				nodes.add(new SankeyNode(PRODUCT_PREFIX+churnProductAndFeature.getId_pr(), churnProductAndFeature.getPr_name(), false,
						false, SankeyNodeType.PRODUCT));
			}
			nodes.add(new SankeyNode("ALL-F", "ALL FEATURES", true, false, SankeyNodeType.FEATURE));
			return new SankeyResponse(sankeyLinks, nodes);
		}		return null;
	}

}
