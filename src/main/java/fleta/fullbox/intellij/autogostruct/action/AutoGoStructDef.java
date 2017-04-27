package fleta.fullbox.intellij.autogostruct.action;

import java.util.List;

public class AutoGoStructDef implements FieldDef {
    private List<FieldDef> fields;

    public AutoGoStructDef(List<FieldDef> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type ").append(getRandGoStructName()).append(" struct {");

        fields.forEach(field -> sb.append(field.toString()));

        sb.append("\n}");
        return sb.toString();
    }

    private String getRandGoStructName() {
        return "AutoGoStruct";
    }
}
