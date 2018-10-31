import sun.reflect.generics.tree.Tree;

import java.util.*;

/*
public class HuffmanTree {
    public LinkedList<TreeNode> list = new LinkedList<TreeNode>();
    public TreeNode[] validnode;
    //对各个字符的权重进行排序
    public void sortValue(int[] weight,int[] value){
        //冒泡排序(从小到大)
        validnode = new TreeNode[weight.length];
        for(int i=0;i<weight.length;i++){
            for(int j=i+1;j<weight.length;j++){
                if(weight[i]>weight[j]){//比较大小
                    int temp=weight[i];
                    weight[i]=weight[j];
                    weight[j]=temp;
                    int temp2 = value[i];
                    value[i]=value[j];
                    value[j]=temp2;
                }
            }
            //创建节点对象，并把它装入队列中
            TreeNode node=new TreeNode(weight[i],value[i]);
            validnode[i]=node;
            list.add(node);
        }
    }
    public void createTree(int[] weight,int[] value){
        //调用sortValue方法，将叶子节点进行封装
        this.sortValue(weight,value);
        //根据哈夫曼编码的原理，建立哈夫曼树
        while(list.size()>1){//节点数大于一时，才进行如下操作
            TreeNode leftnode=list.remove(0);//获得左节点
            TreeNode rightnode=list.remove(0);//获得右节点
            //根据这两个节点，创建它们的父节点
            int weightvale=leftnode.getWeight()+rightnode.getWeight();
            TreeNode parentnode=new TreeNode(weightvale,-1);
            //给这三个节点建立对应的关系
            leftnode.setParent(parentnode);
            rightnode.setParent(parentnode);
            parentnode.setChildLeft(leftnode);
            parentnode.setChildRight(rightnode);
            //将这两个节点的父节点装入队列中，并对它们进行排列
            this.sortAgain(parentnode);
        }
    }
    public void sortAgain(TreeNode newnode){
        //因为里面的节点已经排好序了，所以只是将新节点插入对应的位置就可以了
        int size=list.size();
        if(size>0){//里面至少有一个节点才能进行比较
            if(newnode.getWeight()>list.get(size-1).getWeight()){//如果要加入的节点的权值大于队列中最后一个节点的权值，则放到最后面
                list.add(newnode);
            }else{//否则才去进行比较
                for(int i=0;i<list.size();i++){
                    TreeNode node=list.get(i);//按顺序得到节点
                    if(newnode.getWeight()<=node.getWeight()){//比较它们的权值
                        list.add(i, newnode);//将新节点添加到指定的位置
                        break;//结束循环
                    }
                }
            }
        }else{//否则直接加进去
            list.add(newnode);
        }
    }
}*/
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
            TreeNode leftnode=priorityQueue.poll();//获得左节点
            TreeNode rightnode=priorityQueue.poll();//获得右节点
            //根据这两个节点，创建它们的父节点
            long weightvale=leftnode.getWeight()+rightnode.getWeight();
            TreeNode parentnode=new TreeNode(weightvale,-1);
            //给这三个节点建立对应的关系
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