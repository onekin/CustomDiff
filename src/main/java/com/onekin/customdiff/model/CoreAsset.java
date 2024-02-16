package com.onekin.customdiff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.onekin.customdiff.utils.Formatting;



@Entity
@Table (name="core_asset")
public class CoreAsset {

		@Id int idcoreasset;
		String name;
		String path;
		String content;
		int size;
		int isnewasset;
		int idpackage;
		
		public CoreAsset() {}

		
		
		public CoreAsset(String name, String path, String content) {
			super();
			this.name = name;
			this.path = path;
			this.content = content;
		}



		public int getIdcoreasset() {
			return idcoreasset;
		}

		public void setIdcoreasset(int idcoreasset) {
			this.idcoreasset = idcoreasset;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getContent() {
			try {
				return Formatting.decodeFromBase64(content);
			}catch(Exception e) {
				return content;
			}
		
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getIsnewasset() {
			return isnewasset;
		}

		public void setIsnewasset(int isnewasset) {
			this.isnewasset = isnewasset;
		}

		public int getIdpackage() {
			return idpackage;
		}

		public void setIdpackage(int idpackage) {
			this.idpackage = idpackage;
		}
		
}
