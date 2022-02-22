package ies.luisvives.peluqueriadamtpv.model;

import ies.luisvives.peluqueriadamtpv.utils.ApplicationProperties;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserConfiguration implements Serializable {
    private static transient UserConfiguration userConfigurationInstance;
    public static final transient String SETTINGS_FILE_NAME = "settings.dat";
    private transient Set<String> availableLanguages;
    private transient Set<String> availableThemes;

    //Datos serializados
    private static final long serialVersionUID = 676523;
    private String actualLanguage;
    private String actualTheme;

    private UserConfiguration() {
        initConfiguration();
    }

    public static void loadData(){
        //Cargar datos de persistencia
        try {
            FileInputStream input = new FileInputStream(UserConfiguration.SETTINGS_FILE_NAME);
            ObjectInputStream inStream = new ObjectInputStream(input);
            userConfigurationInstance = (UserConfiguration) inStream.readObject();
            userConfigurationInstance.initConfiguration();
        } catch (FileNotFoundException e){
            System.out.println("Config not found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initConfiguration() {
        initLanguages();
        initThemes();
    }

    private void initThemes() {
        availableThemes = new HashSet<>();
        String[] themeValues = ApplicationProperties.getInstance().readProperty("theme_list").split(";");
        availableThemes.addAll(Arrays.asList(themeValues));
    }

    /**
     * Asigna los idiomas disponibls según el fichero de settings de la carpeta i18n
     */
    private void initLanguages() {
        availableLanguages = new HashSet<>();

        String[] languageValues = ApplicationProperties.getInstance()
                .readProperty("language_list").split(";");
        availableLanguages.addAll(Arrays.asList(languageValues));
    }

    public String getActualLanguage() {
        if (actualLanguage == null){
            assignDefaultLanguage();
        }
        return actualLanguage;
    }

    private void assignDefaultLanguage() {
        String systemLang = System.getProperty("user.language");
        actualLanguage = null; //Sin lenguaje inicial
        availableLanguages.forEach(e -> {
            String[] sp = e.split("_");
            if (systemLang.equalsIgnoreCase(sp[1])){
                actualLanguage = e;
            }
        });

        //En el caso de que el idioma no esté disponible
        if (actualLanguage == null){
            actualLanguage = "en_UK";
            System.out.println("❗ The detected system language has not been implemented in this application." +
                    "\n English assigned by default.");
        }
    }

    public String getActualTheme() {
        if (actualTheme == null){
            assignDefaultTheme();
        }
        return actualTheme;
    }

    private void assignDefaultTheme() {
        actualTheme = "White"; //Default theme
    }

    public void setActualLanguage(String actualLanguage) {
        this.actualLanguage = actualLanguage;
        saveData();
    }

    public void setActualTheme(String actualTheme) {
        this.actualTheme = actualTheme;
        saveData();
    }

    public static UserConfiguration getInstance() {
        if (userConfigurationInstance == null) {
            userConfigurationInstance = new UserConfiguration();
        }
        return userConfigurationInstance;
    }

    private void saveData(){
        try {
            FileOutputStream output = new FileOutputStream(UserConfiguration.SETTINGS_FILE_NAME);
            ObjectOutputStream outStream = new ObjectOutputStream(output);
            outStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public Set<String> getAvailableThemes() {
        return availableThemes;
    }

}