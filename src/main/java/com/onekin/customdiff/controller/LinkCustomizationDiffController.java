package com.onekin.customdiff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.service.LinkCustomizationDiffService;

@Controller
@RequestMapping("/link-customizations")
public class LinkCustomizationDiffController {

	@Autowired
	private LinkCustomizationDiffService linkCustomizationDiffService;

	@GetMapping("/packageproduct")
	public String getPackageAndProductCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> packageAndProductsCustomizations = linkCustomizationDiffService
				.getPackageAndProductsCustomizations(from, to);
		model.addAttribute("customizations", packageAndProductsCustomizations);
		model.addAttribute("linkNodes", packageAndProductsCustomizations.get(0).getPrname() + " and "+ packageAndProductsCustomizations.get(0).getCapath());

		return "links-diffs";
	}

	@GetMapping("/assetproduct")
	public String getProductAndAssetCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> productAndAssetCustomizations = linkCustomizationDiffService
				.getProductAndAssetCustomizations(from, to);
		model.addAttribute("customizations", productAndAssetCustomizations);
		model.addAttribute("linkNodes", productAndAssetCustomizations.get(0).getPrname() + " and "+ productAndAssetCustomizations.get(0).getCapath());
		return "links-diffs";
	}

	@GetMapping("/productfeature")
	public String getProductAndFeatureCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> productAndFeatureCustomizations = linkCustomizationDiffService
				.getProductAndFeatureCustomizations(from, to);
		model.addAttribute("customizations", productAndFeatureCustomizations);
		model.addAttribute("linkNodes", to + " and "+ productAndFeatureCustomizations.get(0).getPrname());
		return "links-diffs";
	}

	@GetMapping("/featureasset")
	public String getFeatureAndAssetCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> featureAndAssetCustomizations = linkCustomizationDiffService
				.getFeatureAndAssetCustomizations(from, to);
		model.addAttribute("customizations", featureAndAssetCustomizations);
		model.addAttribute("linkNodes", from + " and "+ featureAndAssetCustomizations.get(0).getCapath());
		return "links-diffs";
	}

	@GetMapping("/featurepackage")
	public String getFeatureAndPackageCustomizations(@RequestParam("from") String from, @RequestParam("to") String to,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> featureAndPackageCustomizations = linkCustomizationDiffService
				.getFeatureAndPackageCustomizations(from, to);
		model.addAttribute("customizations", featureAndPackageCustomizations);
		model.addAttribute("linkNodes", from + " and "+ featureAndPackageCustomizations.get(0).getIdpackage());

		return "links-diffs";
	}

}
