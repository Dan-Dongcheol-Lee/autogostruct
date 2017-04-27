package fleta.fullbox.intellij.autogostruct.action;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang.WordUtils;

import java.util.Objects;

import static org.apache.commons.lang.StringUtils.leftPad;

public class ValueFieldDef implements FieldDef {
    private String field;
    private String type;
    private final int level;

    public ValueFieldDef(String field, JsonNode node, int level) {
        this.field = field;
        this.type = DataTypeUtils.getDataType(node);
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueFieldDef that = (ValueFieldDef) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String pad = leftPad(" ", level * 2);
        sb.append("\n").append(pad)
          .append(WordUtils.capitalize(field) + " " + type + " `json:\"" + field + "\"`");
        return sb.toString();
    }
}
