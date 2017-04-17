package fleta.fullbox.intellij.autogostruct.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.actions.CodeInsightAction;
import org.jetbrains.annotations.NotNull;

public class JSONToGoStructAction extends CodeInsightAction {

    @NotNull
    @Override
    protected CodeInsightActionHandler getHandler() {
        return new JSONToGoStructActionHandler();
    }
}
