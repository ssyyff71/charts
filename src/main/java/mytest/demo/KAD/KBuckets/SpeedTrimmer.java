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
public class SpeedTrimmer<T extends SimpleDataStructure> implements KBucketTrimmer<T> {
    protected final I2PAppContext _ctx;
    private final int _max;

    public SpeedTrimmer(I2PAppContext ctx, int max) {
        _ctx = ctx;
        _max = max;
    }

    /**
     * @param kbucket the kbucket that is now too big
     * @param toAdd
     * @return
     */
    public boolean trim(KBucket<T> kbucket, T toAdd) {
        List<T> e = new ArrayList<>(kbucket.getEntries());
        int sz = e.size();
        String toAddString = toAdd.toBase64();
        Contact toAddContact = Contact.getContactFromPath(toAddString);
        float speedValue = toAddContact.getSpeedValue();
        float minSpeedValue = speedValue;
        int minIndex = _ctx.random().nextInt(sz);
        for (int i = 0; i < sz; i++) {
            Hash hash = (Hash) e.get(i);
            Contact contactFromHash = Contact.getContactFromHash(hash);

            float speedValueInBucket = contactFromHash.getSpeedValue();
            if (speedValueInBucket <= minSpeedValue) {
                minSpeedValue = speedValueInBucket;
                minIndex = i;
            }

        }
        if (speedValue <= minSpeedValue) return false;
        // concurrency
        if (sz < _max)
            return true;
        T toRemove = e.get(minIndex);
        kbucket.remove(toRemove);
        return true;
    }

}
