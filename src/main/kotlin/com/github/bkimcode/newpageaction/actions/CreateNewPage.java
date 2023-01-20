package com.github.bkimcode.newpageaction.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;

public class CreateNewPage extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        String fileName = Messages.showInputDialog(project, "Enter the file name", "Create New File", Messages.getInformationIcon(), "", new InputValidator() {
            @Override
            public boolean checkInput(String inputString) {
                return true;
            }

            @Override
            public boolean canClose(String inputString) {
                return true;
            }
        });

        if (fileName == null) {
            return;
        }

        // Code to create the file goes here

        Messages.showMessageDialog(project, "File created with name: " + fileName, "File Created", Messages.getInformationIcon());
    }
}
