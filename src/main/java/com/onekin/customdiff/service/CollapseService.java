package com.onekin.customdiff.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.onekin.customdiff.model.*;
import com.onekin.customdiff.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.utils.Formatting;
import com.onekin.customdiff.utils.PrefixConstants;

@Service
public class CollapseService {

    private final String ALL_PRODUCTS = "ALL-PR";
    private final String ALL_FEATURES = "ALL-F";

    @Autowired
    private ChurnParentFeaturesProductComponentsRepository parentFeaturesAndPackagesRepo;

    @Autowired
    private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;

    @Autowired
    private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;

    @Autowired
    private ChurnParentFeaturesAndCoreAssetsRepository parentFeaturesAndCoreAssetsRepo;

    @Autowired
    private ChurnParentFeaturesProductPortfolioRepository churnParentFeaturesProductPortfolioRepo;
    @Autowired
    private ChurnFeaturesPackageAssetsRepository featuresAndAssetsRepo;

    @Autowired
    private ChurnProductPortfolioAndFeaturesRepository productAndFeaturesRepo;

    public void collapseLeftFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
        List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
                .findByIdPackage(Integer.valueOf(packageId));
        SankeyNode node;
        SankeyLink sankeyLink;
        if (nodes.stream().anyMatch(x -> x.getId().equals(ALL_PRODUCTS))) {
            for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
                sankeyLink = new SankeyLink(
                        PrefixConstants.PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'", ALL_PRODUCTS,
                        packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
                sankeyLinks.add(sankeyLink);
            }
            node = new SankeyNode(
                    PrefixConstants.PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
                    churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE);
            nodes.add(node);
        } else {
            for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
                sankeyLink = new SankeyLink(
                        PrefixConstants.PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
                        PrefixConstants.PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(),
                        packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
                sankeyLinks.add(sankeyLink);
            }
            node = new SankeyNode(
                    PrefixConstants.PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
                    churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE);
            nodes.add(node);
        }
    }

    public void collapseRightFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
        SankeyNode node;
        SankeyLink sankeyLink;
        if (nodes.stream().anyMatch(x -> x.getId().equals(ALL_FEATURES))) {
            List<ChurnParentFeaturesProductComponents> parentFeaturesAndPackageList = parentFeaturesAndPackagesRepo
                    .findByPackageId(Integer.parseInt(packageId));
            for (ChurnParentFeaturesProductComponents churnParentFeaturesProductComponents : parentFeaturesAndPackageList) {
                sankeyLink = new SankeyLink(ALL_FEATURES,
                        PrefixConstants.PACKAGE_PREFIX + churnParentFeaturesProductComponents.getIdpackage(),
                        churnParentFeaturesProductComponents.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
                sankeyLinks.add(sankeyLink);

            }
            if (!parentFeaturesAndPackageList.isEmpty()) {

                node = new SankeyNode(
                        PrefixConstants.PACKAGE_PREFIX + parentFeaturesAndPackageList.get(0).getIdpackage(),
                        parentFeaturesAndPackageList.get(0).getPackage_name(), true, false,
                        SankeyNodeType.RIGHTPACKAGE);
                nodes.add(node);
            }
        } else {

            Set<String> parentFeaturesIds = nodes.stream()
                    .filter(x -> x.getSankeyNodeType() == SankeyNodeType.PARENTFEATURE).map(SankeyNode::getId)
                    .collect(Collectors.toSet());
            Set<String> featureIds = nodes.stream().filter(x -> x.getSankeyNodeType() == SankeyNodeType.FEATURE)
                    .map(x -> x.getId().split("-")[1]).collect(Collectors.toSet());

            if (!featureIds.isEmpty()) {
                List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
                        .findByIdPackageAndFeaturesInGroupByFeatures(Integer.valueOf(packageId), featureIds);
                for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
                    sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX + churnFeaturesAndPackage.getIdfeature(),
                            PrefixConstants.PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(),
                            churnFeaturesAndPackage.getChurn(), SankeyLinkType.FEATUREPACKAGE);
                    sankeyLinks.add(sankeyLink);

                }
                if (!churnFeaturesPackagesList.isEmpty()) {

                    node = new SankeyNode(
                            PrefixConstants.PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
                            churnFeaturesPackagesList.get(0).getPackage_name(), true, false,
                            SankeyNodeType.RIGHTPACKAGE);
                    nodes.add(node);
                }
            }
            if (!parentFeaturesIds.isEmpty()) {
                List<ChurnParentFeaturesProductComponents> parentFeaturesAndPackageList = parentFeaturesAndPackagesRepo
                        .findByParentFeatureIdInAndPackageId(Formatting.cleanListOfIds(parentFeaturesIds),
                                Integer.parseInt(packageId));
                for (ChurnParentFeaturesProductComponents churnParentFeaturesProductComponents : parentFeaturesAndPackageList) {
                    sankeyLink = new SankeyLink(
                            PrefixConstants.PARENTFEATURE_PREFIX
                                    + churnParentFeaturesProductComponents.getId_parentfeature(),
                            PrefixConstants.PACKAGE_PREFIX + churnParentFeaturesProductComponents.getIdpackage(),
                            churnParentFeaturesProductComponents.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
                    sankeyLinks.add(sankeyLink);

                }
                if (!parentFeaturesAndPackageList.isEmpty()) {
                    node = new SankeyNode(
                            PrefixConstants.PACKAGE_PREFIX + parentFeaturesAndPackageList.get(0).getIdpackage(),
                            parentFeaturesAndPackageList.get(0).getPackage_name(), true, false,
                            SankeyNodeType.RIGHTPACKAGE);
                    nodes.add(node);
                }
            }
        }

    }

    public void collapseLeftFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks,
                                                     String packageId, Set<String> featureIds) {
        List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
                .findByIdPackageAndFeaturesIn(Integer.valueOf(packageId), featureIds);
        SankeyNode node;
        SankeyLink sankeyLink;
        for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
            sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
                    PrefixConstants.PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(),
                    packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
            sankeyLinks.add(sankeyLink);
        }
        node = new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
                churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE);
        nodes.add(node);
    }

    public void collapseFeature(SankeyResponse sankeyResponse, String parentFeatureId) {
        Set<String> assetsIds = sankeyResponse.getNodes().stream()
                .filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET).map(SankeyNode::getId)
                .collect(Collectors.toSet());
        Set<String> packagesIds = sankeyResponse.getNodes().stream()
                .filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).map(SankeyNode::getId)
                .collect(Collectors.toSet());
        int cleanParentFeature = Integer.parseInt(parentFeatureId.split("-")[1]);
        SankeyLink sankeyLink;
        SankeyNode node;
        if (!assetsIds.isEmpty()) {

            List<ChurnParentFeaturesAndCoreAssets> parentFeaturesAndCoreAssetsChurnList = parentFeaturesAndCoreAssetsRepo
                    .findByParentFeatureIdCoreAssetIdInAndGroupByFeatures(Formatting.cleanListOfIds(assetsIds),
                            cleanParentFeature);
            for (ChurnParentFeaturesAndCoreAssets churnParentFeaturesAndCoreAssets : parentFeaturesAndCoreAssetsChurnList) {
                sankeyLink = new SankeyLink(
                        PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesAndCoreAssets.getParentFeatureId(),
                        PrefixConstants.ASSET_PREFIX + churnParentFeaturesAndCoreAssets.getCoreAssetId(),
                        churnParentFeaturesAndCoreAssets.getChurn(), SankeyLinkType.PARENTFEATUREASSET);
                sankeyResponse.getSankeyLinks().add(sankeyLink);

            }

        }

        if (!packagesIds.isEmpty()) {
            List<ChurnParentFeaturesProductComponents> parentFeaturesAndPackagesChurnList = parentFeaturesAndPackagesRepo
                    .findByParentFeatureIdAndPackageIdIn(Formatting.cleanListOfIds(packagesIds), cleanParentFeature);
            for (ChurnParentFeaturesProductComponents parentFeaturesAndPackagesChurn : parentFeaturesAndPackagesChurnList) {
                sankeyLink = new SankeyLink(
                        PrefixConstants.PARENTFEATURE_PREFIX + parentFeaturesAndPackagesChurn.getId_parentfeature(),
                        PrefixConstants.PACKAGE_PREFIX + parentFeaturesAndPackagesChurn.getIdpackage(),
                        parentFeaturesAndPackagesChurn.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);

            }
        }

        List<ChurnParentFeaturesProductPortfolio> churnParentFeaturesAndProductsList = churnParentFeaturesProductPortfolioRepo
                .findByParentFeatureId(cleanParentFeature);

        if (sankeyResponse.getNodes().stream().anyMatch(x -> x.getId().equals(ALL_PRODUCTS))) {
            for (ChurnParentFeaturesProductPortfolio churnParentFeaturesProductPortfolio : churnParentFeaturesAndProductsList) {
                sankeyLink = new SankeyLink(ALL_PRODUCTS,
                        PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),

                        churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PARENTFEATUREPACKAGE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);
            }
        } else {
            for (ChurnParentFeaturesProductPortfolio churnParentFeaturesProductPortfolio : churnParentFeaturesAndProductsList) {
                sankeyLink = new SankeyLink(
                        PrefixConstants.PRODUCT_PREFIX + churnParentFeaturesProductPortfolio.getProductId(),
                        PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesProductPortfolio.getIdParentFeature(),

                        churnParentFeaturesProductPortfolio.getChurn(), SankeyLinkType.PRODUCTPARENTFEATURE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);
            }
        }
        node = new SankeyNode(
                PrefixConstants.PARENTFEATURE_PREFIX + churnParentFeaturesAndProductsList.get(0).getIdParentFeature(),
                churnParentFeaturesAndProductsList.get(0).getParentFeatureName(), true, false,
                SankeyNodeType.PARENTFEATURE);
        sankeyResponse.getNodes().add(node);

    }

    public void collapseFeatureSibling(SankeyResponse sankeyResponse, String featureId) {
        Set<String> assetsIds = sankeyResponse.getNodes().stream()
                .filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTASSET).map(SankeyNode::getId)
                .collect(Collectors.toSet());
        Set<String> packagesIds = sankeyResponse.getNodes().stream()
                .filter(x -> x.getSankeyNodeType() == SankeyNodeType.RIGHTPACKAGE).map(SankeyNode::getId)
                .collect(Collectors.toSet());
        String cleanFeatureId = featureId.split("-")[1];
        SankeyLink sankeyLink;
        SankeyNode node;
        if (!assetsIds.isEmpty()) {

            List<ChurnFeaturesPackageAssets> featuresAndCoreAssetsList = featuresAndAssetsRepo
                    .findByAssetsIdInAndFeaturesGroupedByFeaturesAndAsset(Formatting.cleanListOfIds(assetsIds),
                            cleanFeatureId);
            for (ChurnFeaturesPackageAssets churnFeaturesAndCoreAssets : featuresAndCoreAssetsList) {
                sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX + churnFeaturesAndCoreAssets.getFeatureId(),
                        PrefixConstants.ASSET_PREFIX + churnFeaturesAndCoreAssets.getIdcoreasset(),
                        churnFeaturesAndCoreAssets.getChurn(), SankeyLinkType.FEATUREASSET);
                sankeyResponse.getSankeyLinks().add(sankeyLink);


            }
        }

        if (!packagesIds.isEmpty()) {
            List<ChurnFeaturesAndPackagesGrouped> churnFeatureAndPackageList = featuresAndPackagesRepo
                    .findByPackageIdInAndFeaturesGroupedByFeaturesAndAsset(Formatting.cleanListOfIds(packagesIds),
                            cleanFeatureId);

            for (ChurnFeaturesAndPackagesGrouped churnParentFeatureAndProd : churnFeatureAndPackageList) {
                sankeyLink = new SankeyLink(PrefixConstants.FEATURE_PREFIX + churnParentFeatureAndProd.getIdfeature(),
                        PrefixConstants.PACKAGE_PREFIX + churnParentFeatureAndProd.getIdpackage(),
                        churnParentFeatureAndProd.getChurn(), SankeyLinkType.FEATUREPACKAGE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);

            }
        }

        List<ChurnProductPortfolioAndFeatures> churnProductsAndFeaturesList = productAndFeaturesRepo.findByIdFeature(cleanFeatureId);

        if (sankeyResponse.getNodes().stream().filter(x -> x.getId().equals(ALL_PRODUCTS)).count() == 0) {

            for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {
                sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getProductId(),
                        PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);
                node = new SankeyNode(PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
                        true, SankeyNodeType.FEATURE);
                node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + churnProdFeature.getParentFeatureId());
                sankeyResponse.getNodes().add(node);

            }
        } else {

            for (ChurnProductPortfolioAndFeatures churnProdFeature : churnProductsAndFeaturesList) {

                sankeyLink = new SankeyLink(ALL_PRODUCTS, PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getChurn(),
                        SankeyLinkType.PRODUCTFEATURE);
                sankeyResponse.getSankeyLinks().add(sankeyLink);
                node = new SankeyNode(PrefixConstants.FEATURE_PREFIX + churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), true,
                        true, SankeyNodeType.FEATURE);
                node.setParentId(PrefixConstants.PARENTFEATURE_PREFIX + churnProdFeature.getParentFeatureId());
                sankeyResponse.getNodes().add(node);

            }

        }
        node = new SankeyNode(
                PrefixConstants.FEATURE_PREFIX + churnProductsAndFeaturesList.get(0).getIdFeature(),
                churnProductsAndFeaturesList.get(0).getFeaturemodified(), true, false,
                SankeyNodeType.FEATURE);
        sankeyResponse.getNodes().add(node);

    }
}
