package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class NewExperiencesAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        DuplicateFiles Dupe = new DuplicateFiles();
        String input = Messages.showInputDialog("Enter Experiences Page Name:", "New Experiences Page", Messages.getQuestionIcon(), "experience-example", new InputValidator() {
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
            if (!input.contains("experience")) {
                input = "experience-" + input;
            }
            VirtualFile selectedFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
            // Check if the selected file is not null
            if (selectedFile == null) {
                return;
            }

            // Get the directory where the action is performed
            VirtualFile directory = selectedFile.isDirectory() ? selectedFile : selectedFile.getParent();
            // Construct the file name
            String fileName = input + ".md";
            // Check if file exists in parent directory or any subdirectories
            if (Dupe.fileExistsInDirectoryOrSubdirectories(new File(directory.getParent().getPath()), fileName)) {
                Messages.showMessageDialog("File already exists.", "Error", Messages.getErrorIcon());
                return;
            }

            String finalInput = input;
            WriteAction.run(() -> {
                try {
                    VirtualFile readingFile = directory.createChildData(this, finalInput + ".md");
                    try (OutputStream outputStream = readingFile.getOutputStream(this)) {
                        String content = "---\n" +
                                "title: \"" + finalInput + "\"\n" +
                                "published: true\n" +
                                "morea_id: " + finalInput + "\n" +
                                "morea_type: experience\n" +
                                "morea_summary: example summary\n" +
                                "morea_sort_order: \n" +
                                "morea_start_date: \n" +
                                "morea_labels: \n" +
                                "---\n\n" +
                                "## " + finalInput + "\n\n" +
                                "This is a sample content for the newly creating experience.md file";
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
