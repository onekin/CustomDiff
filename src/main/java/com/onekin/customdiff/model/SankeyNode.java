package com.onekin.customdiff.model;

public class SankeyNode {

	private String id;
	private String parentId;
	private String name;
	private SankeyNodeType sankeyNodeType;

	private boolean expandable;
	private boolean collapsable;


	public SankeyNode() {
		super();
	}

	public SankeyNode(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SankeyNode(String id, String name, boolean expandable, boolean collapsable, SankeyNodeType sankeyNodeType) {
		super();
		this.id = id;
		this.name = name;
		this.expandable = expandable;
		this.sankeyNodeType = sankeyNodeType;
		this.collapsable = collapsable;
	}

	public SankeyNode(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SankeyNode other = (SankeyNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	public SankeyNodeType getSankeyNodeType() {
		return sankeyNodeType;
	}

	public void setSankeyNodeType(SankeyNodeType sankeyNodeType) {
		this.sankeyNodeType = sankeyNodeType;
	}

	public boolean isCollapsable() {
		return collapsable;
	}

	public void setCollapsable(boolean collapsable) {
		this.collapsable = collapsable;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
