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
public class CapacityTrimmer<T extends SimpleDataStructure> implements KBucketTrimmer<T> {
    protected final I2PAppContext _ctx;
    private final int _max;

    public CapacityTrimmer(I2PAppContext ctx, int max) {
        _ctx = ctx;
        _max = max;
    }

    /**
     * 从已经存满k个节点信息的k桶中选出一个节点信息将它删去，如果删除成功返回 true，未删除返回false
     *
     * @param kbucket 新加入节点应该加入的k桶
     * @param toAdd   新加入的节点
     * @return
     */
    public boolean trim(KBucket<T> kbucket, T toAdd) {
        List<T> e = new ArrayList<>(kbucket.getEntries());
        int sz = e.size();
        String toAddString = toAdd.toBase64();
        Contact toAddContact = Contact.getContactFromPath(toAddString);
        float capacityValue = toAddContact.getCapacityValue();
        float minCapacityValue = capacityValue;
        int minIndex = _ctx.random().nextInt(sz);
        for (int i = 0; i < sz; i++) {
            Hash hash = (Hash) e.get(i);
            Contact contactFromHash = Contact.getContactFromHash(hash);

            float capacityValueInBucket = contactFromHash.getCapacityValue();
            if (capacityValueInBucket < minCapacityValue) {
                minCapacityValue = capacityValueInBucket;
                minIndex = i;
            }

        }
        if (capacityValue <= minCapacityValue) return false;
        // concurrency
        if (sz < _max)
            return true;
        T toRemove = e.get(minIndex);
        kbucket.remove(toRemove);
        return true;
    }

}
