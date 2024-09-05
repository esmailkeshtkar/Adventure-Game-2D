package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, endNode, currentNode;
	boolean endReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNode();
	}
	
	//create node for every tile on the map
	public void instantiateNode() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col<gp.maxWorldCol&&row <gp.maxWorldRow) {
			node[col][row] = new Node(col, row);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void resetNodes() {
		
		int col = 0;
		int row = 0;
		
		while(col<gp.maxWorldCol&&row <gp.maxWorldRow) {
			
			//Reset open, checked and solid state
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col==gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		openList.clear();
		pathList.clear();
		endReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int endCol, int endRow) {
		
		resetNodes();
		
		//Set start and end nodes
		startNode = node[startCol][startRow];
		currentNode = startNode;
		endNode = node[endCol][endRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col<gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//SET SOLID NODE & CHECK TILES
			int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
			if(gp.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			
			//CHECK INTERACTIVE TILES
			for(int i = 0; i < gp.iTile[1].length; i++) {
				if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
					int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
					int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
					node[itCol][itRow].solid = true;
				}
			}
			
			//SET COST
			getCost(node[col][row]);
			
			col++;
			
			if(col==gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node) {
		//G COST
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance+yDistance;
		//H COST
		xDistance = Math.abs(node.col - endNode.col);
		yDistance = Math.abs(node.row - endNode.row);
		node.hCost = xDistance+yDistance;
		//F COST
		node.fCost = node.gCost + node.hCost;
	}
	
	public boolean search() {
		while(endReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			//check current node;
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//Open up node
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			//Open left node
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			//Open down node
			if(row + 1 < gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			//Open right node
			if(col + 1 < gp.maxWorldRow) {
				openNode(node[col+1][row]);
			}
			
			//Find best node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				
				//check if the node's F cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				//if f cost is equal check G Cost
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			//if there is no node in the open list end the loop
			if(openList.size() == 0) {
				break;
			}
			
			//After loop, openList[bestNodeIndex] is the next step(= currentNode)
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == endNode) {
				endReached = true;
				trackPath();
			}
			step++;
		}
		
		return endReached;
	}
	
	public void openNode(Node node) {
		if(node.open==false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	public void trackPath() {
		Node current = endNode;
		
		while(current!= startNode) {
			pathList.add(0,current);
			current = current.parent;
		}
	}
}
