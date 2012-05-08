package se.cth.hedgehogphoto.map.geocoding;

import java.awt.Font;
import java.io.StringReader;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;


public final class EditorPane extends JEditorPane {

    private final Font font = new JLabel().getFont(); //new Font(Font.DIALOG, Font.PLAIN, 12);
    private final String stylesheet =
        "body { color:#808080; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:" + font.getName() + "; font-size:" + font.getSize()+ "pt;}" +
        "a    { color:#4040D9; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:" + font.getName() + "; font-size:" + font.getSize() + "pt;}";

    public EditorPane() {
        super("text/html", "");
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        HTMLEditorKit kit = new HTMLEditorKit();
        setEditorKit(kit);
        setEditable(false);
        setOpaque(false);
        HTMLDocument htmlDocument = (HTMLDocument)getDocument();
        StyleSheet sheet = new StyleSheet();
        try {
            sheet.loadRules(new StringReader(stylesheet), null);
            htmlDocument.getStyleSheet().addStyleSheet(sheet);
        } catch (Exception e) {
        }
        htmlDocument.setAsynchronousLoadPriority(-1);
    }

}
