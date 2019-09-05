package com.onekin.customdiff.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

public class Formatting {

	public static ArrayList<String> extractMiniPaths(ArrayList<String> longPath) {
		System.out.println(longPath);
		ArrayList<String> splittedPaths = new ArrayList<>();
		Iterator<String> it = longPath.iterator();
		String filePath;
		String[] folders;
		while (it.hasNext()) {
			filePath = it.next();
			folders = filePath.split("/");
			for (int i = 0; i < folders.length - 1; i++) {
				if ((i <= 0) && (!splittedPaths.contains(folders[i])))
					splittedPaths.add(folders[i]);
				else if ((i > 0) && (!splittedPaths.contains(folders[i - 1] + "/" + folders[i])))
					splittedPaths.add(folders[i - 1] + "/" + folders[i]);
			}
		}
		return splittedPaths;
	}

	public static ArrayList<String> extractMiniPathsAndFile(ArrayList<String> longPath) {
		System.out.println(longPath);
		ArrayList<String> splittedPaths = new ArrayList<>();
		Iterator<String> it = longPath.iterator();
		String filePath;
		String[] folders;
		while (it.hasNext()) {
			filePath = it.next();
			folders = filePath.split("/");
			for (int i = 0; i < folders.length; i++) {
				if ((i <= 0) && (!splittedPaths.contains(folders[i])))
					splittedPaths.add(folders[i]);
				else if ((i > 0) && (!splittedPaths.contains(folders[i - 1] + "/" + folders[i]))) {
					int j = 0;
					while (j < i) {
						splittedPaths.add(folders[i - 1] + "/" + folders[i]);
						j++;
					}

				}

			}
		}
		return splittedPaths;
	}

	public static ArrayList<String> extractPathsFromPathList(ArrayList<String> paths) {
		ArrayList<String> thepaths = new ArrayList<String>();

		for (int i = 0; i < paths.size(); i++) {
			String path = paths.get(i);
			String[] parsed = path.split("/"); // [input, js, sensors.js]
			for (int j = 0; j < parsed.length; j++) {
				String partialpath = "";
				for (int z = 0; z <= j; z++) {
					if (z == 0)
						partialpath = partialpath.concat(parsed[z]);
					else
						partialpath = partialpath.concat("/" + parsed[z]);
				}
				if (!thepaths.contains(partialpath)) {
					thepaths.add(partialpath);
					System.out.println("Added path:" + partialpath);
				}
			}
		}
		return thepaths;
	}

	public static ArrayList<String> extractPathsFromPathListWitoutFilePath(ArrayList<String> paths) {
		ArrayList<String> thepaths = new ArrayList<String>();

		for (int i = 0; i < paths.size(); i++) {
			String path = paths.get(i);
			String[] parsed = path.split("/"); // [input, js, sensors.js]
			for (int j = 0; j < parsed.length - 1; j++) {
				String partialpath = "";
				for (int z = 0; z <= j; z++) {
					if (z == 0)
						partialpath = partialpath.concat(parsed[z]);
					else
						partialpath = partialpath.concat("/" + parsed[z]);
				}
				if (!thepaths.contains(partialpath)) {
					thepaths.add(partialpath);
					System.out.println("Added path:" + partialpath);
				}
			}
		}
		return thepaths;
	}

	public static String decodeFromBase64(String encoded) {

		byte[] valueDecoded = new Base64().decode(encoded);
		// System.out.println("Decoded value is: " + new String(valueDecoded));

		return new String(valueDecoded);
	}

	public static ArrayList<String> stringToArrayList(String string, String parseBy) {
		ArrayList<String> list = new ArrayList<String>();
		if (string == null)
			return null;

		String[] array = string.split(parseBy);
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	public static String fromArrayListToString(ArrayList<String> list) {

		String text = "";
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			text += it.next();
		}

		return text;
	}
	
	public static List<Integer> cleanListOfIds(Set<String> dirtyIds){
		List<Integer> cleanIds = new ArrayList<>();
		for(String dirtyId: dirtyIds) {
			dirtyId = dirtyId.split("-")[1];
			dirtyId = dirtyId.replace("'", "");
			cleanIds.add(Integer.valueOf(dirtyId));
		}
		return cleanIds;
		
	}

}
