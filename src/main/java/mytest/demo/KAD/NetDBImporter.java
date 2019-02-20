package mytest.demo.KAD;

import mytest.demo.KAD.data.NetDbEntries;
import mytest.demo.KAD.util.FileUtil;
import net.i2p.data.DataFormatException;
import net.i2p.data.router.RouterInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Reads files from the I2P netDB folder and converts them into I2Ps own class
 * RouterInfo.
 * 
 * @author mullj10
 * @return NetDbEntries with all imported routerInfos
 */
public class NetDBImporter {

	private String netDBFolder;
	List<File> inputFiles = new ArrayList<File>();

	public NetDBImporter(String netDBFolder) {
		this.netDBFolder = netDBFolder;
	}

	/**
	 * 将netDb中的routerinfo文件读成RouterInfo实例
	 * @return
	 */
	public NetDbEntries readNetDB() {

		Set<RouterInfo> netDbEntries = new HashSet<>();
		int numberOfErrors = 0;
		boolean processingError = false;
		File netDBRoot = new File(netDBFolder);
		if (!netDBRoot.exists()) {
			System.err.println("NetDB folder not found!");
			System.exit(1);
		}

		// 读取所有netDb中的文件
		inputFiles=FileUtil.listFiles(netDBFolder);

		for (File file : inputFiles) {
			
			// 获得文件输入流
			try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
				RouterInfo router = new RouterInfo();
				router.readBytes(input);
				netDbEntries.add(router);
			} catch (DataFormatException | IOException ex) {
				if (ex.getClass().equals(FileNotFoundException.class)) {

				} else {
					System.err.println("Failed to load one netDB entry.");
					ex.printStackTrace();
				}
				numberOfErrors++;
			}
		}

		if (numberOfErrors >= 20){
			processingError = true;
		}
		
		return new NetDbEntries(netDbEntries, processingError);
	}
}
