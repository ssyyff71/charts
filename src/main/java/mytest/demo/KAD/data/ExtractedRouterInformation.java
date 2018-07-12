package mytest.demo.KAD.data;

import java.util.List;

/**
 * Data class to hold information about an I2P router to create statistics.
 *
 */
public class ExtractedRouterInformation {

	private String hash;
	private List<String> ipaddresses;
	private int isFloodfil;
	private int knownLeaseSets;
	private int knownRouters;
	private String routerVersion;
	private String caps;
	private long createTime;

	
	public ExtractedRouterInformation(String hash, List<String> ipaddresses, int isFloodfil,
			int knownLeaseSets, int knownRouters, String routerVersion) {
		super();
		this.hash = hash;
		this.ipaddresses = ipaddresses;
		this.isFloodfil = isFloodfil;
		this.knownLeaseSets = knownLeaseSets;
		this.knownRouters = knownRouters;
		this.routerVersion = routerVersion;
	}

	public ExtractedRouterInformation(String hash, List<String> ipaddresses, int isFloodfil, int knownLeaseSets, int knownRouters, String routerVersion, String caps, long createTime) {
		this.hash = hash;
		this.ipaddresses = ipaddresses;
		this.isFloodfil = isFloodfil;
		this.knownLeaseSets = knownLeaseSets;
		this.knownRouters = knownRouters;
		this.routerVersion = routerVersion;
		this.caps = caps;
		this.createTime = createTime;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}


	public String getHash() {
		return hash;
	}

	public List<String> getIpaddresses() {
		return ipaddresses;
	}

	public int isFloodfil() {
		return isFloodfil;
	}

	public int getKnownLeaseSets() {
		return knownLeaseSets;
	}
	
	public int getKnownRouters() {
		return knownRouters;
	}
	
	public String getRouterVersion() {
		return routerVersion;
	}

	public String getCaps() {
		return caps;
	}

	public void setCaps(String caps) {
		this.caps = caps;
	}

	public long getCreateTime() {
		return createTime;
	}

	public String toString(){
		return ("RouterInfo: Hash=" + hash + ", address=" + ipaddresses.toString() +  
				", floodfilRouter=" + isFloodfil + ", knownLeaseSets=" + knownLeaseSets + 
				", knownRouters=" + knownRouters +", routerVersion=" + routerVersion+",caps="+caps);
	}
}
