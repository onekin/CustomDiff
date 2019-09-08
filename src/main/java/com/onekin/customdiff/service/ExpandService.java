package com.onekin.customdiff.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnParentFeaturesProductComponents;
import com.onekin.customdiff.model.ChurnParentFeaturesProductPortfolio;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.model.SankeyLink;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.SankeyResponse;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.repository.ChurnFeaturesPackageAssetsRepository;
import com.onekin.customdiff.repository.ChurnParentFeaturesProductComponentsRepository;
import com.onekin.customdiff.repository.ChurnParentFeaturesProductPortfolioRepository;
import com.onekin.customdiff.repository.ChurnProductPortfolioAndFeaturesRepository;
import com.onekin.customdiff.utils.Formatting;
import com.onekin.customdiff.utils.PrefixConstants;

@Service
public class ExpandService {

	private final String ALL_PRODUCTS = "ALL-PR";
	private final String ALL_FEATURES = "ALL-F";

	@Autowired
	private ChurnParentFeaturesProductPortfolioRepository churnParentFeaturesProductPortfolioRepo;

	@Autowired
	private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;

	@Autowired
	private ChurnProductPortfolioAndFeaturesRepository productAndFeaturesRepo;

	@Autowired
	private ChurnFeaturesPackageAssetsRepository featuresAndAssetsRepo;

	@Autowired
	private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;

	@Autowired
	private ChurnParentFeaturesProductComponentsRepository parentFeaturesAndPackagesRepo;

	public void expandLeftPackage(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, String idPackage) {
		Iterator<ChurnCoreAssetsAndFeaturesByProduct> it = assetsAndProductRepo
				.findByPackageIdGroupByAssetId(Integer.parseInt(idPackage)).iterator();
		SankeyLink sankeyLink;

		ChurnCoreAssetsAndFeaturesByProduct assetsAndFeatureChurn;
		SankeyNode node;
		if (nodes.stream().anyMatch(x -> x.getId().equals(ALL_PRODUCTS))) {
			while (it.hasNext()) {
				assetsAndFeatureChurn = it.next();
				sankeyLink = new SankeyLink(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						ALL_PRODUCTS, assetsAndFeatureChurn.getChurn(), SankeyLinkType.ASSETPRODUCT);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.LEFTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
				nodes.add(node);
			}
		} else {
			while (it.hasNext()) {
				assetsAndFeatureChurn = it.next();
				sankeyLink = new SankeyLink(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						PrefixConstants.PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(),
						assetsAndFeatureChurn.getChurn(), SankeyLinkType.ASSETPRODUCT);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
						assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.LEFTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
				nodes.add(node);
			}
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
						PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.FEATUREASSET);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.RIGHTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
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
			sankeyLink = new SankeyLink(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					PrefixConstants.PRODUCT_PREFIX + assetsAndFeatureChurn.getIdproductrelease(),
					assetsAndFeatureChurn.getChurn(), SankeyLinkType.ASSETPRODUCT);
			sankeyLinks.add(sankeyLink);
			node = new SankeyNode(PrefixConstants.ASSET_PREFIX + assetsAndFeatureChurn.getIdcoreasset() + "'",
					assetsAndFeatureChurn.getCa_path(), false, true, SankeyNodeType.LEFTASSET);
			node.setParentId(PrefixConstants.PACKAGE_PREFIX + assetsAndFeatureChurn.getPackageId() + "'");
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
						PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.FEATUREASSET);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.RIGHTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		}
	}

	public SankeyResponse expandAggregatedPackage(SankeyResponse sankeyResponse) {
		SankeyLink sankeyLink;

		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTASSET
				|| x.getSankeyNodeType() == SankeyNodeType.LEFTPACKAGE)) {
			Set<String> assetsIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTASSET).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			Set<String> packagesIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTPACKAGE).map(SankeyNode::getId)
					.collect(Collectors.toSet());

			if (!assetsIds.isEmpty()) {
				Iterable<ChurnCoreAssetsAndFeaturesByProduct> churnAssetsAndProductList = assetsAndProductRepo
						.findByIdcoreassetIn(Formatting.cleanListOfIds(assetsIds));
				for (ChurnCoreAssetsAndFeaturesByProduct churnAssetsProducts : churnAssetsAndProductList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.ASSET_PREFIX + churnAssetsProducts.getIdcoreasset() + "'",
							PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdproductrelease(),
							churnAssetsProducts.getChurn(), SankeyLinkType.ASSETPRODUCT);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdproductrelease(),
									churnAssetsProducts.getPr_name(), false, false, SankeyNodeType.PRODUCT));

				}
			}
			if (!packagesIds.isEmpty()) {

				List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
						.findByIdPackageIn(Formatting.cleanListOfIds(packagesIds));

				for (ChurnPackageAndProduct churnAssetsProducts : churnPackageAndProductList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
							PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
							churnAssetsProducts.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
									churnAssetsProducts.getPrName(), false, false, SankeyNodeType.PRODUCT));

				}
			}

		}
		sankeyResponse.deleteNodesAndLinksById(ALL_PRODUCTS);

		ChurnParentFeaturesProductPortfolio churnParentFeaturesProductPortfolio;

		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getId().equals(ALL_FEATURES))) {
			Iterator<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProducts = churnParentFeaturesProductPortfolioRepo
					.findAllGroupByProduct().iterator();
			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				sankeyLink = new SankeyLink(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						ALL_FEATURES, churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes().add(new SankeyNode(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						churnParentFeaturesProductPortfolio.getProductName(), false, false, SankeyNodeType.PRODUCT));

			}
			sankeyResponse.getNodes()
					.add(new SankeyNode(ALL_FEATURES, "ALL FEATURES", true, false, SankeyNodeType.PARENTFEATURE));
		} else {
			Iterator<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProducts = churnParentFeaturesProductPortfolioRepo
					.findAll().iterator();
			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				sankeyLink = new SankeyLink(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes().add(new SankeyNode(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						churnParentFeaturesProductPortfolio.getProductName(), false, false, SankeyNodeType.PRODUCT));
				sankeyResponse.getNodes()
						.add(new SankeyNode(
								PrefixConstants.PARENTFEATURE_PREFIX
										+ churnParentFeaturesProductPortfolio.getIdParentFeature(),
								churnParentFeaturesProductPortfolio.getParentFeatureName(), true, false,
								SankeyNodeType.PARENTFEATURE));
			}
		}
		return sankeyResponse;

	}

	public SankeyResponse expandAggregatedParentFeature(SankeyResponse sankeyResponse) {
		List<SankeyLink> sankeyLinks = new ArrayList<>();
		Set<SankeyNode> nodes = new HashSet<>();
		ChurnParentFeaturesProductPortfolio churnParentFeaturesProductPortfolio;
		Iterator<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProducts = churnParentFeaturesProductPortfolioRepo
				.findAll().iterator();
		if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {

			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				SankeyLink sankeyLink = new SankeyLink(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
				sankeyLinks.add(sankeyLink);
				nodes.add(new SankeyNode(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						churnParentFeaturesProductPortfolio.getProductName(), false, false, SankeyNodeType.PRODUCT));
				nodes.add(new SankeyNode(
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getParentFeatureName(), true, false,
						SankeyNodeType.PARENTFEATURE));
			}
		} else {
			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				SankeyLink sankeyLink = new SankeyLink(ALL_PRODUCTS,
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
				sankeyLinks.add(sankeyLink);
				nodes.add(new SankeyNode(
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getParentFeatureName(), true, false,
						SankeyNodeType.PARENTFEATURE));
			}
			nodes.add(new SankeyNode(ALL_PRODUCTS, "ALL PRODUCTS", true, false, SankeyNodeType.PRODUCT));

		}
		return new SankeyResponse(sankeyLinks, nodes);

	}

	public SankeyResponse expandParentFeature(SankeyResponse sankeyResponse, String expandId) {
		String parentFeatureId = expandId.split("-")[1];

		List<ChurnProductPortfolioAndFeatures> churnProductsAndFeaturesList = productAndFeaturesRepo
				.findByParentFeatureId(Integer.parseInt(parentFeatureId));
		SankeyLink sankeyLink;
		if (sankeyResponse.getNodes().stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET
				|| x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).count() > 0) {
		}
		sankeyResponse.deleteNodesAndLinksById(expandId);

		if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {

			for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {
				sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getId_pr(),
						churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes().add(new SankeyNode(churnProdFeature.getIdFeature(),
						churnProdFeature.getFeaturemodified(), true, true, SankeyNodeType.FEATURE));
				sankeyResponse.getNodes()
						.add(new SankeyNode(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getId_pr(),
								churnProdFeature.getPr_name(), false, false, SankeyNodeType.PRODUCT));

			}
		} else {

			for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {

				sankeyLink = new SankeyLink(ALL_PRODUCTS, churnProdFeature.getIdFeature(), churnProdFeature.getChurn(),
						SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes().add(new SankeyNode(churnProdFeature.getIdFeature(),
						churnProdFeature.getFeaturemodified(), false, false, SankeyNodeType.FEATURE));

			}
			sankeyResponse.getNodes()
					.add(new SankeyNode(ALL_PRODUCTS, "ALL PRODUCTS", true, false, SankeyNodeType.PRODUCT));

		}

		return sankeyResponse;
	}

	public SankeyResponse showLeftPackages(SankeyResponse sankeyResponse) {

		Iterator<ChurnPackageAndProduct> it = assetsAndProductRepo.findGroupedByProductAndPackage().iterator();
		ChurnPackageAndProduct churnAssetsProducts;
		SankeyLink sankeyLink;
		if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {
			while (it.hasNext()) {
				churnAssetsProducts = it.next();
				sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						PrefixConstants.PRODUCT_PREFIX + churnAssetsProducts.getIdProductRelease(),
						churnAssetsProducts.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes()
						.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
								churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE));

			}
		} else {
			while (it.hasNext()) {
				churnAssetsProducts = it.next();
				sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
						ALL_PRODUCTS, churnAssetsProducts.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes()
						.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnAssetsProducts.getIdPackage() + "'",
								churnAssetsProducts.getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE));

			}
		}

		return sankeyResponse;

	}

	public SankeyResponse showRightPackages(SankeyResponse sankeyResponse) {
		Set<String> parentFeaturesIds = sankeyResponse.getNodes().stream()
				.filter(x -> x.getSankeyNodeType() == SankeyNodeType.PARENTFEATURE).map(SankeyNode::getId)
				.collect(Collectors.toSet());
		Set<String> featureIds = sankeyResponse.getNodes().stream()
				.filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE).map(SankeyNode::getId)
				.collect(Collectors.toSet());
		SankeyLink sankeyLink;

		if (!parentFeaturesIds.contains(ALL_FEATURES)) {
			if (featureIds.size() > 0) {
				Iterable<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.findByIdfeatureIn(featureIds);
				for (ChurnFeaturesAndPackagesGrouped churnFeaturesAssets : it) {
					sankeyLink = new SankeyLink(churnFeaturesAssets.getIdfeature(),
							PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
							churnFeaturesAssets.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
									churnFeaturesAssets.getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE));
				}
			}
			if (parentFeaturesIds.size() > 0) {
				Set<Integer> parenteFeaturesCleaned = Formatting.cleanListOfIds(parentFeaturesIds);
				Iterable<ChurnParentFeaturesProductComponents> churnsParentFeaturesAndPackagesList = parentFeaturesAndPackagesRepo
						.findByParentFeatureIdsInGroupedByPackage(parenteFeaturesCleaned);
				for (ChurnParentFeaturesProductComponents churnsParentFeaturesAndPackages : churnsParentFeaturesAndPackagesList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.PARENTFEATURE_PREFIX
									+ churnsParentFeaturesAndPackages.getId_parentfeature(),
							PrefixConstants.PACKAGE_PREFIX + churnsParentFeaturesAndPackages.getIdpackage(),
							churnsParentFeaturesAndPackages.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PACKAGE_PREFIX + churnsParentFeaturesAndPackages.getIdpackage(),
									churnsParentFeaturesAndPackages.getPackage_name(), true, false,
									SankeyNodeType.RIGHTPACKAGE));
				}
			}

		} else {
			Iterable<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.getCustomsGroupByFeatures();
			for (ChurnFeaturesAndPackagesGrouped churnFeaturesAssets : it) {
				sankeyLink = new SankeyLink(ALL_FEATURES,
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.FEATUREPACKAGE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes()
						.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
								churnFeaturesAssets.getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE));
			}
		}

		return sankeyResponse;
	}

}
