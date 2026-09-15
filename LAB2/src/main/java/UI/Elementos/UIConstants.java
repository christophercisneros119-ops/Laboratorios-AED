package UI.Elementos;

import java.awt.Color;
import java.awt.Font;

public final class UIConstants {
    private UIConstants() {}

    // ── Colores compartidos ──
    public static final Color WHITE = Color.WHITE;
    public static final Color DARK_AQUA = new Color(0, 51, 51);
    public static final Color DARK_GRAY = new Color(51, 51, 51);

    // ── Overlay / Veil ──
    public static final Color VEIL_TOP = new Color(4, 12, 16, 30);
    public static final Color VEIL_MID = new Color(4, 12, 16, 80);
    public static final Color VEIL_BOTTOM = new Color(4, 12, 16, 150);

    // ── Fuentes ──
    public static final Font FONT_TASKBAR = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_TITLE_XL = new Font("Swis721 Blk BT", Font.PLAIN, 36);
    public static final Font FONT_TITLE = new Font("Swis721 Blk BT", Font.PLAIN, 24);
    public static final Font FONT_SUBTITLE = new Font("Swis721 Blk BT", Font.PLAIN, 18);
    public static final Font FONT_LABEL = new Font("Swis721 Blk BT", Font.PLAIN, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_CODE = new Font("Monospaced", Font.PLAIN, 12);
}
