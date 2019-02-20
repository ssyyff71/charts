package mytest.demo.KAD.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sophie
 * @create 2019-01-22 上午9:41
 **/
public class FileUtil {
    private  static List<File> inputFiles = new ArrayList<File>();

    public static List<File> listFiles(String directory) {

        File folder = new File(directory);

        // filter out directories
        File[] files = folder.listFiles();
        for (File f : files) {

            if (f.isFile()) {
                inputFiles.add(f);
            } else if (f.isDirectory()) {
                listFiles(f.getAbsolutePath());
            }

            if (!f.isDirectory())
                inputFiles.add(f);
        }
        return inputFiles;
    }
}
