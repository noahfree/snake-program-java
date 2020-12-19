/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.io.IOException;

/**
 *
 * @author noahf
 */
public interface Navigation {
    public void goToUI(boolean value);
    public void goToAbout();
    public void goToSettings();
    public void goToRecords() throws IOException;
    public void closeWindow();
}
