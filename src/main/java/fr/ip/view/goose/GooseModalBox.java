package fr.ip.view.goose;

import fr.ip.model.goose.GoosePlayer;
import fr.ip.view.core.GameModalBox;
import fr.ip.view.core.components.SettingPanel;

import java.awt.*;

public class GooseModalBox extends GameModalBox<GoosePlayer> {

    public GooseModalBox(Frame frame, String title, boolean modal) {
        super (frame, title, modal);

        form.add.addActionListener(e -> {
            if (out.size() >= SettingPanel.instance.configuration().goose.numberOfPlayers)
                form.add.setEnabled(false);
        });
    }

    @Override
    protected GoosePlayer construct(String name) {
        return new GoosePlayer(name);
    }
}
