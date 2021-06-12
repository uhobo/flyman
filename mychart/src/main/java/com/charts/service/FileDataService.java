package com.charts.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper; 

@Service
public class FileDataService {
	
	private final ObjectMapper obj = new ObjectMapper();
	
//	public List<TreeNode> createTreeMenu(FileData fileData, SubjectType subject) throws JsonProcessingException {
//		List<TreeNode>  resultList = new ArrayList<>();
//		switch(subject) {
//		case SERIE:
//			resultList.add(createSerieTree(fileData));
//			break;
//		case TOPIC:
//			resultList.add(createTopicTree(fileData));
//			break;
//		case CATEGORY:
//			resultList.add(createCategoryTree(fileData));
//			break;
//		default: return null;	
//		}
//		return resultList;
//	}
	
	
	
//	private TreeNode createCategoryTree(FileData fileData) throws JsonProcessingException {
//		TreeNode parenttreeNode = new TreeNode();
//		parenttreeNode.setLabel(SubjectType.CATEGORY.getCaption());
//		parenttreeNode.setExpanded(true);
//		for(DataHeader header : fileData.getHeaders()) {
//			if(!header.isShow() || header.isDisabled() || header.getIndex().equals(fileData.getTopicColIndex())  || header.getIndex().equals(fileData.getGroupColIndex())) {
//				continue;
//			}
//			TreeNode leafNode = new TreeNode();
//			leafNode.setLabel(header.getTitle());
//			
//			//Roni
//			//leafNode.setData(header);
//			leafNode.setData(obj.writeValueAsString(header));
//			leafNode.setLeaf(true);
//			parenttreeNode.getChildren().add(leafNode);
//		}
//		
//		return parenttreeNode;
//	}



//	private TreeNode createSerieTree(FileData fileData) throws JsonProcessingException {
//		TreeNode parenttreeNode = new TreeNode();
//		parenttreeNode.setLabel(SubjectType.SERIE.getCaption());
//		parenttreeNode.setExpanded(true);
//		for(SeriesItem serie : fileData.getSeriesList()) {
//			TreeNode leafNode = new TreeNode();
//			leafNode.setLabel(ChartAppUtil.getSeriesLabel(serie));
//			//Roni
//			//leafNode.setData(serie);
//			leafNode.setData(obj.writeValueAsString(serie));
//			leafNode.setLeaf(true);
//			parenttreeNode.getChildren().add(leafNode);
//		}
//		
//		return parenttreeNode;
//	}
//	
	
//	/**
//	 * Set the data for side menu by group
//	 * @param fileData
//	 * @return
//	 * @throws JsonProcessingException 
//	 */
//	private TreeNode createTopicTree(FileData fileData) throws JsonProcessingException {
//		
//		//<i class="fas fa-chalkboard-teacher"></i>
//		//<i class="fas fa-child"></i>
//		//<i class="fas fa-user"></i>
//		Map<String, TreeNode> mapResult = new HashMap<>();
//		Integer groupColIndex =fileData.getGroupColIndex();
//		//TODO need to handle case the child don't has teacher
//		
//		
//		
//		for(DataLine dataLine : fileData.getDataLines()) {
//			
//			if(dataLine.isDisabled()) {
//				continue;
//			}
//			TreeNode leafNode = new TreeNode();
//			leafNode.setLabel(dataLine.getData().get(fileData.getTopicColIndex()).toString());
//			leafNode.setData(obj.writeValueAsString(dataLine));
//			leafNode.setIcon("fa fa-user");
//			leafNode.setLeaf(true);
//			
//			Object groupData = null;
//			if(groupColIndex  !=null) {
//				groupData = dataLine.getData().get(groupColIndex);
//			}
//			//No group 
//			if(groupData == null) {
//				mapResult.put(leafNode.getLabel(), leafNode);
//				continue;
//			}
//			
//			TreeNode teacher = mapResult.get(groupData.toString());
//			if(teacher == null) {
//				teacher = new TreeNode();
//				teacher.setLabel(groupData.toString());
//				//teacher.setData(groupData.toString());
//				teacher.setExpanded(true);
//				teacher.setExpandedIcon("fa fa-chalkboard-teacher");
//				teacher.setCollapsedIcon("fa fa-chalkboard-teacher");
//			}
//			
//			
//			teacher.getChildren().add(leafNode);
//			mapResult.put(teacher.getLabel(), teacher);
//		}
//		TreeNode parenttreeNode = new TreeNode();
//		parenttreeNode.setLabel(SubjectType.TOPIC.getCaption());
//		parenttreeNode.setExpanded(true);
//		for(TreeNode treeNode: mapResult.values()) {
//			if(!treeNode.getChildren().isEmpty()) {
//				parenttreeNode.getChildren().add(0,treeNode);
//			}else {
//				parenttreeNode.getChildren().add(treeNode);
//			}
//		}
//		return parenttreeNode;
//	}
}
