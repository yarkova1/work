package first;

import org.junit.Before;
import org.junit.Test;


public class MainTest {

    SkipList skipList = new SkipList();

    @Before
    public void start() {
        skipList.skipInsert(6);
        skipList.skipInsert(15);
        skipList.skipInsert(17);
        skipList.skipInsert(4);
        skipList.printSkipList();
    }


    @Test
    public void removeExistingNode() {
        System.out.println("\nTrying to remove 4\n");
        skipList.remove(4);
        skipList.printSkipList();
    }

    @Test
    public void removeAllInnerNodes() {
        System.out.println("\nTrying to remove all inner nodes");
        skipList.remove(4);
        skipList.remove(15);
        skipList.remove(17);
        skipList.remove(6);
        skipList.printSkipList();
    }

    @Test
    public void removeNonExistentNode() {
        System.out.println("\nTrying to remove 13\n");
        skipList.remove(13);
    }


}
