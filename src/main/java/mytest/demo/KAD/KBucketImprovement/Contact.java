package mytest.demo.KAD.KBucketImprovement;

import mytest.demo.KAD.LoadRouterInfo;
import mytest.demo.KAD.util.ReadPeerProfile;
import mytest.demo.KAD.util.WebSpider;
import net.i2p.data.Hash;
import net.i2p.data.SimpleDataStructure;
import net.i2p.data.router.RouterInfo;
import net.i2p.router.peermanager.PeerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * k桶中存储的contact
 *
 * @author sophie
 * @create 2019-01-22 上午9:21
 **/
public class Contact extends SimpleDataStructure {
    private Hash hash;
    private PeerProfile peerProfile;
    private RouterInfo routerInfo;
    private Float speedValue;
    private Float capacityValue;
    {
        this.speedValue = 200 * new Random().nextFloat();
        this.capacityValue = 200 * new Random().nextFloat();
    }

    public Float getSpeedValue() {
        return speedValue;
    }

    public void setSpeedValue(Float speedValue) {
        this.speedValue = speedValue;
    }

    public Float getCapacityValue() {
        return capacityValue;
    }

    public void setCapacityValue(Float capacityValue) {
        this.capacityValue = capacityValue;
    }

    public Contact() {

    }

    public Contact(Hash hash, PeerProfile peerProfile, RouterInfo routerInfo) {
        this.hash = hash;
        this.peerProfile = peerProfile;
        this.routerInfo = routerInfo;
    }

    public Contact(Hash hash) {
        this.hash = hash;
    }

    public Hash getHash() {
        return hash;
    }

    public PeerProfile getPeerProfile() {
        return peerProfile;
    }

    public RouterInfo getRouterInfo() {
        return routerInfo;
    }

    public void setHash(PeerProfile peerProfile) {
        this.hash = peerProfile.getPeer();
    }

    public void setPeerProfile(PeerProfile peerProfile) {
        this.peerProfile = peerProfile;
    }

    public void setRouterInfo(RouterInfo routerInfo) {
        this.routerInfo = routerInfo;
    }

    public static List<Contact> getAllContacts(List<PeerProfile> peerProfiles) {
        LoadRouterInfo loadRouterInfo = new LoadRouterInfo();
        List<Contact> contactList = new ArrayList<>();
        for (PeerProfile profile : peerProfiles) {
            Hash peer = profile.getPeer();
            String s = peer.toBase64();
            char c = s.charAt(0);
            String file = "/Users/sophie/Library/Application Support/i2p/netDb/r" + c + "/routerinfo-" + s + ".dat";
            RouterInfo router = loadRouterInfo.getOneRouterInfo(file);
            Contact contact = new Contact(peer);
            contact.setPeerProfile(profile);
            contact.setRouterInfo(router);
            contactList.add(contact);
        }
        return contactList;
    }

    public static Contact getContactFromHash(Hash hash) {
        String s = hash.toBase64();
        char c = s.charAt(0);
        String profileFile = "/Users/sophie/Library/Application Support/i2p/peerProfiles/p" + c + "/profile-" + s + ".txt.gz";
        String routerFile = "/Users/sophie/Library/Application Support/i2p/netDb/r" + c + "/routerinfo-" + s + ".dat";
        PeerProfile profile = ReadPeerProfile.getOnePeerProfile(profileFile);
        RouterInfo routerInfo = LoadRouterInfo.getOneRouterInfo(routerFile);
        Contact contact = new Contact(hash, profile, routerInfo);
        WebSpider.getContactSC(contact,s);
        return contact;
    }

    public static Contact getContactFromPath(String hash) {
        char c = hash.charAt(0);
        String profileFile = "/Users/sophie/Library/Application Support/i2p/peerProfiles/p" + c + "/profile-" + hash + ".txt.gz";
        String routerFile = "/Users/sophie/Library/Application Support/i2p/netDb/r" + c + "/routerinfo-" + hash + ".dat";
        PeerProfile profile = ReadPeerProfile.getOnePeerProfile(profileFile);
        RouterInfo routerInfo = LoadRouterInfo.getOneRouterInfo(routerFile);
        Contact contact = new Contact();
        contact.setRouterInfo(routerInfo);
        contact.setPeerProfile(profile);
        WebSpider.getContactSC(contact,hash);
        return contact;
    }

    @Override
    public int length() {
        return hash.length();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "hash=" + hash +
                ", peerProfile=" + peerProfile +
                ", routerInfo=" + routerInfo +
                '}';
    }


    @Override
    public byte[] getData() {
        return hash.getData();
    }
//    public static void main(String[] args) {
//        List<Contact> allContacts = getAllContacts(ReadPeerProfile.getAllPeerProfiles());
//    }
}
