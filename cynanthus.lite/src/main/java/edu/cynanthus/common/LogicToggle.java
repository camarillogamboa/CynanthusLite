package edu.cynanthus.common;

public final class LogicToggle {

    private boolean toggle;

    public LogicToggle() {
        this.toggle = false;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void doToggle() {
        this.toggle = true;
    }

    public void untoggle() {
        this.toggle = false;
    }

}
