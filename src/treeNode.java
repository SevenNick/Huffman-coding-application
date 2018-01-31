
public class treeNode {
    //定义节点的属性，下面是必须有的，你也可以根据需呀定义更多
    private treeNode parent; //上一个节点（父节点）
    private treeNode Left;  //子节点为左节点
    private treeNode Right;  //子节点的为右节点
    private int weight;//该节点的权值，主要是根据它来构造树
    private String value;//该节点对应的字符
    //重载构造方法，在创建对象时必须传入value数据
    public treeNode(int weight,String value){
        this.weight=weight;
        this.value=value;
    }
    //设置是一个父节点的方法(即上一个节点）
    public void setParent(treeNode parent){
        this.parent=parent;
    }
    ///获得上一个节点的方法
    public treeNode getParent(){
        return parent;
    }
    //设置子节点（左节点）的方法
    public void setChildLeft(treeNode childLeft){
        this.Left=childLeft;
    }
    //获得子节点（左节点）的方法
    public  treeNode getChildLeft(){
        return Left;
    }
    //设置子节点（左节点）的方法
    public void setChildRight(treeNode childRight){
        this.Right=childRight;
    }
    //获得子节点（左节点）的方法
    public  treeNode getChildRight(){
        return Right;
    }
    //设置权值的方法
    public void setValue(int weight){
        this.weight=weight;
    }
    //获得权值的方法
    public int getWeight(){
        return weight;
    }
    //获取字符的方法
    public String getValue(){
        return value;
    }
}