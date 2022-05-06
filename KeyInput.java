import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyInput implements KeyListener {
    private HashMap<String,Boolean> keysDown = new HashMap<String,Boolean>();

    public boolean isKeyDown(String key){
        return keysDown.getOrDefault(key, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.put(KeyEvent.getKeyText(e.getKeyCode()),true);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown.put(KeyEvent.getKeyText(e.getKeyCode()),false);
        
    }

}
