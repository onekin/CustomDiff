package com.onekin.customdiff.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.onekin.customdiff.dao.ChurnFeatureSiblingsCoreAssetsDAO;
import com.onekin.customdiff.dao.ChurnFeaturesProductPortfolioDAO;
import com.onekin.customdiff.model.*;
import com.onekin.customdiff.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
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

	@Autowired
	private ChurnParentFeaturesAndCoreAssetsRepository parentFeaturesAndCoreAssetsRepo;

	@Autowired
	private ChurnFeaturesProductPortfolioDAO productAndFeatureSiblingDao;

	@Autowired
	private ChurnFeatureSiblingsCoreAssetsDAO churnFeatureSiblingsCoreAssetsDAO;

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
						ALL_PRODUCTS, assetsAndFeatureChurn.getChurn(), SankeyLinkType.ASSETALLPRODUCT);
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

	public void expandRightPackage(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes, int packageId) {
		SankeyLink sankeyLink;
		SankeyNode node;
		if (nodes.stream().anyMatch(x -> x.getId().equals(ALL_FEATURES))) {
			ChurnFeaturesPackageAssets churnFeaturesAssets;

			Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
					.findByPackageIdGroupedByFeaturesAndAsset(packageId).iterator();
			while (it.hasNext()) {

				churnFeaturesAssets = it.next();
				sankeyLink = new SankeyLink(ALL_FEATURES,
						PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.PARENTFEATUREASSET);
				sankeyLinks.add(sankeyLink);
				node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeaturesAssets.getIdcoreasset(),
						churnFeaturesAssets.getCapath(), false, true, SankeyNodeType.RIGHTASSET);
				node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getPackageId());
				nodes.add(node);
			}
		} else {
			Set<String> parentFeaturesIds = nodes.stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.PARENTFEATURE).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			if (!parentFeaturesIds.isEmpty()) {
				List<ChurnParentFeaturesAndCoreAssets> parentFeaturesAndCoreAssetsChurnList = parentFeaturesAndCoreAssetsRepo
						.findByPackageIdAndParentFeatureIdIn(Formatting.cleanListOfIds(parentFeaturesIds), packageId);
				for (ChurnParentFeaturesAndCoreAssets churnParentFeaturesAndCoreAssets : parentFeaturesAndCoreAssetsChurnList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.PARENTFEATURE_PREFIX
									+ churnParentFeaturesAndCoreAssets.getParentFeatureId(),
							PrefixConstants.ASSET_PREFIX + churnParentFeaturesAndCoreAssets.getCoreAssetId(),
							churnParentFeaturesAndCoreAssets.getChurn(), SankeyLinkType.PARENTFEATUREASSET);
					sankeyLinks.add(sankeyLink);
					node = new SankeyNode(
							PrefixConstants.ASSET_PREFIX + churnParentFeaturesAndCoreAssets.getCoreAssetId(),
							churnParentFeaturesAndCoreAssets.getCoreAssetPath(), false, true,
							SankeyNodeType.RIGHTASSET);
					node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnParentFeaturesAndCoreAssets.getPackageId());
					nodes.add(node);
				}
			}

			Set<String> featureIds = nodes.stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE)
					.map(x-> x.getId().split("-")[1]).collect(Collectors.toSet());

			if (!featureIds.isEmpty()) {
				List<ChurnFeaturesPackageAssets> featuresAndAssetsList = featuresAndAssetsRepo
						.findByPackageIdAndFeaturesInGroupedByFeaturesAndAsset(packageId, featureIds);

				for (ChurnFeaturesPackageAssets churnFeaturesAsset : featuresAndAssetsList) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX+churnFeaturesAsset.getFeatureId(),
							PrefixConstants.ASSET_PREFIX + churnFeaturesAsset.getIdcoreasset(),
							churnFeaturesAsset.getChurn(), SankeyLinkType.FEATUREASSET);
					sankeyLinks.add(sankeyLink);
					node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeaturesAsset.getIdcoreasset(),
							churnFeaturesAsset.getCapath(), false, true, SankeyNodeType.RIGHTASSET);
					node.setParentId(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAsset.getPackageId());
					nodes.add(node);
				}
			}

			Set<String> featureSiblingIds = nodes.stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURESIBLING)
					.map(x-> x.getId().split("-")[1]).collect(Collectors.toSet());
			if (!featureSiblingIds.isEmpty()) {

				for(String featureSiblingId:featureSiblingIds){
					List<ChurnFeatureSiblingsAndAssets> featuresSiblingAndAssetsList = churnFeatureSiblingsCoreAssetsDAO.getByPackageIdAndFeatureSiblingId(featureSiblingId,packageId);

					for(ChurnFeatureSiblingsAndAssets churnFeatureSiblingsAndAssets:featuresSiblingAndAssetsList) {
						sankeyLink = new SankeyLink(PrefixConstants.FEATURE_SIBLING + churnFeatureSiblingsAndAssets.getIdFeatureSibling(),
								PrefixConstants.ASSET_PREFIX + churnFeatureSiblingsAndAssets.getAssetId(),
								churnFeatureSiblingsAndAssets.getChurn(), SankeyLinkType.FEATURESIBLINGCOREASSET);
						sankeyLinks.add(sankeyLink);
						node = new SankeyNode(PrefixConstants.ASSET_PREFIX + churnFeatureSiblingsAndAssets.getAssetId(),
								churnFeatureSiblingsAndAssets.getAssetName(), false, true, SankeyNodeType.RIGHTASSET);
						node.setParentId(PrefixConstants.PACKAGE_PREFIX + packageId);
						nodes.add(node);
					}
				}

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
		/*
		 * SankeyLink sankeyLink;
		 * 
		 * Iterator<ChurnFeaturesPackageAssets> it = featuresAndAssetsRepo
		 * .findByPackageIdAndFeaturesInGroupedByFeaturesAndAsset(Integer.parseInt(
		 * packageId), featureIds) .iterator();
		 * 
		 * ChurnFeaturesPackageAssets churnFeaturesAssets; SankeyNode node; while
		 * (it.hasNext()) { churnFeaturesAssets = it.next(); if
		 * (!churnFeaturesAssets.getFeatureId().equals("No Feature")) { sankeyLink = new
		 * SankeyLink(churnFeaturesAssets.getFeatureId(), PrefixConstants.ASSET_PREFIX +
		 * churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getChurn(),
		 * SankeyLinkType.FEATUREASSET); sankeyLinks.add(sankeyLink); node = new
		 * SankeyNode(PrefixConstants.ASSET_PREFIX +
		 * churnFeaturesAssets.getIdcoreasset(), churnFeaturesAssets.getCapath(), false,
		 * true, SankeyNodeType.RIGHTASSET);
		 * node.setParentId(PrefixConstants.PACKAGE_PREFIX +
		 * churnFeaturesAssets.getPackageId()); nodes.add(node); } }
		 */
	}

	public SankeyResponse expandAggregatedProduct(SankeyResponse sankeyResponse) {
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
						ALL_FEATURES, churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes().add(new SankeyNode(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						churnParentFeaturesProductPortfolio.getProductName(), false, false, SankeyNodeType.PRODUCT));

			}
			sankeyResponse.getNodes()
					.add(new SankeyNode(ALL_FEATURES, "ALL FEATURES", true, false, SankeyNodeType.PARENTFEATURE));
		} else {
			Set<String> parentFeaturesIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.PARENTFEATURE).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			Set<String> featureIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE).map(x -> x.getId().split("-")[1])
					.collect(Collectors.toSet());

			Set<String> featureSiblingIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURESIBLING).map(x -> x.getId().split("-")[1])
					.collect(Collectors.toSet());
			if (!parentFeaturesIds.isEmpty()) {

				Iterator<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProducts = churnParentFeaturesProductPortfolioRepo
						.findByParentFeatureIdIn(Formatting.cleanListOfIds(parentFeaturesIds)).iterator();

				while (churnParentFeaturesAndProducts.hasNext()) {
					churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
					sankeyLink = new SankeyLink(
							PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
							PrefixConstants.PARENTFEATURE_PREFIX
									+ churnParentFeaturesProductPortfolio.getIdParentFeature(),
							churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
									churnParentFeaturesProductPortfolio.getProductName(), false, false,
									SankeyNodeType.PRODUCT));
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PARENTFEATURE_PREFIX
											+ churnParentFeaturesProductPortfolio.getIdParentFeature(),
									churnParentFeaturesProductPortfolio.getParentFeatureName(), true, false,
									SankeyNodeType.PARENTFEATURE));
				}
			}

			if (!featureIds.isEmpty()) {
				SankeyNode node;
				Iterator<ChurnProductPortfolioAndFeatures> it = productAndFeaturesRepo.findByIdFeatureIn(featureIds)
						.iterator();
				ChurnProductPortfolioAndFeatures churnProdFeature;

				while (it.hasNext()) {
					churnProdFeature = it.next();
					sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
							PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getChurn(),
							SankeyLinkType.PRODUCTFEATURE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					node = new SankeyNode(PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), false,
							false, SankeyNodeType.FEATURE);
					node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + churnProdFeature.getParentFeatureId());
					sankeyResponse.getNodes().add(node);
					if (!(sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTASSET
							|| x.getSankeyNodeType() == SankeyNodeType.LEFTPACKAGE))) {
						sankeyResponse.getNodes()
								.add(new SankeyNode(
										PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
										churnProdFeature.getProductName(), false, false,
										SankeyNodeType.PRODUCT));
					}
				}
			}

			if (!featureSiblingIds.isEmpty()) {
				SankeyNode node;
				for (String featureSiblingId : featureSiblingIds) {
					List<ChurnProductsAndFeatureSiblings> churnProdFeatureSiblingList = productAndFeatureSiblingDao.findByFeatureSiblingId(featureSiblingId);

					for (ChurnProductsAndFeatureSiblings churnProdFeatureSibling : churnProdFeatureSiblingList) {
						sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeatureSibling.getIdProduct(),
								PrefixConstants.FEATURE_SIBLING + churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getChurn(),
								SankeyLinkType.PRODUCTFEATURESIBLING);
						sankeyResponse.getSankeyLinks().add(sankeyLink);
						node = new SankeyNode(PrefixConstants.FEATURE_SIBLING + churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getExpression(), false,
								true, SankeyNodeType.FEATURE);
						node.setParentId(PrefixConstants.FEATURE_PREFIX + sankeyResponse.getNodes().stream().filter(x-> x.getId().equals("fs-"+featureSiblingId)).findFirst().get().getParentId());
						sankeyResponse.getNodes().add(node);
						if (!(sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.LEFTASSET
								|| x.getSankeyNodeType() == SankeyNodeType.LEFTPACKAGE))) {
							sankeyResponse.getNodes()
									.add(new SankeyNode(
											PrefixConstants.PRODUCT_PREFIX + churnProdFeatureSibling.getIdProduct(),
											churnProdFeatureSibling.getProductName(), false, false,
											SankeyNodeType.PRODUCT));
						}
					}
				}
			}
		}
		return sankeyResponse;

	}

	public SankeyResponse expandAggregatedParentFeature(SankeyResponse sankeyResponse) {
		SankeyLink sankeyLink;
		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE
				|| x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET)) {
			Set<String> assetsIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			Set<String> packagesIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).map(SankeyNode::getId)
					.collect(Collectors.toSet());

			if (!assetsIds.isEmpty()) {
				List<ChurnParentFeaturesAndCoreAssets> parentFeaturesAndCoreAssetsList = parentFeaturesAndCoreAssetsRepo
						.findByCoreAssetIdInAndGroupByFeatures(Formatting.cleanListOfIds(assetsIds));
				for (ChurnParentFeaturesAndCoreAssets churnParentFeaturesAndCoreAssets : parentFeaturesAndCoreAssetsList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.PARENTFEATURE_PREFIX
									+ churnParentFeaturesAndCoreAssets.getParentFeatureId(),
							PrefixConstants.ASSET_PREFIX + churnParentFeaturesAndCoreAssets.getCoreAssetId(),
							churnParentFeaturesAndCoreAssets.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PARENTFEATURE_PREFIX
											+ churnParentFeaturesAndCoreAssets.getParentFeatureId(),
									churnParentFeaturesAndCoreAssets.getParentFeatureName(), true, false,
									SankeyNodeType.PARENTFEATURE));
				}
			}
			if (!packagesIds.isEmpty()) {

				List<ChurnParentFeaturesProductComponents> churnParentFeatureAndProdList = parentFeaturesAndPackagesRepo
						.findByIdPackageIn(Formatting.cleanListOfIds(packagesIds));

				for (ChurnParentFeaturesProductComponents churnParentFeatureAndProd : churnParentFeatureAndProdList) {
					sankeyLink = new SankeyLink(
							PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeatureAndProd.getId_parentfeature(),
							PrefixConstants.PACKAGE_PREFIX + churnParentFeatureAndProd.getIdpackage(),
							churnParentFeatureAndProd.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PARENTFEATURE_PREFIX
											+ churnParentFeatureAndProd.getId_parentfeature(),
									churnParentFeatureAndProd.getParentfeaturename(), true, false,
									SankeyNodeType.PARENTFEATURE));

				}
			}

		}

		sankeyResponse.deleteNodesAndLinksById(ALL_FEATURES);

		ChurnParentFeaturesProductPortfolio churnParentFeaturesProductPortfolio;
		Iterator<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProducts = churnParentFeaturesProductPortfolioRepo
				.findAll().iterator();
		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getId().equals(ALL_PRODUCTS))) {
			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				sankeyLink = new SankeyLink(ALL_PRODUCTS,
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes()
						.add(new SankeyNode(
								PrefixConstants.PARENTFEATURE_PREFIX
										+ churnParentFeaturesProductPortfolio.getIdParentFeature(),
								churnParentFeaturesProductPortfolio.getParentFeatureName(), true, false,
								SankeyNodeType.PARENTFEATURE));
			}

		} else {

			while (churnParentFeaturesAndProducts.hasNext()) {
				churnParentFeaturesProductPortfolio = churnParentFeaturesAndProducts.next();
				sankeyLink = new SankeyLink(
						PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
						PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),
						churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
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

	public SankeyResponse expandParentFeature(SankeyResponse sankeyResponse, String expandId) {
		int parentFeatureId = Integer.parseInt(expandId.split("-")[1]);

		SankeyLink sankeyLink;
		SankeyNode node;

		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET
				|| x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE)) {
			Set<String> assetsIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			Set<String> packagesIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).map(SankeyNode::getId)
					.collect(Collectors.toSet());

			if (!assetsIds.isEmpty()) {
				List<ChurnFeaturesPackageAssets> featuresAndCoreAssetsList = featuresAndAssetsRepo
						.findByParentFeatureIdCoreAssetIdInAndGroupByFeatures(Formatting.cleanListOfIds(assetsIds),
								parentFeatureId);
				for (ChurnFeaturesPackageAssets churnFeaturesAndCoreAssets : featuresAndCoreAssetsList) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX+churnFeaturesAndCoreAssets.getFeatureId(),
							PrefixConstants.ASSET_PREFIX + churnFeaturesAndCoreAssets.getIdcoreasset(),
							churnFeaturesAndCoreAssets.getChurn(), SankeyLinkType.FEATUREASSET);
					sankeyResponse.getSankeyLinks().add(sankeyLink);

				}
			}
			if (!packagesIds.isEmpty()) {

				List<ChurnFeaturesAndPackagesGrouped> churnFeatureAndPackageList = featuresAndPackagesRepo
						.findByParentFeatureAndPackageIdsInAndGroupByFeatures(Formatting.cleanListOfIds(packagesIds),
								parentFeatureId);

				for (ChurnFeaturesAndPackagesGrouped churnParentFeatureAndProd : churnFeatureAndPackageList) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX+churnParentFeatureAndProd.getIdfeature(),
							PrefixConstants.PACKAGE_PREFIX + churnParentFeatureAndProd.getIdpackage(),
							churnParentFeatureAndProd.getChurn(), SankeyLinkType.FEATUREPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);

				}
			}

		}
		sankeyResponse.deleteNodesAndLinksById(expandId);
		List<ChurnProductPortfolioAndFeatures> churnProductsAndFeaturesList = productAndFeaturesRepo
				.findByParentFeatureId(parentFeatureId);
		if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {

			for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {
				sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
						PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				node = new SankeyNode(PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
						true, SankeyNodeType.FEATURE);
				node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + churnProdFeature.getParentFeatureId());
				sankeyResponse.getNodes().add(node);

			}
		} else {

			for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {

				sankeyLink = new SankeyLink(ALL_PRODUCTS, PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getChurn(),
						SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				node = new SankeyNode(PrefixConstants.FEATURE_PREFIX+churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
						true, SankeyNodeType.FEATURE);
				node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + churnProdFeature.getParentFeatureId());
				sankeyResponse.getNodes().add(node);

			}

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
						ALL_PRODUCTS, churnAssetsProducts.getChurn(), SankeyLinkType.PACKAGEPRODUCTALL);
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
				.filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE).map(x->x.getId().split("-")[1])
				.collect(Collectors.toSet());
		Set<String> featureSiblingIds = sankeyResponse.getNodes().stream()
				.filter(x -> x.getSankeyNodeType() == SankeyNodeType.
						FEATURESIBLING).map(x->x.getId())
				.collect(Collectors.toSet());
		SankeyLink sankeyLink;

		if (!parentFeaturesIds.contains(ALL_FEATURES)) {
			if (featureIds.size() > 0) {
				Iterable<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.findByIdfeatureIn(featureIds);
				for (ChurnFeaturesAndPackagesGrouped churnFeaturesAssets : it) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX+churnFeaturesAssets.getIdfeature(),
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

			if (featureSiblingIds.size() > 0) {
				Set<Integer> featureSiblingIdsClenades = Formatting.cleanListOfIds(featureSiblingIds);
				Iterable<ChurnFeatureSiblingsAndPackages> churnFeatureSiblingsAndPackages = churnFeatureSiblingsCoreAssetsDAO.getChurnFeatureSiblingsPackages(featureSiblingIdsClenades);
				for (ChurnFeatureSiblingsAndPackages churnsParentFeaturesAndPackages : churnFeatureSiblingsAndPackages) {
					sankeyLink = new SankeyLink(
							PrefixConstants.FEATURE_SIBLING
									+ churnsParentFeaturesAndPackages.getIdFeatureSibling(),
							PrefixConstants.PACKAGE_PREFIX + churnsParentFeaturesAndPackages.getPackageId(),
							churnsParentFeaturesAndPackages.getChurn(), SankeyLinkType.FEATURESIBLINGPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);
					sankeyResponse.getNodes()
							.add(new SankeyNode(
									PrefixConstants.PACKAGE_PREFIX + churnsParentFeaturesAndPackages.getPackageId(),
									churnsParentFeaturesAndPackages.getPackageName(), true, false,
									SankeyNodeType.RIGHTPACKAGE));
				}
			}


		} else {
			Iterable<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.getCustomsGroupByFeatures();
			for (ChurnFeaturesAndPackagesGrouped churnFeaturesAssets : it) {
				sankeyLink = new SankeyLink(ALL_FEATURES,
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				sankeyResponse.getNodes()
						.add(new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
								churnFeaturesAssets.getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE));
			}
		}

		return sankeyResponse;
	}

    public SankeyResponse expandFeature(SankeyResponse sankeyResponse, String expandId) {
		String featureId = expandId.split("-")[1];

		SankeyLink sankeyLink;
		SankeyNode node;



		List<ChurnProductsAndFeatureSiblings> churnProductsAndFeaturesList = productAndFeatureSiblingDao.getChurnFeatureSiblingProducts(featureId);
		productAndFeatureSiblingDao.setFeatureSiblingsExpression(churnProductsAndFeaturesList);
		if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {

			for (ChurnProductsAndFeatureSiblings churnProdFeatureSibling : churnProductsAndFeaturesList) {
				sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeatureSibling.getIdProduct(),
						PrefixConstants.FEATURE_SIBLING+churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getChurn(), SankeyLinkType.PRODUCTFEATURESIBLING);
				sankeyResponse.getSankeyLinks().add(sankeyLink);
				node = new SankeyNode(PrefixConstants.FEATURE_SIBLING+churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getExpression(), false,
						true, SankeyNodeType.FEATURESIBLING);
				node.setParentId(PrefixConstants.FEATURE_PREFIX + featureId);
				sankeyResponse.getNodes().add(node);

			}
		} else {

			for (ChurnProductsAndFeatureSiblings churnProdFeatureSibling : churnProductsAndFeaturesList) {


				node = new SankeyNode(PrefixConstants.FEATURE_SIBLING+churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getExpression(), false,
						true, SankeyNodeType.FEATURESIBLING);
				node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + featureId);
				sankeyResponse.getNodes().add(node);
				sankeyLink = new SankeyLink(ALL_PRODUCTS, PrefixConstants.FEATURE_SIBLING+churnProdFeatureSibling.getIdFeatureSibling(), churnProdFeatureSibling.getChurn(),
						SankeyLinkType.PRODUCTFEATURE);
				sankeyResponse.getSankeyLinks().add(sankeyLink);

			}

		}
		sankeyResponse.deleteNodesAndLinksById(expandId);

		if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET
				|| x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE)) {
			Set<String> assetsIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET).map(SankeyNode::getId)
					.collect(Collectors.toSet());
			Set<String> packagesIds = sankeyResponse.getNodes().stream()
					.filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).map(SankeyNode::getId)
					.collect(Collectors.toSet());

			if (!assetsIds.isEmpty()) {
				List<ChurnFeatureSiblingsAndAssets> churnFeatureSiblingsAndAssetsList = churnFeatureSiblingsCoreAssetsDAO.
						getChurnFeatureSiblingsCoreAssetsInAssets(
								featureId,Formatting.cleanListOfIds(assetsIds));

				for (ChurnFeatureSiblingsAndAssets churnParentFeatureAndAsset : churnFeatureSiblingsAndAssetsList) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_SIBLING+churnParentFeatureAndAsset.getIdFeatureSibling(),
							PrefixConstants.ASSET_PREFIX + churnParentFeatureAndAsset.getAssetId(),
							churnParentFeatureAndAsset.getChurn(), SankeyLinkType.FEATURESIBLINGCOREASSET);
					sankeyResponse.getSankeyLinks().add(sankeyLink);

				}
			}
			if (!packagesIds.isEmpty()) {

				List<ChurnFeatureSiblingsAndPackages> churnFeatureAndPackageList = churnFeatureSiblingsCoreAssetsDAO.
						getChurnFeatureSiblingsCoreAssetsInPackages(
								featureId,Formatting.cleanListOfIds(packagesIds));

				for (ChurnFeatureSiblingsAndPackages churnParentFeatureAndProd : churnFeatureAndPackageList) {
					sankeyLink = new SankeyLink(PrefixConstants.FEATURE_SIBLING+churnParentFeatureAndProd.getIdFeatureSibling(),
							PrefixConstants.PACKAGE_PREFIX + churnParentFeatureAndProd.getPackageId(),
							churnParentFeatureAndProd.getChurn(), SankeyLinkType.FEATURESIBLINGPACKAGE);
					sankeyResponse.getSankeyLinks().add(sankeyLink);

				}
			}

		}
		return sankeyResponse;

    }
}
