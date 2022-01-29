package com.example.pierrefeuilleciseaux;

public class TextSwitch {
    public String getText() {
        return texte;
    }

    public void setText(String texte) {
        this.texte = texte;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private String texte;
    private boolean isSelected;
}