import lejos.hardware.Sound;
 
import lejos.utility.Delay;
 
public class t02 {
    public static void main(String[] args) {
        // �s���n
        Sound.twoBeeps();
        // �𮧤T��
        Delay.msDelay(3000);
        // �s�@�n
        Sound.beep();
    }
} 