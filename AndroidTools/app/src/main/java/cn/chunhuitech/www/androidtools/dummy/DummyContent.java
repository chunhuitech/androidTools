package cn.chunhuitech.www.androidtools.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 1;


    public static final String INDEX_ROOT = "1";
    public static final String INDEX_DEVICE = "2";
    public static final String INDEX_STEP = "3";

    static {
        //查看root权限
        DummyItem rootItem =  new DummyItem(INDEX_ROOT, "是否有root权限", "点击按钮查看");
        addItem(rootItem);
        //查看设备信息
        DummyItem deviceItem =  new DummyItem(INDEX_DEVICE, "查看设备信息", "点击按钮查看");
        addItem(deviceItem);

        //查看计步信息
        DummyItem stepItem =  new DummyItem(INDEX_STEP, "查看计步", "计步");
        addItem(stepItem);


        // Add some sample items.
//        for (int i = 100; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
