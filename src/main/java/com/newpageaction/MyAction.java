package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import java.io.IOException;
import java.io.OutputStream;
public class MyAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Show a dialog to get the input for Page Type
        String input1 = Messages.showInputDialog("Enter Page Type:", "New Morea Page", Messages.getQuestionIcon(), "", new InputValidator() {
            @Override
            public boolean checkInput(String input) {
                return !input.isEmpty();
            }

            @Override
            public boolean canClose(String input) {
                return checkInput(input);
            }
        });
        // Show a dialog to get the input for Page Name
        String input2 = Messages.showInputDialog("Enter Page Name:", "Page Name", Messages.getQuestionIcon(), "", new InputValidator() {
            @Override
            public boolean checkInput(String input) {
                return !input.isEmpty();
            }

            @Override
            public boolean canClose(String input) {
                return checkInput(input);
            }
        });
        // Check if both inputs are not null
        if (input1 != null && input2 != null) {
            // Get the selected file in the project view
            VirtualFile selectedFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
            // Check if the selected file is not null
            if (selectedFile == null) {
                return;
            }

            // Get the directory where the action is performed
            VirtualFile directory = selectedFile.isDirectory() ? selectedFile : selectedFile.getParent();
            // this is needed or else you can't perform actions on the plugin test
            WriteAction.run(() -> {
                try {
                    // create a .md file as input1(type)-input2(name).md
                    VirtualFile newFile = directory.createChildData(this, input1 + "-" + input2 + ".md");
                    // some random input to write to the file
                    try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "## " + input2 + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
}
