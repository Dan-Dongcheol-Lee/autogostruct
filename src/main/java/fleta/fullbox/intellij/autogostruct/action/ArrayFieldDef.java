package fleta.fullbox.intellij.autogostruct.action;

import org.apache.commons.lang.WordUtils;

import java.util.Set;

import static org.apache.commons.lang.StringUtils.leftPad;

public class ArrayFieldDef implements FieldDef {
    private String field;
    private Set<FieldDef> elements;
    private int level;

    public ArrayFieldDef(String field, Set<FieldDef> elements, int level) {
        this.field = field;
        this.elements = elements;
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String pad = leftPad(" ", level * 2);
        sb.append("\n").append(pad)
          .append(WordUtils.capitalize(field)).append(" []struct {");

        elements.forEach(el -> sb.append(el.toString()));

        sb.append("\n").append(pad)
          .append("} `json:\"" + field + "\"`");

        return sb.toString();
    }
}
