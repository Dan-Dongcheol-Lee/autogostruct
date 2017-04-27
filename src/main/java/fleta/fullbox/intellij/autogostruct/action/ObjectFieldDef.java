package fleta.fullbox.intellij.autogostruct.action;

import org.apache.commons.lang.WordUtils;

import java.util.List;

import static org.apache.commons.lang.StringUtils.leftPad;

public class ObjectFieldDef implements FieldDef {
    private String field;
    private List<FieldDef> fields;
    private int level;

    public ObjectFieldDef(String field, List<FieldDef> fields, int level) {
        this.field = field;
        this.fields = fields;
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String pad = leftPad(" ", level * 2);
        sb.append("\n").append(pad)
          .append(WordUtils.capitalize(field)).append(" struct {");

        fields.forEach(f -> sb.append(f.toString()));

        sb.append("\n").append(pad)
          .append("} `json:\"" + field + "\"`");

        return sb.toString();
    }
}
