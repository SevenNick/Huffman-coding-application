import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class DecompressTool {

    private long[] weight;

    public DecompressTool() {
        weight = new long[256];
    }

    /**
     * @param source : the source file to decompress
     * @param dest   : the destination you want to put the decompress result
     */
    public void decompress(String source, String dest) {

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(source));
            String fileUrl;
            while (true) {
                fileUrl = getBaseUrl(in);

                if (fileUrl.equals(""))//decompress end
                    break;

                String outputPath = dest + fileUrl;
                String parentPath = outputPath.substring(0, outputPath.lastIndexOf('\\'));
                File file = new File(parentPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                if (fileUrl.endsWith("\\"))//it's a empty folder,no need for the following steps
                    break;

                DataOutputStream out = new DataOutputStream(new FileOutputStream(outputPath));
                //according the message saved before to re-construction the huffmanTree
                for (int i = 0; i < 256; i++) {
                    weight[i] = in.readLong();
                }
                HuffmanTree huffmanTree = new HuffmanTree();
                TreeNode root = huffmanTree.createHuffmanTree(weight);

                long fileSize = in.readLong();//read the file size

                TreeNode ele = root;
                int readByte;
                while ((readByte = in.read()) != -1 && fileSize > 0) {
                    String binaryOfInt = Integer.toBinaryString(readByte);
                    //one byte's length should be 8,eg. 100 -> 00000100
                    for (; binaryOfInt.length() < 8; ) {
                        binaryOfInt = "0" + binaryOfInt;
                    }
                    for (int i = 0; i < binaryOfInt.length(); i++) {
                        if (binaryOfInt.charAt(i) == '0')
                            ele = ele.getChildLeft();
                        else
                            ele = ele.getChildRight();
                        if (ele.getChildLeft() == null && ele.getChildRight() == null) {//find the meaningful leave
                            byte result = (byte) ele.getValue();
                            out.writeByte(result);
                            fileSize--;
                            if (fileSize == 0)
                                break;
                            ele = root;
                        }
                    }
                }
                in.read();//read the last \n
                out.close();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get the file relative path
    private String getBaseUrl(DataInputStream dataInputStream) throws IOException {
        if (dataInputStream.available() == 0)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        char reading;
        while ((reading = dataInputStream.readChar()) != '\n') {
            stringBuilder.append(reading);
        }
        return stringBuilder.toString();
    }
}
