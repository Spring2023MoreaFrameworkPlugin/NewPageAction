package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
public class MyAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String input = Messages.showInputDialog("Enter item name:", "New Item", Messages.getQuestionIcon(), "", new InputValidator() {
            @Override
            public boolean checkInput(String input) {
                return !input.isEmpty();
            }

            @Override
            public boolean canClose(String input) {
                return checkInput(input);
            }
        });

        if (input != null) {
            // Do something with the input, for example, create a new file with the specified name
            // in the current project directory
        }
    }
}
