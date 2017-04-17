package fleta.fullbox.intellij.autogostruct.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.lang.ContextAwareActionHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class JSONToGoStructActionHandler implements CodeInsightActionHandler, ContextAwareActionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile psiFile) {
        System.out.println("psiFile: " + psiFile);

        String selectedText = editor.getSelectionModel().getSelectedText();

        Optional<String> generated = jsonToGoStruct(selectedText);
        System.out.println("generated: [" + generated.orElse("") + "]");
    }

    public Optional<String> jsonToGoStruct(String selectedText) {
        System.out.println("selectedText: [" + selectedText + "]");

        if (selectedText == null || selectedText.trim().equals("")) {
            return Optional.empty();
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(selectedText.trim());

            StringBuilder sb = new StringBuilder();
            traverse(sb, getRandGoStructName(), jsonNode, 1);
            return Optional.of(sb.toString());

        } catch (IOException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    private String getRandGoStructName() {
        return "AutoGoStruct" + Instant.now().toEpochMilli();
    }


    public static void traverse(StringBuilder sb, String field, JsonNode node, int level) {
        String pad = StringUtils.leftPad(" ", level * 2);
        if (node.isArray()) {
            JsonNode firstChild = node;
            boolean isLeaf = true;
            for (JsonNode child : node) {
                firstChild = child;
                if (child.isObject()) {
                    isLeaf = false;
                    traverse(sb, field, child, level);
                }
            }
            if (isLeaf) {
                sb.append(getValueNodeField(field, firstChild, level));
            }

        } else if (node.isObject()) {
            sb.append("\n").append(pad);
            if (level == 1) {
                sb.append("type ");
            }
            sb.append(WordUtils.capitalize(field)).append(" struct {");
            node.fields().forEachRemaining(entry -> traverse(sb, entry.getKey(), entry.getValue(), level + 1));
            sb.append("\n").append(pad)
                    .append("}");

            if (level > 1) {
                sb.append(" `json:\"" + field + "\"`");
            }

        // Otherwise, ValueNode
        } else {
            sb.append(getValueNodeField(field, node, level));
        }
    }

    private static String getValueNodeField(String field, JsonNode node, int level) {
        String pad = StringUtils.leftPad(" ", level * 2);
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(pad)
                .append(WordUtils.capitalize(field)).append(" ").append(getRightDataType(node))
                .append(" `json:\"" + field + "\"`");
        return sb.toString();
    }

    private static String getRightDataType(JsonNode node) {
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
        }

        if (node.isArray()) {
            return "[]" + datatype;
        }
        return datatype;
    }

    @Override
    public boolean isAvailableForQuickList(@NotNull Editor editor, @NotNull PsiFile psiFile, @NotNull DataContext dataContext) {
        return false;
    }

}