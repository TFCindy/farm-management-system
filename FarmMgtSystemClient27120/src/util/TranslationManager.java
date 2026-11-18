/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Munezero Cindyyy
 */
public class TranslationManager {
     private static final Map<String, String> translations = new HashMap<>();
    
    static {
        // Authentication forms
        // Add these to your TranslationManager class:

translations.put("Close", "Funga");
translations.put("No_notifications_available", "Nta matangazo ahari");

// Kinyarwanda translations
translations.put("notifications_rw", "Amakuru");
translations.put("crops_rw", "Ibihingwa");
translations.put("livestock_rw", "Amatungo"); 
translations.put("close_rw", "Gufunga");
translations.put("no_notifications_available_rw", "Nta makuru ahari");

// Kinyarwanda notification messages
translations.put("crop_harvest_warning_rw", "Igihingwa {0} gitegerejwe gusarurwa vuba ({1} iminsi isigaye).");
translations.put("crop_planting_warning_rw", "Itariki yo guhinga {0} iregereje ({1} iminsi isigaye).");
translations.put("livestock_sick_warning_rw", "Itungo {0} rirwaye. Rikeneye kwitabwaho byihutirwa.");
        translations.put("Recently added livestock and crops", "Amatungo n'ibihingwa biheruka kwandikwa");
        translations.put("Crops", "Ibihingwa");
        translations.put("Livestock", "Amatungo");
        translations.put("Employees", "Abakozi");
        translations.put("Dashboard", "Ahabanza");
        translations.put("Notifications", "Amatangazo");
        translations.put("First Name:", "Izina ry'umukiriya");
        translations.put("Last Name:", "Irindi zina");
        translations.put("Password:", "Ijambobanga");
        translations.put("District:", "Akarere");
        translations.put("Phone number:", "Nomero ya telefoni");
        translations.put("Email:", "Aderesi ya imeyili");
        translations.put("ID:", "Nomero y'indangamuntu");
        translations.put("Sign Up", "Iyandikishe");
        translations.put("Login", "Injira");
        translations.put("Show", "Reba");
        translations.put("Hide", "Hisha");
        translations.put("FARM MANAGEMENT SYSTEM-SIGN UP", "SISITEMU CUNGABUHINZI-KWIYANDIKISHA");
        translations.put("National ID:", "Nomero y'indangamuntu");
        translations.put("Already have an account? Login", "Ufite konti? Kanda hano winjire");
        translations.put("Login via phone number", "Injira ukoresheje nomero ya telefoni");
        translations.put("Add fertilizer details", "Shyiraho ibijyanye n'ifumbire");
        translations.put("View Report", "Reba raporo");
        translations.put("Back to dashboard", "Gusubira ahabanza");
        translations.put("Add contract details", "Ongeraho contira");
        // Dashboard
        translations.put("Welcome, {0}", "Ikaze, {0}");
        translations.put("Dashboard Home", "Ahabanza");
        translations.put("Crop Management", "Gucunga ibihingwa");
        translations.put("Livestock Management", "Gucunga amatungo");
        translations.put("Employee Management", "Gucunga abakozi");
        translations.put("Logout", "Sohoka");
        translations.put("Total Crops", "Ibihingwa byose");
        translations.put("Total Livestock", "Amatungo yose");
        translations.put("Total Employees", "Abakozi bose");
        translations.put("Add Crop", "Andika igihingwa");
        translations.put("Recently added livestock and crops", "Amatungo n'ibihingwa biheruka kwandikishwa");
        translations.put("Add Livestock", "Andika itungo");
        translations.put("Add Employee", "Andika umukozi");
        
        // Crop Management
        translations.put("CROP MANAGEMENT", "GUCUNGA IBIHINGWA");
        translations.put("Crop Name:", "Izina ry'igihingwa");
        translations.put("Planting Date:", "Itariki yo guhinga");
        translations.put("Harvest Date:", "Itariki yo gusarura");
        translations.put("Expected yield:", "Umusaruro utegerejwe");
        translations.put("Status:", "Imimerere");
        translations.put("Add Crop", "Ongeraho igihingwa");
        translations.put("Update Crop", "Hindura igihingwa");
        translations.put("Search Crop", "Shaka igihingwa");
        translations.put("Delete crop", "Siba igihingwa");
        translations.put("All crops", "Reba ibihingwa byose");
        translations.put("Back to dashboard", "Subira ahabanza");
        translations.put("Crop added successfully", "Igihingwa cyanditswe neza");
        translations.put("Login successful! Welcome, ", "Kwinjira byagenze neza! Ikaze,");
        
        
        // Livestock Management  
        translations.put("LIVESTOCK MANAGEMENT", "GUCUNGA AMATUNGO");
        translations.put("Animal type:", "Ubwoko bw'itungo");
        translations.put("Breed(Optional):", "Urubyiruko");
        translations.put("Age:", "Imwaka");
        translations.put("Health status:", "Imimerere y'ubuzima");
        translations.put("Quantity:", "Umubare");
        translations.put("Add livestock", "Ongeraho itungo");
        translations.put("Update livestock", "Hindura amakuru kw'itungo");
        translations.put("Delete livestock", "Siba itungo");
        translations.put("All livestock", "Reba amatungo yose");
        
        // Employee Management
        translations.put("EMPLOYEE MANAGEMENT", "GUCUNGA ABAKOZI");
        translations.put("Name:", "Amazina");
        translations.put("Role:", "Akazi");
        translations.put("Contact:", "Uburyo bwo kuvugana");
        translations.put("Salary(FRW):", "Umushahara");
        translations.put("Hire date:", "Itariki yaherewe akazi");
        translations.put("Add employee", "Ongeraho umukozi");
        translations.put("Update employee", "Hindura amakuru k'umukozi");
        translations.put("Delete employee", "Siba umukozi");
        translations.put("Show All", "Reba byose");
        
        // Dialog forms
        translations.put("Save", "Bika");
        translations.put("Cancel", "Hagarika");
        translations.put("Planted", "Byaribwe");
        translations.put("Harvested", "Byasakwe");
        translations.put("Healthy", "Kira");
        translations.put("Sick", "Ararwaye");
        translations.put("Back to dashboard", "Gusubira inyuma");
        translations.put("Logout", "Gusohoka");
        translations.put("Welcome", "Ikaze");
        
        
        translations.put("FARM MANAGEMENT SYSTEM", "SISITEMU CUNGABUHINZI");
        translations.put("No account? Sign up", "Nta konti ufite?Kanda hano wiyandikishe");
        translations.put("Sign in", "Kwinjira");
        // Error and Success Messages
        translations.put("All fields are required!", "Subiza hose!");
        translations.put("Validation Error", "Ikosa mu kwinjiza");
        translations.put("Login successful!", "Winjiye neza!");
        translations.put("Invalid National ID or password.", "Numero y'indangamuntu cyangwa ijambobanga ntabwo ari byo.");
        translations.put("Signup successful!", "Wiyandikishije neza!");
        translations.put("User with this National ID already exists!", "Uyu munsi afite konti!");
        translations.put("Password must be at least 8 chars, with uppercase, lowercase, number & symbol!", 
                        "Ijambobanga rigomba kuba nibura imibare 8, rikaba ririmo inyuguti nkuru, nto, imibare n'ikimenyetso!");
        translations.put("National ID must be exactly 16 digits!", "Numero y'indangamuntu igomba kuba imibare 16!");
        translations.put("Contact must be exactly 10 digits!", "Numero ya terefone igomba kuba imibare 10!");
        translations.put("Saved Successfully!", "Byakiriwe neza!");
        translations.put("Updated Successfully!", "Byahinduwe neza!");
        translations.put("Deleted Successfully!", "Byasibwe neza!");
        translations.put("Are you sure you want to delete?", "Urifuza gusiba koko?");
        translations.put("Please log in first.", "Banza winjire.");
        translations.put("No farmer session found. Please log in again.", "Banza winjire muri konti yawe.");
        translations.put("Are you sure you want to log out?", "Urashaka gusohoka koko?");
        translations.put("Account exists, proceed to login.", "Konti yarazwi, kanda hano winjire.");
        translations.put("Account Found", "Konti yabonetse");
        translations.put("Record loaded. Click Update to modify or Delete to remove.", "Amakuru yanditswe. Kanda Hindura kugira ngo wahindure cyangwa Siba kugira ngo uyisibe.");
        translations.put("Record Selected", "Amakuru yatoranyijwe");
        translations.put("Please enter an Employee ID to search.", "Andika ID y'umukozi kugira ngo ushakire.");
        translations.put("No employee found with ID:", "Nta mukozi wabonetse afite ID:");
        translations.put("Employee ID must be a number!", "ID y'umukozi igomba kuba imibare!");
        translations.put("You can now edit the fields. Click 'Save Changes' when done.\nNote: Employee ID cannot be changed.", "Ushobora guhindura amakuru. Kanda 'Bika amahinduka' uko wamaze.\nIcyitonderwa: ID y'umukozi ntishobora guhindurwa.");
        translations.put("Hide", "Hisha");
        translations.put("Please log in first.", "Banza winjire.");
        translations.put("Access Denied", "Ntushobora kwinjira");
        translations.put("Confirm Logout", "Emeza gusohoka");
        translations.put("Database connection failed. Please check your database credentials.", "Ntushobora kuvugana na database. Reba amakuru y'ibanze.");
        // Friendly login messages
        translations.put("We couldn't sign you in. Please check your email and password. If you don't have an account, please sign up.",
                 "Ntitwashoboye kukwinjiza. Reba email na ijambobanga byawe. Niba udafite konti, wiyandikishe.");
        translations.put("We couldn't sign you in. Please check your phone number and password.",
                 "Ntitwashoboye kukwinjiza. Reba numero ya terefone na ijambobanga byawe.");
        translations.put("We couldn't sign you in. Please check your National ID and password.",
                 "Ntitwashoboye kukwinjiza. Reba numero y'indangamuntu na ijambobanga byawe.");
        translations.put("Can't reach the server right now. Please ensure the server is running and try again.",
                 "Server ntiboneka ubu. Reba niba server iri gukora hanyuma wongere ugerageze.");
        translations.put("We couldn't sign you in. Your email or password may be incorrect. Use 'Forgot Password' if needed.",
                 "Ntitwashoboye kukwinjiza. Email cyangwa ijambobanga byawe birashobora kuba bitarigukwiriye. Koresha 'Forgot Password' niba ubyibagiwe.");
        translations.put("No account found for that email. Please sign up first or check the email you entered.",
                 "Nta konti ibonetse kuri iyo email. Iyandikishe cyangwa reba email wagiyeho.");
        translations.put("No account found with that identifier. Please sign up first or check your details.",
                 "Nta konti ibonetse kuri iyo nimero/ID. Iyandikishe cyangwa urebe neza amakuru yawe.");
        translations.put("The OTP is invalid or has expired. Request a new OTP and try again.",
                 "OTP ntabwo ari yo cyangwa yashize igihe. Saba OTP nshya hanyuma wongere ugerageze.");
        translations.put("An error occurred during login. Please try again.", "Habaye ikosa mu kwinjira. Ongera ugerageze.");
    }
    
    public static String getTranslation(String englishText) {
        return translations.getOrDefault(englishText, englishText);
    }
    
}
