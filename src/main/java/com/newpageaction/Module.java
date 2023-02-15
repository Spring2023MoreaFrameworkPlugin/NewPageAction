package com.newpageaction;


import com.intellij.ide.DataManager;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.actionSystem.CommonDataKeys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class Module extends AnAction {



  @Override
    public void actionPerformed(AnActionEvent e) {
        String input = Messages.showInputDialog("Enter Morea Module Name:", "New Morea Module", Messages.getQuestionIcon(), "ExampleModule", new InputValidator() {
            @Override
            public boolean checkInput(String input) {
                return !input.isEmpty();
            }

            @Override
            public boolean canClose(String input) {
                return checkInput(input);
            }
        });
        System.out.println("Starting File Sequence");
        if (input != null) {
            // Do something with the input, for example, create a new file with the specified name
            // in the current project directory
            VirtualFile folder = e.getData(CommonDataKeys.VIRTUAL_FILE);
            System.out.println("File Path: " + folder);
            // within the folder, insert a file
            // this is needed or else you can't perform actions on the plugin test
            WriteAction.run(() -> {
                try {
                    // create a directory with the specified name
                    VirtualFile newDir = folder.createChildDirectory(this, input);
                    // create a .md file called module-input.md inside the new directory
                    VirtualFile newFile = newDir.createChildData(this, "module" + "-" + input + ".md");
                    // Create the file so it matches
                    try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "---\ntitle: \"" + input + "\"\npublished: true\nmorea_coming_soon: false\nmorea_id: \nmorea_outcomes:\nmorea_readings:\nmorea_experiences:\nmorea_assessments:\nmorea_type: module\nmorea_icon_url:\nmorea_start_date:\nmorea_end_date:\nmorea_labels:\nmorea_sort_order:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        else{
          System.out.println("Input is null");
        }
    }
}
