import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.*;

public class Main extends Application {
    static long[] weight = new long[256];
    static String[] code = new String[256];

    static String srcPath="";//要压缩的文件夹在哪里
    static String destPath="";//压缩的文件放在哪里

    private static TreeNode root;
    public static void main(String args[]){
        launch(args);
    }
/*    public static void compress(String src,String dest){
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
            compressFun(src,dest+"\\"+filene);
        }
        else {
            File file2 = new File(dest+"\\"+filene);
            file2.mkdirs();
            String[] filename = file.list();
            for (int i = 0;i< filename.length;i++){
                File file1 = new File(src+"\\"+filename[i]);
                if(file1.isDirectory()){
                    compress(src+"\\"+filename[i],dest+"\\"+filene);
                }
                else {
                    compressFun(src+"\\"+filename[i],dest+"\\"+filene+"\\"+filename[i]);
                }
            }
        }
    }
    public static void decompress(String src,String dest){
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
            decompressFun(src,dest+"\\"+filene);
        }
        else {
            File file2 = new File(dest+"\\"+filene);
            file2.mkdirs();
            String[] filename = file.list();
            for (int i = 0;i< filename.length;i++){
                File file1 = new File(src+"\\"+filename[i]);
                if(file1.isDirectory()){
                    decompress(src+"\\"+filename[i],dest+"\\"+filene);
                }
                else {
                    decompressFun(src+"\\"+filename[i],dest+"\\"+filene+"\\"+filename[i]);
                }
            }
        }
    }*/
/*    public static void compressFun(String src,String dest){
        countWeight(src);
        saveTree(dest);
        HuffmanTree huffmanTree= new HuffmanTree();
        huffmanTree.createHuffmanTree(weight);//构建出哈夫曼树
        gainCode(huffmanTree.getMeanningfulNodeList());
        myCompress(src,dest);
    }*/
/*    public static void decompressFun(String source,String dest){
        myDecompress(source,dest);
    }*/
    //统计0-255出现的次数
/*    public static void countWeight(String path){
        for(int i = 0; i < 256;i++){
            weight[i]=0;
        }
        File file = new File(path);
        InputStream in = null;
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                weight[tempbyte]++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    //储存重构哈夫曼树所需要的信息
    /*public static void saveTree(String path){
        try {
            OutputStream os = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(os);
//            for(int i = 0;i < 256;i++){
//                if(weight[i] !=0){
//                    num++;
//                }
//            }
//            dos.writeInt(num);
//            for(int i = 0;i < 256;i++){
//                if(weight[i] !=0){
//                    dos.writeInt(i);
//                    dos.writeInt(weight[i]);
//                }
//            }
            for (int i = 0; i < 256; i++) {
                dos.writeLong(weight[i]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return;
    }*/
    //获得编码
/*    public static void gainCode(TreeNode[] nodes){
        for(int i = 0;i < nodes.length;i++){
            String result = "";
            TreeNode node = nodes[i];
            int index = node.getValue();
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
    }*/

    /*public static void myCompress(String source,String dest){
        try {
            // 一次读一个字节
            File file = new File(source);
            long fileSize = file.length();
            InputStream is=new FileInputStream(source);
            DataInputStream in = new DataInputStream(is);
            OutputStream os = new FileOutputStream(dest,true);
            DataOutputStream out = new DataOutputStream(os);

            out.writeLong(fileSize);

            int tempbyte;
            String record ="";//用于记录编码
            String gainbyte="";//用于把8位编码转换成byte
            byte result;
            while ((tempbyte = in.read()) != -1) {
                record += code[tempbyte];
                while (record.length()>8 || record.length() == 8){
                    gainbyte=record.substring(0,8);
                    if(record.length()>8){
                        record = record.substring(8);
                    }
                    else {
                        record="";
                    }
                    result = Integer.valueOf(gainbyte,2).byteValue();
                    out.writeByte(result);
                }
            }
            if(record.length() != 0){
                for (; record.length() < 8;) {
                    record = record + "0";
                }
                result = Integer.valueOf(record,2).byteValue();
                out.writeByte(result);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        return;
    }*/

    /*public static void myDecompress(String source,String dest){

        try {
            InputStream is=new FileInputStream(source);
            DataInputStream in = new DataInputStream(is);
            OutputStream os = new FileOutputStream(dest);
            DataOutputStream out = new DataOutputStream(os);
            for(int i = 0;i < 256;i++){
                weight[i]= in.readLong();
            }
            HuffmanTree huffmanTree = new HuffmanTree();
            root = huffmanTree.createHuffmanTree(weight);

            long fileSize = in.readLong();

            TreeNode ele = root;
            int rbyte;
            while ((rbyte=in.read()) != -1 && fileSize>0){
                String binofint = Integer.toBinaryString((int) rbyte);
                //补足为8位！
                for (;binofint.length()<8;){
                    binofint ="0"+binofint;
                }
                for (int i=0;i<binofint.length();i++){
                    if(binofint.charAt(i)=='0')
                        ele = ele.getChildLeft();
                    else
                        ele = ele.getChildRight();
                    if(ele.getChildLeft()==null && ele.getChildRight() == null){
                        int resultofint = ele.getValue();
                        byte result = (byte)resultofint;
                        out.writeByte(result);
                        fileSize--;
                        if (fileSize==0)
                            break;
                        ele=root;
                    }
                }
               *//* if(binofint.length()-8<0){
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
                h=Integer.parseInt(binofbyte.substring(7,8));*//*
                //int i = 0;
               *//* while (i <8){
                    if(i==0){
                        if(a == 0){
                            ele = ele.getChildLeft();
                        }
                        else {
                            ele=ele.getChildRight();
                        }
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){
                            int resultofint = ele.getValue();
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
                            int resultofint = ele.getValue();
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
                }*//*
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("简易压缩器");
        primaryStage.setHeight(400);
        primaryStage.setWidth(400);
        //初始界面
        GridPane initialPane = new GridPane();
        Button cp = new Button("Compress");
        Button dcp = new Button("Decompress");
        initialPane.setConstraints(cp,0,0);
        initialPane.setConstraints(dcp,1,0);
        initialPane.setPadding(new Insets(40));
        initialPane.getChildren().addAll(cp,dcp);
        initialPane.setHgap(10);initialPane.setAlignment(Pos.CENTER);
        //压缩界面
        GridPane cpPane = new GridPane();
        Button cpFile = new Button("压缩文件");cpFile.setMinWidth(80);
        TextField fpath=new TextField();fpath.setEditable(false);

        Button cpDir = new Button("压缩文件夹");cpDir.setMinWidth(80);
        TextField dirpath=new TextField();dirpath.setEditable(false);

        Button cpTo = new Button("压缩到....");cpTo.setMinWidth(80);
        TextField destpath=new TextField();destpath.setEditable(false);

        Button cpBegin = new Button("开始压缩....");cpBegin.setMinWidth(80);
        Button button = new Button("返回");

        Label result=new Label("Welcome!");

        cpPane.setConstraints(cpFile,0,0);cpPane.setConstraints(fpath,1,0);
        cpPane.setConstraints(cpDir,0,1);cpPane.setConstraints(dirpath,1,1);
        cpPane.setConstraints(cpTo,0,2);cpPane.setConstraints(destpath,1,2);
        cpPane.setConstraints(cpBegin,0,3);
        cpPane.setConstraints(button,1,3);
        cpPane.setConstraints(result,0,4,2,2);
        cpPane.setPadding(new Insets(40));
        cpPane.getChildren().addAll(cpFile,cpDir,cpTo,cpBegin,button,fpath,dirpath,destpath,result);
        cpPane.setHgap(10); cpPane.setVgap(10);cpPane.setAlignment(Pos.CENTER);
        //解压界面
        GridPane dcpPane = new GridPane();
        Button dcpFile = new Button("解压文件");dcpFile.setMinWidth(80);
        TextField fpath1=new TextField();fpath1.setEditable(false);

        Button useless = new Button("这里不能点");useless.setMinWidth(80);
        TextField dirpath1=new TextField("This is useless!");dirpath1.setEditable(false);

        Button dcpTo = new Button("解压到....");dcpTo.setMinWidth(80);
        TextField destpath1=new TextField();destpath1.setEditable(false);

        Button dcpBegin = new Button("开始解压....");dcpBegin.setMinWidth(80);
        Button button1 = new Button("返回");

        Label result1=new Label("Welcome!");

        dcpPane.setConstraints(dcpFile,0,0);dcpPane.setConstraints(fpath1,1,0);
        dcpPane.setConstraints(useless,0,1);dcpPane.setConstraints(dirpath1,1,1);
        dcpPane.setConstraints(dcpTo,0,2);dcpPane.setConstraints(destpath1,1,2);
        dcpPane.setConstraints(dcpBegin,0,3);
        dcpPane.setConstraints(button1,1,3);
        dcpPane.setConstraints(result1,0,4,2,2);

        dcpPane.setPadding(new Insets(40));
        dcpPane.getChildren().addAll(dcpFile,useless,dcpTo,dcpBegin,button1,fpath1,dirpath1,destpath1,result1);

        dcpPane.setHgap(10); dcpPane.setVgap(10);dcpPane.setAlignment(Pos.CENTER);
        Scene initialScene = new Scene(initialPane);
        Scene cpScene = new Scene(cpPane);
        Scene dcpScene = new Scene(dcpPane);
        cp.setOnAction(event -> {
            primaryStage.setScene(cpScene);
        });
        dcp.setOnAction(event -> {
            primaryStage.setScene(dcpScene);
        });
        button.setOnAction(event -> {
            primaryStage.setScene(initialScene);
        });
        button1.setOnAction(event -> {
            primaryStage.setScene(initialScene);
        });
        cpFile.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                srcPath=filePath;
                fpath.setText(filePath);
                dirpath.setText("");
            }
        });
        dcpFile.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                srcPath=filePath;
                fpath1.setText(filePath);
            }
        });
        cpDir.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                srcPath=filePath;
                dirpath.setText(filePath);
                fpath.setText("");
            }
        });
        useless.setOnAction(event ->{
            //do nothing
        });
        cpTo.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                destPath=filePath;
                destpath.setText(filePath);
            }
        });
        dcpTo.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                destPath=filePath;
                destpath1.setText(filePath);
            }
        });
        cpBegin.setOnAction(event ->{
            result.setText("Begin!");
//            compress(srcPath,destPath);
            result.setText("Success!");
        });
        dcpBegin.setOnAction(event ->{
            result1.setText("Begin!");
//            decompress(srcPath,destPath);
            result1.setText("Success!");
        });
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }
}
