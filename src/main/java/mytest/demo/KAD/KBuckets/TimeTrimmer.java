package mytest.demo.KAD.KBuckets;

import mytest.demo.KAD.KBucketImprovement.Contact;
import net.i2p.I2PAppContext;
import net.i2p.data.Hash;
import net.i2p.data.SimpleDataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes a random element. Not resistant to flooding.
 *
 * @since 0.9.2 in i2psnark, moved to core in 0.9.10
 */
public class TimeTrimmer<T extends SimpleDataStructure> implements KBucketTrimmer<T> {
    protected final I2PAppContext _ctx;
    private final int _max;

    public TimeTrimmer(I2PAppContext ctx, int max) {
        _ctx = ctx;
        _max = max;
    }

    public boolean trim(KBucket<T> kbucket, T toAdd) {
        List<T> e = new ArrayList<>(kbucket.getEntries());
        int sz = e.size();
        String toAddString = toAdd.toBase64();
        Contact toAddContact = Contact.getContactFromPath(toAddString);
        long minLastHeard = toAddContact.getPeerProfile().getLastHeardAbout();
        long minLastHeardInBucket = System.currentTimeMillis();
        int minIndex=_ctx.random().nextInt(sz);
        for (int i = 0; i < sz; i++) {
            Hash hash = (Hash) e.get(i);
            Contact contactFromHash = Contact.getContactFromHash(hash);
            if(contactFromHash.getPeerProfile()!=null){
                long lastHeardAbout = contactFromHash.getPeerProfile().getLastHeardAbout();
                if(lastHeardAbout<=minLastHeardInBucket){
                    minLastHeardInBucket=lastHeardAbout;
                    minIndex=i;
                }
            }
        }
        if(minLastHeard>minLastHeardInBucket) return false;
        // concurrency
        if (sz < _max)
            return true;
        T toRemove = e.get(minIndex);
        kbucket.remove(toRemove);
        return true;
    }

}
