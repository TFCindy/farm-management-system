/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import javax.swing.*;
import util.TranslationManager;
/**
 *
 * @author Munezero Cindyyy
 */
public class BaseForm extends JFrame {
    
    // Common translation methods available to all forms
    protected void applyLabelTranslation(JLabel label, String englishText) {
        String kinyarwandaText = TranslationManager.getTranslation(englishText);
        label.setText("<html>" + englishText + "<br/><small><i>(" + kinyarwandaText + ")</i></small></html>");
        label.setVerticalTextPosition(SwingConstants.TOP);
    }
    
    protected void applyButtonTranslation(JButton button, String englishText) {
    String kinyarwandaText = TranslationManager.getTranslation(englishText);
    button.setText("<html><center>" + englishText + "<br/><small>" + kinyarwandaText + "</small></center></html>");
}
    
    protected void applyTitleTranslation(JLabel label, String englishText) {
        String kinyarwandaText = TranslationManager.getTranslation(englishText);
        label.setText("<html><center>" + englishText + "<br/><small>(" + kinyarwandaText + ")</small></center></html>");
    }
    
    protected void showTranslatedMessage(String englishMessage) {
        String translated = TranslationManager.getTranslation(englishMessage);
        JOptionPane.showMessageDialog(this, translated);
    }
    
    protected void showTranslatedError(String englishMessage) {
        String translated = TranslationManager.getTranslation(englishMessage);
        JOptionPane.showMessageDialog(this, translated, 
            TranslationManager.getTranslation("Validation Error"), 
            JOptionPane.ERROR_MESSAGE);
    }
    protected void showDualLanguageMessage(String englishMessage) {
    String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
    String message = "<html><div style='text-align: center;'>" + 
                    englishMessage + 
                    "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
    JOptionPane.showMessageDialog(this, message);
}
protected int showDualLanguageConfirm(String englishMessage, String englishTitle) {
    String kinyarwandaMessage = TranslationManager.getTranslation(englishMessage);
    String kinyarwandaTitle = TranslationManager.getTranslation(englishTitle);
    
    String dualMessage = "<html><div style='text-align: center;'>" +
                        englishMessage +
                        "<br/><small><i>" + kinyarwandaMessage + "</i></small></div></html>";
    
    String dualTitle = englishTitle + " / " + kinyarwandaTitle;
    
    return JOptionPane.showConfirmDialog(this,
        dualMessage,
        dualTitle,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
}
protected void showDualLanguageError(String englishMessage) {
    String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
    String message = "<html><div style='text-align: center;'>" + 
                    englishMessage + 
                    "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
    JOptionPane.showMessageDialog(this, message, 
        "Validation Error", 
        JOptionPane.ERROR_MESSAGE);
}

protected void showDualLanguageSuccess(String englishMessage) {
    String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
    String message = "<html><div style='text-align: center;'>" + 
                    englishMessage + 
                    "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
    JOptionPane.showMessageDialog(this, message, 
        "Success", 
        JOptionPane.INFORMATION_MESSAGE);
}

 public static void showStaticDualMessage(String englishMessage) {
        String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
        String message = "<html><div style='text-align: center;'>" + 
                        englishMessage + 
                        "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
        JOptionPane.showMessageDialog(null, message);
    }
    
    // Static method for dual language confirmation dialogs
    public static int showStaticDualConfirm(String englishMessage, String englishTitle) {
        String kinyarwandaMessage = TranslationManager.getTranslation(englishMessage);
        String kinyarwandaTitle = TranslationManager.getTranslation(englishTitle);
        
        String dualMessage = "<html><div style='text-align: center;'>" +
                            englishMessage +
                            "<br/><small><i>" + kinyarwandaMessage + "</i></small></div></html>";
        
        String dualTitle = englishTitle + " / " + kinyarwandaTitle;
        
        return JOptionPane.showConfirmDialog(null,
            dualMessage,
            dualTitle,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    }
    protected void showDualLanguageWelcome(String firstName, String lastName) {
    String userName = firstName + " " + lastName;
    String englishMessage = "Login successful! Welcome, " + userName;
    String kinyarwandaMessage = TranslationManager.getTranslation("Login successful! Welcome, ") + userName;
    
    String dualMessage = "<html><div style='text-align: center;'>" +
                        englishMessage +
                        "<br/><small><i>" + kinyarwandaMessage + "</i></small></div></html>";
    
    JOptionPane.showMessageDialog(this, dualMessage, 
        "Success", 
        JOptionPane.INFORMATION_MESSAGE);
}
   protected void applyWelcomeTitleTranslation(JLabel label, String englishText, String userName) {
    String englishWelcome = englishText + ", " + userName;
    
    // Translate the complete welcome phrase with placeholder
    String welcomePattern = englishText + ", {0}";
    String kinyarwandaPattern = TranslationManager.getTranslation(welcomePattern);
    String kinyarwandaWelcome = kinyarwandaPattern.replace("{0}", userName);
    
    label.setText("<html><center>" + englishWelcome + "<br/><small>(" + kinyarwandaWelcome + ")</small></center></html>");
}
}
