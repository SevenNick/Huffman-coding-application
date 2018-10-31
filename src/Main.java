import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;

public class Main extends Application {
    private static String srcPath="";
    private static String destPath="";

    private static CompressTool compressTool;
    private static DecompressTool decompressTool;

    public static void main(String args[]){
        compressTool = new CompressTool();
        decompressTool = new DecompressTool();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("简易压缩器----By:TomMonkey");
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

        Button cpTo = new Button("压缩到....");cpTo.setMinWidth(80);
        TextField destpath=new TextField();destpath.setEditable(false);

        Text destFileNameText = new Text("     压缩为：");destFileNameText.setTextAlignment(TextAlignment.CENTER);
        TextField destFileName=new TextField();

        Button cpBegin = new Button("开始压缩....");cpBegin.setMinWidth(80);
        Button button = new Button("返回");

        Label result=new Label("Welcome!");

        cpPane.setConstraints(cpFile,0,0);cpPane.setConstraints(fpath,1,0);
        cpPane.setConstraints(cpTo,0,1);cpPane.setConstraints(destpath,1,1);
        cpPane.setConstraints(destFileNameText,0,2);cpPane.setConstraints(destFileName,1,2);
        cpPane.setConstraints(cpBegin,0,3);
        cpPane.setConstraints(button,1,3);
        cpPane.setConstraints(result,0,4,2,2);
        cpPane.setPadding(new Insets(40));
        cpPane.getChildren().addAll(cpFile,destFileNameText,cpTo,cpBegin,button,fpath,destFileName,destpath,result);
        cpPane.setHgap(10); cpPane.setVgap(10);cpPane.setAlignment(Pos.CENTER);

        //解压界面
        GridPane dcpPane = new GridPane();

        Button dcpFile = new Button("解压文件");dcpFile.setMinWidth(80);
        TextField fpath1=new TextField();fpath1.setEditable(false);

        Button dcpTo = new Button("解压到....");dcpTo.setMinWidth(80);
        TextField destpath1=new TextField();destpath1.setEditable(false);

        Text decompressDestText = new Text("  解压为(可选):");
        TextField decompressDest=new TextField("");

        Button dcpBegin = new Button("开始解压....");dcpBegin.setMinWidth(80);
        Button button1 = new Button("返回");

        Label result1=new Label("Welcome!");

        dcpPane.setConstraints(dcpFile,0,0);dcpPane.setConstraints(fpath1,1,0);
        dcpPane.setConstraints(dcpTo,0,1);dcpPane.setConstraints(destpath1,1,1);
        dcpPane.setConstraints(decompressDestText,0,2);dcpPane.setConstraints(decompressDest,1,2);
        dcpPane.setConstraints(dcpBegin,0,3);
        dcpPane.setConstraints(button1,1,3);
        dcpPane.setConstraints(result1,0,4,2,2);

        dcpPane.setPadding(new Insets(40));
        dcpPane.getChildren().addAll(dcpFile,decompressDestText,dcpTo,dcpBegin,button1,fpath1,decompressDest,destpath1,result1);

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
            fpath.setText("");
            destpath.setText("");
            destFileName.setText("");
            srcPath="";
            destPath="";
        });
        button1.setOnAction(event -> {
            primaryStage.setScene(initialScene);
            fpath1.setText("");
            destpath1.setText("");
            decompressDest.setText("");
            srcPath="";
            destPath="";
        });
        cpFile.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            fileChooser.showOpenDialog(fileChooser);
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filePath);
            srcPath=filePath;
            fpath.setText(filePath);
        });
        dcpFile.setOnAction(event ->{
            JFileChooser fileChooser = new JFileChooser();
            //set file filter, only .tm permit
            TomMonkeyFileFilter tomMonkeyFileFilter = new TomMonkeyFileFilter();
            fileChooser.addChoosableFileFilter(tomMonkeyFileFilter);
            fileChooser.setFileFilter(tomMonkeyFileFilter);

            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath= fileChooser.getSelectedFile().getAbsolutePath();
                srcPath=filePath;
                fpath1.setText(filePath);
            }
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
            compressTool.compress(srcPath,destPath+"\\"+destFileName.getText()+".tm");
            result.setText("Success!");
        });
        dcpBegin.setOnAction(event ->{
            result1.setText("Begin!");
            if (decompressDest.getText().equals("")){
                decompressTool.decompress(srcPath,destPath);
            }
            else {
                decompressTool.decompress(srcPath,destPath+"\\"+decompressDest.getText());
            }
            result1.setText("Success!");
        });
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }
}

//when decompress, one can only choose the file end with .tm
class TomMonkeyFileFilter extends FileFilter {

    public String getDescription() {
        return "*.tm";
    }

    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        return pathname.isDirectory() || name.toLowerCase().endsWith(".tm");
    }
}
