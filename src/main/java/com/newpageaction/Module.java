package com.newpageaction;


import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import com.newpageaction.settings.AppSettingsState;

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
    AppSettingsState settings = AppSettingsState.getInstance();
    boolean outcomes = settings.outcomeStatus;
    boolean assessments = settings.assessmentStatus;
    boolean experiences = settings.experienceStatus;
    boolean readings = settings.readingStatus;

    System.out.println("Settings\n________");
    System.out.println("Outcomes: " + outcomes);
    System.out.println("Assessments: " + assessments);
    System.out.println("Experiences: "+ experiences);
    System.out.println("Readings: " + readings);




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
                        String content = "---\ntitle: \"" + input + "\"\npublished: true\nmorea_coming_soon: false\nmorea_id: "+ input+"-module \nmorea_outcomes:\n  -"+input+"-outcome\nmorea_readings:\n  -"+input+"-reading\nmorea_experiences:\n  -"+input+"-experience\nmorea_assessments:\n  -"+input+"-assessment\nmorea_type: module\nmorea_icon_url:\nmorea_start_date:\nmorea_end_date:\nmorea_labels:\nmorea_sort_order:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if(readings == true) {
                      //Create Reading.md Template
                      newFile = newDir.createChildData(this, input + "-" + "Reading" + ".md");
                      // Create the file so it matches
                      try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "---\nmorea_id: " + input + "-reading \ntitle: \"" + input + "\"\npublished: true\nmorea_summary: \nmorea_type: reading\nmorea_sort_order: 2\nmorea_labels:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                      } catch (IOException ex) {
                        ex.printStackTrace();
                      }
                    }

                    if(assessments == true) {
                      //Create Assessment.md Template
                      newFile = newDir.createChildData(this, input + "-" + "Assessment" + ".md");
                      // Create the file so it matches
                      try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "---\nmorea_id: " + input + "-assessment \ntitle: \"" + input + "\"\npublished: true\nmorea_summary: \nmorea_type: assessment\nmorea_sort_order: 2\nmorea_labels:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                      } catch (IOException ex) {
                        ex.printStackTrace();
                      }
                    }

                    if(outcomes == true) {
                      //Create Outcome.md Template
                      newFile = newDir.createChildData(this, input + "-" + "Outcome" + ".md");
                      // Create the file so it matches
                      try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "---\nmorea_id: " + input + "-outcome \ntitle: \"" + input + "\"\npublished: true\nmorea_summary: \nmorea_type: outcome\nmorea_sort_order: 2\nmorea_labels:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                      } catch (IOException ex) {
                        ex.printStackTrace();
                      }
                    }

                    if(experiences == true) {
                      //Create Experience.md Template
                      newFile = newDir.createChildData(this, input + "-" + "Experience" + ".md");
                      // Create the file so it matches
                      try (OutputStream outputStream = newFile.getOutputStream(this)) {
                        String content = "---\nmorea_id: " + input + "-experience \ntitle: \"" + input + "\"\npublished: true\nmorea_summary: \nmorea_type: experience\nmorea_sort_order: 2\nmorea_labels:\n---\n\n## " + input + "\n\nThis is a sample content for the newly created .md file.";
                        outputStream.write(content.getBytes());
                      } catch (IOException ex) {
                        ex.printStackTrace();
                      }
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

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        VirtualFile selectedFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (selectedFile != null) {
            e.getPresentation().setEnabledAndVisible("morea".equals(selectedFile.getName()));
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }


}



