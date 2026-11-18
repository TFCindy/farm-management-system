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
public class BaseDialog extends JDialog {
    
     public BaseDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    
    // Same translation methods as BaseForm but for JDialog
    protected void applyLabelTranslation(JLabel label, String englishText) {
        String kinyarwandaText = TranslationManager.getTranslation(englishText);
        label.setText("<html>" + englishText + "<br/><small><i>(" + kinyarwandaText + ")</i></small></html>");
        label.setVerticalTextPosition(SwingConstants.TOP);
    }
    
    protected void applyButtonTranslation(JButton button, String englishText) {
        String kinyarwandaText = TranslationManager.getTranslation(englishText);
        button.setText("<html><center>" + englishText + "<br/><small>" + kinyarwandaText + "</small></center></html>");
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


 protected void showDualLanguageError(String englishMessage) {
        String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
        String message = "<html><div style='text-align: center;'>" + 
                        englishMessage + 
                        "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
        JOptionPane.showMessageDialog(this, message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    // Dual language success message for dialogs
    protected void showDualLanguageSuccess(String englishMessage) {
        String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
        String message = "<html><div style='text-align: center;'>" + 
                        englishMessage + 
                        "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
        JOptionPane.showMessageDialog(this, message, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Dual language info message for dialogs
    protected void showDualLanguageMessage(String englishMessage) {
        String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
        String message = "<html><div style='text-align: center;'>" + 
                        englishMessage + 
                        "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
        JOptionPane.showMessageDialog(this, message);
    }
    
    // Dual language confirmation dialog for dialogs
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
    
    // Static method for dual language messages (for static contexts)
    public static void showStaticDualMessage(String englishMessage) {
        String kinyarwandaText = TranslationManager.getTranslation(englishMessage);
        String message = "<html><div style='text-align: center;'>" + 
                        englishMessage + 
                        "<br/><small><i>" + kinyarwandaText + "</i></small></div></html>";
        JOptionPane.showMessageDialog(null, message);
    }
}