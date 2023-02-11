package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

          File file= new File(input +".md");
          System.out.println(input + ".md");
          System.out.println("Executing from: "+ System.getProperty("user.dir"));
          FileWriter fw;
          try{
          if (file.exists())
          {
            Messages.showErrorDialog("Another Module with the same name exists, please rename your module","Error");
          }
          else
          {
            boolean wasFileCreated = file.createNewFile();
            fw = new FileWriter(file);
            fw.append("XXX");
            System.out.println("File Creation: " + wasFileCreated);
          }

          } catch (IOException ex) {
            System.out.println("File Writing Failed: [Error Code 1]");
            throw new RuntimeException(ex);

          }
        }
        else{
          System.out.println("Input is null");
        }
    }
}
