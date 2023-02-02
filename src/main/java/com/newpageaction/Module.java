package com.newpageaction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import java.io.FileWriter;
import java.io.IOException;
public class Module extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String input = Messages.showInputDialog("Enter Morea Module Name:", "New Morea Module", Messages.getQuestionIcon(), "", new InputValidator() {
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
          try {
            FileWriter myWriter = new FileWriter(input + ".md");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException n) {
            System.out.println("An error occurred.");
            n.printStackTrace();
          }
        }
        else{
        }
    }
}
