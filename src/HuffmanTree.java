import sun.reflect.generics.tree.Tree;

import java.util.*;

public class HuffmanTree {
    private TreeNode[] meanningfulNodeList;

    public TreeNode createHuffmanTree(long[] weight) {
        PriorityQueue<TreeNode> priorityQueue = new PriorityQueue<>(new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return (int) (o1.getWeight() - o2.getWeight());
            }
        });
        initial(weight,priorityQueue);
        while (priorityQueue.size()>1){

            TreeNode leftnode=priorityQueue.poll();//get left
            TreeNode rightnode=priorityQueue.poll();//get right

            //generate the parent node
            long weightValue=leftnode.getWeight()+rightnode.getWeight();
            TreeNode parentnode=new TreeNode(weightValue,-1);

            //set relation
            leftnode.setParent(parentnode);
            rightnode.setParent(parentnode);
            parentnode.setChildLeft(leftnode);
            parentnode.setChildRight(rightnode);
            priorityQueue.add(parentnode);
        }
        return priorityQueue.poll();
    }

    private void initial(long[] weight, PriorityQueue<TreeNode> priorityQueue) {
        meanningfulNodeList = new TreeNode[weight.length];
        for (int i = 0; i < weight.length; i++) {
            TreeNode treeNode = new TreeNode(weight[i], i);
            meanningfulNodeList[i] = treeNode;
            priorityQueue.add(treeNode);
        }
    }

    public TreeNode[] getMeanningfulNodeList() {
        return meanningfulNodeList;
    }
}