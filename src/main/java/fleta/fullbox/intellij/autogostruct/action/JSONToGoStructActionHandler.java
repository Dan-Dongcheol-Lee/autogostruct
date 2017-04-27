package fleta.fullbox.intellij.autogostruct.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.lang.ContextAwareActionHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JSONToGoStructActionHandler implements CodeInsightActionHandler, ContextAwareActionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile psiFile) {
        String selectedText = editor.getSelectionModel().getSelectedText();

        Optional<String> generated = jsonToGoStruct(selectedText);
        System.out.println("generated: [" + generated.orElse("") + "]");
    }

    public Optional<String> jsonToGoStruct(String selectedText) {

        if (selectedText == null || selectedText.trim().equals("")) {
            return Optional.empty();
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(selectedText.trim());
            return Optional.of(startTraverse(jsonNode));

        } catch (IOException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    public String startTraverse(JsonNode jsonNode) {
        List<FieldDef> fields = new ArrayList<>();

        jsonNode.fields().forEachRemaining(entry -> fields.addAll(traverse(entry.getKey(), entry.getValue(), 1)));

        return new AutoGoStructDef(fields).toString();
    }

    public static List<FieldDef> traverse(String field, JsonNode node, int level) {

        List<FieldDef> fields = new ArrayList<>();

        if (node.isArray()) {
            boolean isLeafArray = true;
            Iterator<JsonNode> children = node.elements();
            if (children.hasNext()) {
                JsonNode first = children.next();
                if (first.isObject()) {
                    isLeafArray = false;
                }
            }

            if (isLeafArray) {
                fields.add(new ValueFieldDef(field, node, level));

            } else {
                //Unique and keep order of elements
                Set<FieldDef> elements = new LinkedHashSet<>();
                node.elements().forEachRemaining(child ->
                        child.fields().forEachRemaining(entry ->
                                elements.addAll(traverse(entry.getKey(), entry.getValue(), level + 1))));
                fields.add(new ArrayFieldDef(field, elements, level));
            }

        } else if (node.isObject()) {

            List<FieldDef> elements = new ArrayList<>();
            node.fields().forEachRemaining(entry ->
                    elements.addAll(traverse(entry.getKey(), entry.getValue(), level + 1)));

            fields.add(new ObjectFieldDef(field, elements, level));

            // Otherwise, ValueNode
        } else {
            fields.add(new ValueFieldDef(field, node, level));
        }
        return fields;
    }

    @Override
    public boolean isAvailableForQuickList(@NotNull Editor editor, @NotNull PsiFile psiFile, @NotNull DataContext dataContext) {
        return false;
    }

}