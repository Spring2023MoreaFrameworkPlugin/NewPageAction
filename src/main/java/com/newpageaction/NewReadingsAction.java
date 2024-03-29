package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.io.OutputStream;

public class NewReadingsAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MoreaUtils morea = new MoreaUtils();
        String input = Messages.showInputDialog("Enter Readings Page Name:", "New Readings Page", Messages.getQuestionIcon(), "reading-example", new InputValidator() {
            @Override
            public boolean checkInput(String inputString) {
                return !inputString.isEmpty();
            }

            @Override
            public boolean canClose(String inputString) {
                return checkInput(inputString);
            }
        });

        // Check if name to create is not null
        if (input != null) {
            // Check input name matches MOREA naming convention
            input = morea.toMoreaName(input, "reading");
            VirtualFile directory = morea.checkDupes(e, input);

            String finalInput = input;
            WriteAction.run(() -> {
                try {
                    VirtualFile readingFile = directory.createChildData(this, finalInput + ".md");
                    try (OutputStream outputStream = readingFile.getOutputStream(this)) {
                        String content = "---\n" +
                                "title: \"CHANGE ME\"\n" +
                                "published: true\n" +
                                "morea_id: " + finalInput + "\n" +
                                "morea_type: reading\n" +
                                "morea_summary: \"CHANGE ME\"\n" +
                                "morea_sort_order: \n" +
                                "morea_start_date: \n" +
                                "morea_labels: \n" +
                                "---\n\n" +
                                "## \"CHANGE ME\"\n\n" ;
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

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        VirtualFile selectedFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (selectedFile != null) {
            VirtualFile parent = selectedFile.getParent();
            e.getPresentation().setEnabledAndVisible(parent != null && "morea".equals(parent.getName()));
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }
}
