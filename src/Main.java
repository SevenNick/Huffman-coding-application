import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.*;

public class Main extends Application {
    static int[] weight = new int[256];
    static String[] value = new String[256];
    static String[] code = new String[256];
    static String[] savecode = new String[256];
    static String pacpath="";//要压缩的文件夹在哪里
    static String respacpath="";//压缩的文件放在哪里
    public static void main(String args[]){
        launch(args);
    }
    public static void haha(String src,String dest){
        String filene="";
        File file = new File(src);
        all:{//这个all好像多此一举了
            for(int i =src.length()-1;i>0;i--){
                if(src.charAt(i)=='\\') {
                    filene=src.substring(i+1,src.length());
                    break all;
                }
            }
        }
        if(file.isFile()){
            compressfun(src,dest+"\\"+filene);
        }
        else {
            File file2 = new File(dest+"\\"+filene);
            file2.mkdirs();
            String[] filename = file.list();
            for (int i = 0;i< filename.length;i++){
                File file1 = new File(src+"\\"+filename[i]);
                if(file1.isDirectory()){
                    haha(src+"\\"+filename[i],dest+"\\"+filene);
                }
                else {
                    compressfun(src+"\\"+filename[i],dest+"\\"+filene+"\\"+filename[i]);
                }
            }
        }
    }
    public static void hehe(String src,String dest){
        String filene="";
        File file = new File(src);
        all:{
            for(int i =src.length()-1;i>0;i--){
                if(src.charAt(i)=='\\') {
                    filene=src.substring(i+1,src.length());
                    break all;
                }
            }
        }
        if(file.isFile()){
            decompressfun(src,dest+"\\"+filene);
        }
        else {
            File file2 = new File(dest+"\\"+filene);
            file2.mkdirs();
            String[] filename = file.list();
            for (int i = 0;i< filename.length;i++){
                File file1 = new File(src+"\\"+filename[i]);
                if(file1.isDirectory()){
                    hehe(src+"\\"+filename[i],dest+"\\"+filene);
                }
                else {
                    decompressfun(src+"\\"+filename[i],dest+"\\"+filene+"\\"+filename[i]);
                }
            }
        }
    }
    public static void compressfun(String src,String dest){
        getarr(src);
        savetree(dest);
        myHuffmanTree mytree = new myHuffmanTree();
        mytree.createTree(weight,value);//构建出哈夫曼树
        gaincode(mytree.validnode);
        mycompress(src,dest);
        return;
    }
    public static void decompressfun(String source,String dest){
        mydecompress(source,dest);
        return;
    }
    public static void gaincode(treeNode[] nodes){
        for(int i = 0;i < nodes.length;i++){
            String result = "";
            treeNode node = nodes[i];
            int index = Integer.parseInt(node.getValue());
            while (node.getParent() != null){
                if(node.getParent().getChildLeft() == node){
                    result = "0" + result;
                }
                else {
                    result = "1" + result;
                }
                node = node.getParent();
            }
            code[index]=result;
        }
        return;
    }
    public static void getarr(String path){
        for(int i = 0; i < 256;i++){
            weight[i]=0;
            value[i]=i+"";
        }
        File file = new File(path);
        InputStream in = null;
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                weight[tempbyte] += 1;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        return;
    }
    public static void mycompress(String source,String dest){
        try {
            // 一次读一个字节
            InputStream is=new FileInputStream(source);
            DataInputStream in = new DataInputStream(is);
            OutputStream os = new FileOutputStream(dest,true);
            DataOutputStream out = new DataOutputStream(os);
            //用于记录重构哈夫曼树所需要的数据
            //for(int i = 0;i < 256;i++){
            //  out.write(weight[i]);
            //  }
            int tempbyte;
            String record ="";//用于记录编码
            String gainbyte="";//用于把8位编码转换成byte
            byte result;
            while ((tempbyte = in.read()) != -1) {
                record += code[tempbyte];
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //这里有问题，如果最后一个不足八位怎么办？
                while (record.length()>8 || record.length() == 8){
                    gainbyte=record.substring(0,8);
                    if(record.length()>8){
                        record = record.substring(8,record.length());
                    }
                    else {
                        record="";
                    }
                    result = Integer.valueOf(gainbyte,2).byteValue();
                    out.writeByte(result);
                }
            }
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //这里如何处理八位八位后剩下的不足八位的代码？
            if(record.length() != 0){
                result = Integer.valueOf(record,2).byteValue();
                out.writeByte(result);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        return;
    }
    //储存重构哈夫曼树所需要的信息
    public static void savetree(String path){
        int num=0;
        try {
            /*FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0;i < 256;i++){
                bw.write(weight[i]);
                bw.write(" ");
                System.out.print("1");
            }*/
            OutputStream os = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(os);
            for(int i = 0;i < 256;i++){
                if(weight[i] !=0){
                    num++;
                }
            }
            dos.writeInt(num);
            for(int i = 0;i < 256;i++){
                if(weight[i] !=0){
                    dos.writeInt(i);
                    dos.writeInt(weight[i]);
                }
            }
           /* FileInputStream fi = new FileInputStream(file);
            int b;
            while ((b=fi.read()) != -1){
                System.out.println(b);
            }*/
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
    public static void mydecompress(String source,String dest){
        int num = 0;
        try {
            InputStream is=new FileInputStream(source);
            DataInputStream in = new DataInputStream(is);
            OutputStream os = new FileOutputStream(dest);
            DataOutputStream out = new DataOutputStream(os);
            for(int i = 0;i < 256;i++){
                weight[i]=0;
                value[i] = i+"";
            }
            num = in.readInt();
            while (num > 0){
                weight[in.readInt()]=in.readInt();
                num--;
            }
            myHuffmanTree recontree = new myHuffmanTree();
            recontree.createTree(weight,value);
            treeNode root = recontree.list.get(0);
            treeNode ele = root;
            int a,b,c,d,e,f,g,h,rbyte;
            while ((rbyte=in.read()) != -1){
                String binofint = Integer.toBinaryString((int) rbyte);
                String binofbyte="";
                //补足为8位！
                if(binofint.length()-8<0){
                    if(8-binofint.length()==1){
                        binofbyte = "0"+binofint;
                    }
                    if(8-binofint.length()==2){
                        binofbyte = "00"+binofint;
                    }
                    if(8-binofint.length()==3){
                        binofbyte = "000"+binofint;
                    }
                    if(8-binofint.length()==4){
                        binofbyte = "0000"+binofint;
                    }
                    if(8-binofint.length()==5){
                        binofbyte = "00000"+binofint;
                    }
                    if(8-binofint.length()==6){
                        binofbyte = "000000"+binofint;
                    }
                    if(8-binofint.length()==7){
                        binofbyte = "0000000"+binofint;
                    }
                }
                else {
                    binofbyte=binofint;
                }
                a=Integer.parseInt(binofbyte.substring(0,1));
                b=Integer.parseInt(binofbyte.substring(1,2));
                c=Integer.parseInt(binofbyte.substring(2,3));
                d=Integer.parseInt(binofbyte.substring(3,4));
                e=Integer.parseInt(binofbyte.substring(4,5));
                f=Integer.parseInt(binofbyte.substring(5,6));
                g=Integer.parseInt(binofbyte.substring(6,7));
                h=Integer.parseInt(binofbyte.substring(7,8));
                int i = 0;
                while (i <8){
                    if(i==0){
                        if(a == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==1){
                        if(b == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==2){
                        if(c == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==3){
                        if(d == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==4){
                        if(e == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==5){
                        if(f == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==6){
                        if(g == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    if(i==7){
                        if(h == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = Integer.parseInt(ele.getValue());
                            byte result = (byte)resultofint;
                            out.writeByte(result);
                            ele=root;
                        }
                    }
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        return;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("简易压缩器");
        primaryStage.setHeight(200);
        primaryStage.setWidth(300);
        //初始界面
        GridPane pane = new GridPane();
        //压缩界面
        GridPane pane1 = new GridPane();
        //解压界面
        GridPane pane2 = new GridPane();
        //初始界面
        Button com = new Button("Compress");
        Button decom = new Button("Decompress");
        //压缩界面
        Button button1 = new Button("压缩文件");
        Button button2 = new Button("压缩文件夹");
        Button button3 = new Button("压缩到....");
        Button button4 = new Button("开始压缩....");
        //解压界面
        Button button5 = new Button("解压文件");
        Button button6 = new Button("这里不能点");
        Button button7 = new Button("解压到....");
        Button button8 = new Button("开始解压....");

        //返回按钮
        Button button = new Button("返回");
        Button button9 = new Button("返回");
        //初始界面
        pane.setConstraints(com,0,0);
        pane.setConstraints(decom,1,0);
        pane.setPadding(new Insets(40));
        pane.getChildren().addAll(com,decom);
        //压缩界面
        pane1.setConstraints(button1,0,0);
        pane1.setConstraints(button2,1,0);
        pane1.setConstraints(button3,0,1);
        pane1.setConstraints(button4,1,1);
        pane1.setConstraints(button,0,2);
        pane1.setPadding(new Insets(40));
        pane1.getChildren().addAll(button1,button2,button3,button4,button);
        //解压界面
        pane2.setConstraints(button5,0,0);
        pane2.setConstraints(button6,1,0);
        pane2.setConstraints(button7,0,1);
        pane2.setConstraints(button8,1,1);
        pane2.setConstraints(button9,0,2);
        pane2.setPadding(new Insets(40));
        pane2.getChildren().addAll(button5,button6,button7,button8,button9);

        Scene scene = new Scene(pane);
        Scene scene1 = new Scene(pane1);
        Scene scene2 = new Scene(pane2);
        com.setOnAction(event -> {
            primaryStage.setScene(scene1);
        });
        decom.setOnAction(event -> {
            primaryStage.setScene(scene2);
        });
        button.setOnAction(event -> {
            primaryStage.setScene(scene);
        });
        button9.setOnAction(event -> {
            primaryStage.setScene(scene);
        });
        button1.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                pacpath=filePath;
            }
        });
        button5.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                pacpath=filePath;
            }
        });
        button2.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                pacpath=filePath;
            }
        });
        button6.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                pacpath=filePath;
            }
        });
        button3.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                respacpath=filePath;
            }
        });
        button7.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                respacpath=filePath;
            }
        });
        button4.setOnAction(event ->{
            System.out.println("Begin!");
            haha(pacpath,respacpath);
            System.out.println("Success!");
        });
        button8.setOnAction(event ->{
            System.out.println("Begin!");
            hehe(pacpath,respacpath);
            System.out.println("Success!");
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
