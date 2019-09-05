package com.onekin.customdiff.service;

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
import com.onekin.customdiff.model.SankeyItem;
import com.onekin.customdiff.model.SankeyNode;
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

	public void setProductAndFeaturesChurn(List<SankeyItem> sankeyData, Set<SankeyNode> nodes) {
		Iterator<ChurnProductPortfolioAndFeatures> it = productAndFeaturesRepo.findAll().iterator();
		ChurnProductPortfolioAndFeatures churnProdFeature;
		SankeyItem sankeyItem;

		while (it.hasNext()) {
			churnProdFeature = it.next();
			if (!churnProdFeature.getIdFeature().equals("No Feature")) {
				sankeyItem = new SankeyItem(PRODUCT_PREFIX + churnProdFeature.getId_pr(),
						churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyData.add(sankeyItem);
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

	public void setFeaturesAndPackagesChurn(List<SankeyItem> sankeyData, Set<SankeyNode> nodes) {

		SankeyItem sankeyItem;

		ChurnFeaturesAndPackagesGrouped churnFeaturesAssets;
		Iterator<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.getCustomsGroupByFeatures().iterator();

		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getIdfeature().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAssets.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyData.add(sankeyItem);
			}
		}
	}

	public void setPackagesAndProductsChurn(List<SankeyItem> sankeyData, Set<SankeyNode> nodes) {

		Iterator<ChurnPackageAndProduct> it = assetsAndProductRepo.findGroupedByProductAndPackage().iterator();
		ChurnPackageAndProduct churnAssetsProducts;
		SankeyItem sankeyItem;

		while (it.hasNext()) {
			churnAssetsProducts = it.next();
			sankeyItem = new SankeyItem(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(), churnAssetsProducts.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyData.add(sankeyItem);
			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage(),
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
					churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.PACKAGE));
			nodes.add(new SankeyNode(PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
					churnAssetsProducts.getPrName(), false, false, SankeyNodeType.PRODUCT));

		}
	}

	public void setInitialSankeyNodesAndLinks(List<SankeyItem> sankeyData, Set<SankeyNode> nodes) {
		setPackagesAndProductsChurn(sankeyData, nodes);
		setProductAndFeaturesChurn(sankeyData, nodes);
		setFeaturesAndPackagesChurn(sankeyData, nodes);

	}

	public void expandLeftPackage(List<SankeyItem> sankeyItems, Set<SankeyNode> nodes, String idPackage) {
		Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = assetsAndProductRepo
				.findByPackageIdGroupByAssetId(Integer.parseInt(idPackage)).iterator();
		SankeyItem sankeyItem;

		ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
		SankeyNode node;

		while (it.hasNext()) {
			assetsAndFeatureChurn = it.next();
			sankeyItem = new SankeyItem(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(), assetsAndFeatureChurn.getChurn(),
					SankeyLinkType.ASSETPRODUCT);
			sankeyItems.add(sankeyItem);
			node = new SankeyNode(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.ASSET);
			node.setParentId(PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
			nodes.add(node);
		}
	}

	public void expandRightPackage(List<SankeyItem> sankeyItems, Set<SankeyNode> nodes, String idPackage) {
		SankeyItem sankeyItem;

		Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
				.findByPackageIdGroupedByFeaturesAndAsset(Integer.parseInt(idPackage)).iterator();

		ChurnFeaturesPackageAssets churnFeaturesAssets;
		SankeyNode node;
		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getFeatureId().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAssets.getFeatureId(),
						ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyItems.add(sankeyItem);
				node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		}
	}

	public void expandLeftPackageFiltered(List<SankeyItem> sankeyItems, Set<SankeyNode> nodes, String idPackage,
			List<String> featureIds) {
		Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = assetsAndProductRepo
				.findByPackageIdAndFeaturesInGroupByAssetId(Integer.parseInt(idPackage), featureIds).iterator();
		SankeyItem sankeyItem;

		ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
		SankeyNode node;

		while (it.hasNext()) {
			assetsAndFeatureChurn = it.next();
			sankeyItem = new SankeyItem(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(), assetsAndFeatureChurn.getChurn(),
					SankeyLinkType.ASSETPRODUCT);
			sankeyItems.add(sankeyItem);
			node = new SankeyNode(ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.ASSET);
			node.setParentId(PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
			nodes.add(node);
		}
	}

	public void expandRightPackageFiltered(List<SankeyItem> sankeyItems, Set<SankeyNode> nodes, String packageId,
			List<String> featureIds) {
		SankeyItem sankeyItem;

		Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
				.findByPackageIdAndFeaturesInGroupedByFeaturesAndAsset(Integer.parseInt(packageId), featureIds)
				.iterator();

		ChurnFeaturesPackageAssets churnFeaturesAssets;
		SankeyNode node;
		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getFeatureId().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAssets.getFeatureId(),
						ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
						SankeyLinkType.FEATUREASSET);
				sankeyItems.add(sankeyItem);
				node = new SankeyNode(ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.ASSET);
				node.setParentId(PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		}
	}

	public void collapseLeftFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyItem> sankeyItems, String packageId) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackage(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyItem sankeyItem;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyItem = new SankeyItem(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(), packageAndProductChurn.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyItems.add(sankeyItem);
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyItem> sankeyItems, String packageId) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageGroupByFeatures(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyItem sankeyItem;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAndPackage.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(), churnFeaturesAndPackage.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyItems.add(sankeyItem);

			}
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseLeftFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyItem> sankeyItems,
			String packageId, List<String> featureIds) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackageAndFeaturesIn(Integer.valueOf(packageId), featureIds);
		SankeyNode node;
		SankeyItem sankeyItem;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyItem = new SankeyItem(PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(), packageAndProductChurn.getChurn(),
					SankeyLinkType.PACKAGEPRODUCT);
			sankeyItems.add(sankeyItem);
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyItem> sankeyItems,
			String packageId, List<String> featureIds) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageAndFeaturesInGroupByFeatures(Integer.valueOf(packageId),featureIds);
		SankeyNode node;
		SankeyItem sankeyItem;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyItem = new SankeyItem(churnFeaturesAndPackage.getIdfeature(),
						PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(), churnFeaturesAndPackage.getChurn(),
						SankeyLinkType.FEATUREPACKAGE);
				sankeyItems.add(sankeyItem);

			}
		}
		node = new SankeyNode(PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.PACKAGE);
		nodes.add(node);
	}

}
