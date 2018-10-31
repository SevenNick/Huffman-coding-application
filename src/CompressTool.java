import java.io.*;

public class CompressTool {
    private long[] weight;
    private String[] code;
    private String destFile;

    public CompressTool() {
        weight = new long[256];
        code = new String[256];
    }

    public  void  testCompress(String src, String dest){
        destFile =dest;
        compressIterator(src,"");
    }

    public void compressIterator(String src, String baseURL){
        String currentLevel="";
        for(int i =src.length()-1;i>0;i--){
            if(src.charAt(i)=='\\') {
                currentLevel=src.substring(i+1);
                break;
            }
        }
        baseURL += "\\"+currentLevel;

        File file = new File(src);
        if(file.isFile()){
            compressFile(src,baseURL);
        }
        else {
            String[] filename = file.list();
            if (filename.length==0){
                try {
                    OutputStream os = new FileOutputStream(destFile,true);
                    DataOutputStream dos = new DataOutputStream(os);
                    dos.writeChars(baseURL+"\\"+"\n");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            for (int i = 0;i< filename.length;i++){
                File file1 = new File(src+"\\"+filename[i]);
                if(file1.isDirectory()){
                    compressIterator(src+"\\"+filename[i],baseURL);
                }
                else {
                    compressFile(src+"\\"+filename[i],baseURL+"\\"+filename[i]);
                }
            }
        }
    }

    public void compressFile(String src, String baseUrl) {
        countWeight(src);
        saveTree(destFile,baseUrl);
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.createHuffmanTree(weight);//构建出哈夫曼树
        gainCode(huffmanTree.getMeanningfulNodeList());
        compress(src);
    }

    private void countWeight(String path) {
        for (int i = 0; i < 256; i++) {
            weight[i] = 0;
        }
        try {
            // 一次读一个字节
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
            int tempbyte;
            while ((tempbyte = bufferedInputStream.read()) != -1) {
                weight[tempbyte]++;
            }
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //save the message for tree re-construction
    private void saveTree(String path, String baseUrl) {
        System.out.println(baseUrl);
        try {
            OutputStream os = new FileOutputStream(path,true);
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeChars(baseUrl+"\n");
            for (int i = 0; i < 256; i++) {
                dos.writeLong(weight[i]);
            }
            dos.close();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //process the huffmanTree to get the huffman code for each meaningful node
    private void gainCode(TreeNode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            StringBuilder result = new StringBuilder();
            TreeNode node = nodes[i];
            int index = node.getValue();
            while (node.getParent() != null) {
                if (node.getParent().getChildLeft() == node) {
                    result.insert(0, "0");
                } else {
                    result.insert(0, "1");
                }
                node = node.getParent();//bottom-up
            }
            code[index] = result.toString();
        }
    }

    private void compress(String source) {
        try {
            File file = new File(source);
            long fileSize = file.length();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(destFile, true));

            dataOutputStream.writeLong(fileSize);//record the file size

            int readByte;
            StringBuilder fileCoding = new StringBuilder();//record the huffman-coding for the file
            String outputByte;//represent the byte to output
            byte result;
            while ((readByte = bufferedInputStream.read()) != -1) {
                fileCoding.append(code[readByte]);
                while (fileCoding.length() >= 8) {
                    outputByte = fileCoding.substring(0, 8);
                    if (fileCoding.length() > 8) {
                        fileCoding = new StringBuilder(fileCoding.substring(8));
                    } else {
                        fileCoding = new StringBuilder();
                    }
                    result = Integer.valueOf(outputByte, 2).byteValue();
                    dataOutputStream.writeByte(result);
                }
            }
            if (fileCoding.length() != 0) {
                for (; fileCoding.length() < 8; ) {
                    fileCoding.append("0");
                }
                result = Integer.valueOf(fileCoding.toString(), 2).byteValue();
                dataOutputStream.writeByte(result);
            }
            dataOutputStream.writeChars("\n");
            bufferedInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
