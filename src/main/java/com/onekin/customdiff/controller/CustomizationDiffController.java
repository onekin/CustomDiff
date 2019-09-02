package com.onekin.customdiff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.service.CustomizationDiffService;

@Controller
public class CustomizationDiffController {

	@Autowired
	private CustomizationDiffService customizationDiffService;

	@GetMapping("/link-customizations/packageproduct")
	public String getPackageAndProductCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> packageAndProductsCustomizations = customizationDiffService
				.getPackageAndProductsCustomizations(from, to);
		model.addAttribute("customizations", packageAndProductsCustomizations);
		return "customizations-diffs";
	}

	@GetMapping("/link-customizations/assetproduct")
	public String getProductAndAssetCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> productAndAssetCustomizations = customizationDiffService
				.getProductAndAssetCustomizations(from, to);
		model.addAttribute("customizations", productAndAssetCustomizations);
		return "customizations-diffs";
	}

	@GetMapping("/link-customizations/productfeature")
	public String getProductAndFeatureCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> productAndFeatureCustomizations = customizationDiffService
				.getProductAndFeatureCustomizations(from, to);
		model.addAttribute("customizations", productAndFeatureCustomizations);
		return "customizations-diffs";
	}

	@GetMapping("/link-customizations/featureasset")
	public String getFeatureAndAssetCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> featureAndAssetCustomizations = customizationDiffService
				.getFeatureAndAssetCustomizations(from, to);
		model.addAttribute("customizations", featureAndAssetCustomizations);
		return "customizations-diffs";
	}

	@GetMapping("/link-customizations/featurepackage")
	public String getFeatureAndPackageCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> featureAndPackageCustomizations = customizationDiffService
				.getFeatureAndPackageCustomizations(from, to);
		model.addAttribute("customizations", featureAndPackageCustomizations);
		return "customizations-diffs";
	}

}
