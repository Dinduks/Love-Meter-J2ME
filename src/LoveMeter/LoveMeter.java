/**
 * @author Samy DINDANE
 *
 * Change picture.jpg by a picture of your girl and you ;)
 *
 */

package LoveMeter;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class LoveMeter extends MIDlet implements CommandListener {

    private Command exitCommand = new Command("Quitter", Command.EXIT, 0);
    private Command okCommand = new Command("Ok", Command.OK, 0);
    private Command backCommand = new Command("Retour", Command.BACK, 2);
    private Command bonusCommand = new Command("Bonus", Command.OK, 0);

    private Form mainForm;
    private Form results;
    private Form bonus;

    private String instructions = "Bienvenue sur Love Meter! \n\n";
    private Image logo;
    private Image usPicture;

    private String himValue;
    private String herValue;
    private String descResults;

    private int pourcentage;

    private TextField himField = new TextField("Lui", null, 30, TextField.ANY);
    private TextField herField = new TextField("Elle", null, 30, TextField.ANY);


    public Form showMainForm() {
        if (mainForm == null) {
            mainForm = new Form("Love Meter");
            mainForm.append(instructions);
            mainForm.append(himField);
            mainForm.append(herField);
            mainForm.addCommand(exitCommand);
            mainForm.addCommand(okCommand);
            mainForm.setCommandListener(this);
        }
        return mainForm;
    }

    public String getPResults(String himValue, String herValue) {
        descResults = "Le pourcentage d'amour entre ";
        descResults += himValue.substring(0,1).toUpperCase() + himValue.substring(1);
        descResults += " et ";
        descResults += herValue.substring(0,1).toUpperCase() + herValue.substring(1);
        descResults += " est: \n";
        return descResults;
    }

    public int getPourcentage(String himValue, String herValue) {
        // Here you can add special values depending of the "lovers" name ;)
        if ( ( himValue.equals("samy") || himValue.equals("dinduks") ) && (herValue.equals("nora") ) )
            pourcentage = 666;
        else
            pourcentage = ( himValue.hashCode() + herValue.hashCode() ) % 100;

        if ( pourcentage < 0 )
            pourcentage *= -1;

        return pourcentage;
    }

    public Form showResults() {
        himValue = himField.getString().toLowerCase();
        herValue = herField.getString().toLowerCase();

        if (results == null) {
            results = new Form("Résultats");

            results.append( getPResults(himValue, herValue) );

            results.addCommand(exitCommand);
            results.addCommand(backCommand);
            results.setCommandListener(this);
        }

        results.append(getPourcentage(himValue, herValue) + "%");

        // Here you can add special text/screen depending
        // of the percentage (and indirectly the lovers name) ;)
        if ( pourcentage == 666 ) {
            results.append("!");
            results.append(getPicture());
            results.append("\nI love you! <3 \n");
            //results.addCommand(bonusCommand);
        }

        return results;
    }


    // Un écran bonus au cas où
    public Form showBonus() {
        if (bonus == null) {
            bonus = new Form("Bonus");
            bonus.append("I love you! <3");
            bonus.append(getPicture());
            bonus.addCommand(exitCommand);
            bonus.addCommand(backCommand);
            bonus.setCommandListener(this);
        }
        return bonus;
    }

    public void commandAction(Command command, Displayable displayable) {
        if (displayable == mainForm) {
            if (command == exitCommand)
                exitApp();
            else if (command == okCommand)
                if( !himField.getString().equals("") && !herField.getString().equals("") )
                    switchDisplayable(null, showResults());
        } else if (displayable == results) {
            if (command == exitCommand)
                exitApp();
            else if (command == backCommand) {
                switchDisplayable(null, showMainForm());
                results = null;
            } else if (command == bonusCommand)
                switchDisplayable(null, showBonus());
        } else if ( displayable == bonus ) {
            if (command == exitCommand)
                exitApp();
            else if (command == backCommand) {
                switchDisplayable(null, showMainForm());
                results = null;
            }
        }
    }

    public void startApp() {
        switchDisplayable(null, showMainForm());
    }

    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        Display display = getDisplay();
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
    }

    public void exitApp() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public Image getPicture() {
        if(usPicture == null) {
            try {
                usPicture = Image.createImage("/LoveMeter/picture.jpg");
            } catch (java.io.IOException e) {
            }
        }
        return usPicture;
    }

    /*public Image getLogo() {
        if(logo == null) {
            try {
                logo = Image.createImage("/splash.jpg");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return logo;
    }*/

}
