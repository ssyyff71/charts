package mytest.demo.KAD.util;

import net.i2p.router.RouterContext;
import net.i2p.router.peermanager.PeerProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 从文件读PeerProfile
 *
 * @author sophie
 * @create 2019-01-02 上午10:02
 **/
public class ReadPeerProfile {

    private static String fileLocation = "/Users/sophie/Library/Application Support/i2p/peerProfiles/p1";
    private static List<File> inputFiles = new ArrayList<File>();

    public static List<PeerProfile> getAllPeerProfiles() {
        RouterContext ctx = new RouterContext(null); // new net.i2p.router.Router());
        ProfilePersistenceHelper helper = new ProfilePersistenceHelper(ctx);
        List<File> inputFiles = FileUtil.listFiles(fileLocation);
        List<PeerProfile> peerProfiles = new ArrayList<>();
        for (int i = 0; i < inputFiles.size(); i++) {
            PeerProfile profile = helper.readProfile(inputFiles.get(i));
            if (profile == null) {
                System.err.println("Could not load profile for number " + i);
                continue;
            }
//            System.out.println("peerprofile "+i);
            peerProfiles.add(profile);
        }
        return peerProfiles;
    }

    public static PeerProfile getOnePeerProfile(String filePath) {
        RouterContext ctx = new RouterContext(null); // new net.i2p.router.Router());
        ProfilePersistenceHelper helper = new ProfilePersistenceHelper(ctx);
        File file = new File(filePath);
        PeerProfile profile = helper.readProfile(file);
        if (profile == null) {
            System.err.println("Could not load profile for " + filePath);
        }
        return profile;
    }

}
