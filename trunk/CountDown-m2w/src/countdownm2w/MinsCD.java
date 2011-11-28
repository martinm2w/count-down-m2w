/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package countdownm2w;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.Timer;

/**
 *
 * @author ruobo
 * @date Nov 27, 2011
 */
public class MinsCD implements ActionListener{

//    private String MinsCD_Mode = "";
    private String MinsCD_mins = "";
    private javax.swing.Timer MinsCD_Timer;
    private long MinsCD_now = 0;
    private long MinsCD_elapsed = 0;
    private long MinsCD_remaining = 0;
    private long MinsCD_lastUpdate = 0;
    private NumberFormat format; // Format minutes:seconds with leading zeros
    private javax.swing.JLabel MinsCD_CounterLable;

    public MinsCD(javax.swing.JLabel CDlable, String MinsCDinput){
        MinsCD_CounterLable = CDlable;
        MinsCD_remaining = Long.parseLong(MinsCDinput) * 60000;

        // Obtain a NumberFormat object to convert number of minutes and
        // seconds to strings. Set it up to produce a leading 0 if necessary
        format = NumberFormat.getNumberInstance();
        format.setMinimumIntegerDigits(2); // pad with 0 if necessary\
        MinsCD_Timer = new Timer(1000, this);
        MinsCD_Timer.setInitialDelay(0);
    }
    
    public void startCDauto(){
        MinsCD_lastUpdate = System.currentTimeMillis();
        MinsCD_Timer.start();
    }

    public void stopCD(){
        MinsCD_Timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        this.updateDisplay();
    }

        private void updateDisplay(){
        MinsCD_now = System.currentTimeMillis(); // current time in ms
        MinsCD_elapsed = MinsCD_now - MinsCD_lastUpdate; // ms elapsed since last update
        MinsCD_remaining -= MinsCD_elapsed; // adjust remaining time
        MinsCD_lastUpdate = MinsCD_now; // remember this update time


        // Convert remaining milliseconds to mm:ss format and display
        if (MinsCD_remaining < 0) MinsCD_remaining = 0;
        int hours = (int)(MinsCD_remaining/3600000);
        int minutes = (int)((MinsCD_remaining - (hours * 3600000))/60000);
        int seconds = (int)((MinsCD_remaining - (hours * 3600000) - (minutes * 60000))/1000);
        MinsCD_CounterLable.setText(format.format(hours) + ":" + format.format(minutes) + ":" + format.format(seconds));
        // If we've completed the countdown beep and display new page

        if (MinsCD_remaining == 0) {
        // Stop updating now.
            MinsCD_Timer.stop();

        }
    }



}
