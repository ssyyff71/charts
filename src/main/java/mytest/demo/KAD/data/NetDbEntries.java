package mytest.demo.KAD.data;

import net.i2p.data.router.RouterInfo;

import java.util.Set;

public class NetDbEntries {

	private Set<RouterInfo> netDbEntries;
	private boolean processingError;
	
	public NetDbEntries(Set<RouterInfo> netDbEntries, boolean processingError) {
		super();
		this.netDbEntries = netDbEntries;
		this.processingError = processingError;
	}

	public Set<RouterInfo> getNetDbEntries() {
		return netDbEntries;
	}

	public boolean hadProcessingError() {
		return processingError;
	}
}
