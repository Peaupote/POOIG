package fr.ip.model.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class SETTINGS {
    public final ArrayList<SETTING> settings;
    public static SETTINGS instance = null;

    public SETTINGS() {
        if(instance != null) {
            settings = instance.settings;
            return;
        }

        File config = new File("SETTINGS.config");

        this.settings = parse(config);
    }

    private ArrayList<SETTING> parse(File f) {
        Scanner sc;

        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            new Message(MessageType.WARNING, "No SETTINGS.config file found - default settings applied.").print();
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
        for(SETTING s : this.settings) {
            if(s.name.equals(name))
                return s;
        }
        return null;
    }

    public boolean isSetting(String name) {
        for(SETTING s : this.settings) {
            if(s.name.equals(name))
                return true;
        }
        return false;
    }

    private ArrayList<SETTING> defaultSettings() {
        ArrayList<SETTING> s = new ArrayList<>();
        s.add(new SETTING<>("PATH_TO_QUESTION_FOLDER", "data/questions"));
        s.add(new SETTING<>("ANY_LEVEL", false));
        return s;
    }

    public final class SETTING<T> {
        public final String name;
        public final T value;

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
    }
}
