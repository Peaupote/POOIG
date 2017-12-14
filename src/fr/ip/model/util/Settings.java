package fr.ip.model.util;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public final class Settings {
    private ArrayList<SETTING> settings;
    public static Settings instance = null;

    public Settings() {
        if(instance == null) {
            Facade.show(new Message("Creating an instance of Settings.", JOptionPane.INFORMATION_MESSAGE));
            instance = this;
            this.settings = parse(new File("Settings.config"));
            addSettings(defaultSettings());
        } else {
            Facade.show(new Message("Settings already has an instance.", JOptionPane.INFORMATION_MESSAGE));
            settings = getInstanceSettings();
            return;
        }
        System.out.println(toString(true));
    }

    private ArrayList<SETTING> parse(File f) {
        Facade.show(new Message("Settings.parse(File f) called.", JOptionPane.INFORMATION_MESSAGE));

        Scanner sc;

        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            Facade.show(new Message("No Settings.config file found - default settings applied.", JOptionPane.INFORMATION_MESSAGE));
            return defaultSettings();
        }

        ArrayList<SETTING> settings = new ArrayList<>();
        Scanner line;
        String sLine;
        SETTING setting;

        while(sc.hasNextLine()) {
            sLine = sc.nextLine();
            if(StringUtil.removeWhiteSpace(sLine).startsWith("#"))
                continue; // Checks if comment line.

            line = new Scanner(sLine);
            line.useDelimiter("=");

            setting = settingFromString(
                    StringUtil.removeWhiteSpace(line.next()),
                    StringUtil.removeWhiteSpace(line.next())
            );

            if(setting != null)
                settings.add(setting);
        }

        return settings;
    }

    private SETTING settingFromString(String name, String value) {
        if(name == null || value == null)
            return null;

        SETTING out;

        if(value.equals("true") || value.equals("false"))
            out = new SETTING<>(name, value.equals("true"));
        else if(StringUtil.isInteger(value))
            out = new SETTING<>(name, Integer.parseInt(value));
        else
            out = new SETTING<>(name, value);

        return out;
    }

    public SETTING getSetting(String name) {
        for(SETTING s : getInstanceSettings()) {
            if(s.name.equals(name))
                return s;
        }
        return null;
    }

    public boolean isSetting(String name) {
        if(name == null) return false;
        for(SETTING s : getInstanceSettings())
            if(s.name.equals(name))
                return true;
        return false;
    }

    public boolean isSetting(SETTING setting) {
        if(setting == null) return false;
        for(SETTING s : getInstanceSettings())
            if(s.equals(setting))
                return true;
        return false;
    }

    public ArrayList<SETTING> getInstanceSettings() {
        return instance.getSettings();
    }

    public ArrayList<SETTING> getSettings() {
        return settings;
    }

    public boolean write(String pathToFile) {
        Facade.show(new Message("Settings.write() called.", JOptionPane.INFORMATION_MESSAGE));

        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(pathToFile);
        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            for(SETTING s : getInstanceSettings()) {
                writer.write(s.toString(), 0, s.toString().length());
                writer.newLine();
            }
        } catch (IOException e) {
            Facade.show(new Message("Error while writing " + pathToFile, JOptionPane.ERROR_MESSAGE));
            return false;
        }

        return true;
    }

    public boolean write() {
        return write("Settings.config");
    }

    @Override
    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean verbose) {
        String str = "List of Settings : \n";

        for(SETTING s : getInstanceSettings()) {
            str += s.toString(verbose) + "\n";
        }

        return str;
    }

    private ArrayList<SETTING> defaultSettings() {

        // ADD ALL DEFAULT Settings HERE.

        ArrayList<SETTING> s = new ArrayList<>();
        s.add(new SETTING<>("PATH_TO_QUESTION_FOLDER", "data/questions"));
        s.add(new SETTING<>("ANY_LEVEL", false));
        s.add(new SETTING<>("VERBOSE", false));
        return s;
    }

    private boolean removeSetting(SETTING s) {
        if(isSetting(s)) {
            getInstanceSettings().remove(s);
            return true;
        }
        return false;
    }

    private boolean removeSetting(String name) {
        if(isSetting(name)) {
            return removeSetting(getSetting(name));
        }
        return false;
    }

    private boolean addSetting(SETTING s, boolean force, boolean sameTypeCheck) {
        if(!isSetting(s)) {
            getInstanceSettings().add(s);
            return true;
        }
        if(force || (sameTypeCheck && !getSetting(s.name).isSameType(s))) {
            removeSetting(s.name);
            addSetting(s);
            return true;
        }
        return false;
    }

    private boolean addSetting(SETTING s, boolean force) {
         return addSetting(s, force,false);
    }

    private boolean addSetting(SETTING s) {
        return addSetting(s, false,false);
    }

    private int addSettings(ArrayList<SETTING> ss, boolean force, boolean sameTypeCheck) {
        int out = 0;
        for(SETTING s : ss) {
            if(addSetting(s, force, sameTypeCheck))
                out ++;
        }
        Facade.show(new Message(out + " SETTING(s) were added.", JOptionPane.INFORMATION_MESSAGE));
        return out;
    }

    private int addSettings(ArrayList<SETTING> ss) {
        return addSettings(ss, false, false);
    }

    public final class SETTING<T> {
        public final String name;
        private T value;

        public SETTING(String name, T setting) {
            this.name = name;
            this.value = setting;
        }

        public T value() {
            return value;
        }
        public String name() {
            return name;
        }
        public void setvalue(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return name + "=" + value.toString();
        }

        public boolean equals(SETTING setting) {
            return equals(setting, false);
        }

        public boolean equals(SETTING setting, boolean checkValue) {
            if(!name.equals(setting.name)) return false;
            if(!checkValue) return true;
            if(value instanceof Integer && setting.value() instanceof Integer && value == setting.value())
                return true;
            else if(value instanceof Boolean && setting.value() instanceof Boolean && value == setting.value())
                return true;
            else if(value instanceof String && setting.value() instanceof String && value.equals(setting.value()))
                return true;
            else {
                Facade.show(new Message("The setting " + name + " or " + setting.name + " isn't of a valid type.", JOptionPane.INFORMATION_MESSAGE));
                return false;
            }
        }

        public boolean isSameType(SETTING setting) {
            if(    value instanceof Integer && setting.value() instanceof Integer
                || value instanceof Boolean && setting.value() instanceof Boolean
                || value instanceof String && setting.value() instanceof String)
                return true;
            else {
                return false;
            }
        }

        public String toString(boolean verbose) {
            if(!verbose) {
                return toString();
            } else {
                String str = toString();

                if(value instanceof Boolean) {
                    str += " (bool)";
                } else if (value instanceof Integer) {
                    str += " (int)";
                } else {
                    str += " (char[])";
                }

                return str;
            }
        }
    }
}
