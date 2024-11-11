package me.doublenico.scaraGUI.button;


import java.util.ArrayList;
import java.util.List;

public class ButtonManager {

    private final List<Button> buttons;

    public ButtonManager() {
        buttons = new ArrayList<>();
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