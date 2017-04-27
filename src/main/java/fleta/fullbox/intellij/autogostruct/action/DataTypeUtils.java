package fleta.fullbox.intellij.autogostruct.action;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

public class DataTypeUtils {

    public static String getDataType(JsonNode node) {
        String datatype = "string";
        if (node.isBigDecimal() || node.isDouble() || node.isFloat()) {
            datatype =  "float64";
        } else if (node.isBinary()) {
            datatype = "string";
        } else if (node.isBoolean()) {
            datatype = "bool";
        } else if (node.isBigInteger() || node.isLong()) {
            datatype = "int64";
        } else if (node.isInt() || node.isShort()) {
            datatype = "int";
        } else if (node.isObject()) {
            datatype = "struct";
        }

        if (node.isArray()) {
            Iterator<JsonNode> children = node.elements();
            if (children.hasNext()) {
                JsonNode first = children.next();
                if (first.isValueNode()) {
                    return "[]" + getDataType(first);
                }
            }
            return "[]" + datatype;
        }
        return datatype;
    }
}
