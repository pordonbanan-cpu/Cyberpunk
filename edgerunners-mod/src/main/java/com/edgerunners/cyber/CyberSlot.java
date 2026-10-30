package com.edgerunners.cyber;

public enum CyberSlot {
    FRONTAL_CORTEX("frontal_cortex", 3),
    OPERATING_SYSTEM("operating_system", 1),
    ARMS("arms", 1),
    FACE("face", 1),
    SKELETON("skeleton", 2),
    HANDS("hands", 2),
    NERVOUS_SYSTEM("nervous_system", 3),
    CIRCULATORY_SYSTEM("circulatory_system", 3),
    INTEGUMENTARY_SYSTEM("integumentary_system", 3),
    LEGS("legs", 1);

    public final String key;
    public final int count;
    private int offset;

    public static final int TOTAL;
    private static final CyberSlot[] OWNER;

    static {
        int o = 0;
        for (CyberSlot s : values()) { s.offset = o; o += s.count; }
        TOTAL = o;
        OWNER = new CyberSlot[o];
        for (CyberSlot s : values())
            for (int i = 0; i < s.count; i++) OWNER[s.offset + i] = s;
    }

    CyberSlot(String key, int count) { this.key = key; this.count = count; }

    public int offset() { return offset; }

    public static CyberSlot fromIndex(int index) { return OWNER[index]; }

    public String langKey() { return "cyberware.edgerunners.slot." + key; }
}
