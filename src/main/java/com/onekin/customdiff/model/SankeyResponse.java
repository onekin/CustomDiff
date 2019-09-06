package com.onekin.customdiff.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SankeyResponse {

	private List<SankeyLink> sankeyLinks;
	private Set<SankeyNode> nodes;

	
	public SankeyResponse() {
		super();
	}

	public SankeyResponse(List<SankeyLink> sankeyLinks, Set<SankeyNode> nodes) {
		super();
		this.sankeyLinks = sankeyLinks;
		this.nodes = nodes;
	}

	public List<SankeyLink> getSankeyItems() {
		return sankeyLinks;
	}

	public void setSankeyItems(List<SankeyLink> sankeyLinks) {
		this.sankeyLinks = sankeyLinks;
	}

	public Set<SankeyNode> getNodes() {
		return nodes;
	}

	public void setNodes(Set<SankeyNode> nodes) {
		this.nodes = nodes;
	}

	public void removeLinksByParentId(List<SankeyNode> nodesToRemove) {
		List<String> idsToRemove = nodesToRemove.stream().map(SankeyNode::getId).collect(Collectors.toList());
		this.sankeyLinks.removeIf(x-> idsToRemove.contains(x.getFrom()) ||idsToRemove.contains(x.getTo()));		
	}

	public void removeLinksById(String expandId) {
		this.sankeyLinks.removeIf(x -> x.getFrom().equals(expandId) | x.getTo().equals(expandId));
		
	}

}
