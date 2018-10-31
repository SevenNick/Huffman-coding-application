import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class DecompressTool {

    private long[] weight;

    public DecompressTool() {
        weight = new long[256];
    }

    public void decompressFile(String source,String dest){

        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            DataInputStream in = new DataInputStream(fileInputStream);

            String fileUrl;
            while (true){
                fileUrl = getBaseUrl(in);
                if (fileUrl.equals(""))
                    break;

                System.out.println("fileUrl: " + fileUrl);
                String outputPath = dest + fileUrl;
                System.out.println("outputPath: " + outputPath);
                String parentPath = outputPath.substring(0, outputPath.lastIndexOf('\\'));
                System.out.println("parentPath: " + parentPath);
                File file = new File(parentPath);
                if(!file.exists()){
                    file.mkdirs();
                }

                if (fileUrl.endsWith("\\"))
                    break;


                DataOutputStream out = new DataOutputStream(new FileOutputStream(outputPath));
                //according the message saved before to re-construction the huffmanTree
                for(int i = 0;i < 256;i++){
//                    weight[i]= in.readLong();
                    weight[i]= in.readLong();
                }
                HuffmanTree huffmanTree = new HuffmanTree();
                TreeNode root = huffmanTree.createHuffmanTree(weight);

//                long fileSize = in.readLong();//read the file size
                long fileSize = in.readLong();//read the file size

                TreeNode ele = root;
                int readByte;
//                while ((readByte=in.read()) != -1 && fileSize>0){
                while ((readByte=in.read()) != -1 && fileSize>0){
                    String binaryOfInt = Integer.toBinaryString(readByte);
                    //one byte's length should be 8,eg. 100 -> 00000100
                    for (;binaryOfInt.length()<8;){
                        binaryOfInt ="0"+binaryOfInt;
                    }
                    for (int i=0;i<binaryOfInt.length();i++){
                        if(binaryOfInt.charAt(i)=='0')
                            ele = ele.getChildLeft();
                        else
                            ele = ele.getChildRight();
                        if(ele.getChildLeft()==null && ele.getChildRight() == null){//find the meaningful leave
                            byte result = (byte)ele.getValue();
                            out.writeByte(result);
                            fileSize--;
                            if (fileSize==0)
                                break;
                            ele=root;
                        }
                    }
                }
                in.read();//file end
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getBaseUrl(DataInputStream dataInputStream)throws IOException{
        if (dataInputStream.available()==0)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        char reading;
        while ( (reading = dataInputStream.readChar()) != '\n' ){
            stringBuilder.append(reading);
        }
        return stringBuilder.toString();
    }
}
