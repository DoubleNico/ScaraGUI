package me.doublenico.scaraGUI.button;


import java.util.ArrayList;
import java.util.List;

public class ButtonManager {

    private final List<Button> buttons;
    private final List<RoundedButton> roundedButtons;

    public ButtonManager() {
        buttons = new ArrayList<>();
        roundedButtons = new ArrayList<>();
    }

    public void addRoundedButton(RoundedButton button){
        roundedButtons.add(button);
    }

    public RoundedButton getRoundedButton(String name){
        for (RoundedButton button : roundedButtons){
            if (button.getName().equalsIgnoreCase(name))
                return button;
        }
        return null;
    }

    public void addButton(Button button){
        buttons.add(button);
    }

    public Button getButton(String name){
        for (Button button : buttons){
            if (button.getName().equalsIgnoreCase(name))
                return button;
        }
        return null;
    }

    public List<Button> getButtons(){
        return buttons;
    }
}