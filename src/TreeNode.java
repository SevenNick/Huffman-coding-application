public class TreeNode {
    //定义节点的属性，下面是必须有的，你也可以根据需呀定义更多
    private TreeNode parent; //上一个节点（父节点）
    private TreeNode Left;  //子节点为左节点
    private TreeNode Right;  //子节点的为右节点
    private long weight;//该节点的权值，主要是根据它来构造树
    private int value;//该节点对应的字符
    //重载构造方法，在创建对象时必须传入value数据
    public TreeNode(long weight, int value){
        this.weight=weight;
        this.value=value;
    }
    //设置是一个父节点的方法(即上一个节点）
    public void setParent(TreeNode parent){
        this.parent=parent;
    }
    ///获得上一个节点的方法
    public TreeNode getParent(){
        return parent;
    }
    //设置子节点（左节点）的方法
    public void setChildLeft(TreeNode childLeft){
        this.Left=childLeft;
    }
    //获得子节点（左节点）的方法
    public TreeNode getChildLeft(){
        return Left;
    }
    //设置子节点（左节点）的方法
    public void setChildRight(TreeNode childRight){
        this.Right=childRight;
    }
    //获得子节点（左节点）的方法
    public TreeNode getChildRight(){
        return Right;
    }
    //设置权值的方法
    public void setValue(int weight){
        this.weight=weight;
    }
    //获得权值的方法
    public long getWeight(){
        return weight;
    }

    public int getValue() {
        return value;
    }
}