/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package countdownm2w;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author ruobo
 * @date Nov 27, 2011
 */
public class MinsCD implements ActionListener{

//    private String MinsCD_Mode = "";
//    private String MinsCD_mins = "";
    
    private javax.swing.Timer MinsCD_Timer;
    javax.swing.JProgressBar MinsCD_ProgressBar;
    javax.swing.JProgressBar Summary_MinsCD_ProgressBar;

    private long MinsCD_now = 0;
    private long MinsCD_elapsed = 0;
    private long MinsCD_remaining = 0;
    private long MinsCD_lastUpdate = 0;
    private long MinsCD_BreakMins = 0;
    private long MinsCD_mins = 0;
    private boolean isAuto = false;
    private int MinsCD_bORs = 0;
    private boolean isBreakTime = false;

    private NumberFormat format; // Format minutes:seconds with leading zeros
    private javax.swing.JLabel MinsCD_CounterLable;
    private javax.swing.JLabel Summary_MinsCD_CounterLable;

    public MinsCD(javax.swing.JLabel sum_CDlable, javax.swing.JLabel CDlable, String MinsCDinput, String BreakMins, javax.swing.JProgressBar ProgressBar, javax.swing.JProgressBar sum_ProgressBar){
        MinsCD_CounterLable = CDlable;
        Summary_MinsCD_CounterLable = sum_CDlable;
        MinsCD_mins = Long.parseLong(MinsCDinput) * 60000;
        MinsCD_remaining = Long.parseLong(MinsCDinput) * 60000;
        MinsCD_BreakMins = Long.parseLong(BreakMins) * 60000;
        // Obtain a NumberFormat object to convert number of minutes and
        // seconds to strings. Set it up to produce a leading 0 if necessary
        format = NumberFormat.getNumberInstance();
        format.setMinimumIntegerDigits(2); // pad with 0 if necessary\
        MinsCD_Timer = new Timer(1000, this);
        MinsCD_Timer.setInitialDelay(0);
        MinsCD_ProgressBar = ProgressBar;
        Summary_MinsCD_ProgressBar = sum_ProgressBar;
    }
    
    public void startCDauto(){
        isAuto = true;
        setMinsCD_lastUpdate(System.currentTimeMillis());
        MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));
        Summary_MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));

        getMinsCD_Timer().start();
    }

    public void startCDManual(){
        setMinsCD_lastUpdate(System.currentTimeMillis());
        MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));
        Summary_MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));
        
        getMinsCD_Timer().start();
    }

    public Timer resetCD(){
        getMinsCD_Timer().stop();
        setMinsCD_now(0);
        setMinsCD_elapsed(0);
        setMinsCD_remaining(0);
        setMinsCD_lastUpdate(0);
        getMinsCD_CounterLable().setText("00:00:00");
        return getMinsCD_Timer();
    }

    //resume the countdown
    public void resumeCD() {
    // Restore the time we're counting down from and restart the timer.
        setMinsCD_lastUpdate(System.currentTimeMillis());
        getMinsCD_Timer().start(); // Start the timer
}
    public void pauseCD() {
    // Subtract elapsed time from the remaining time and stop timing
        long MinsCD_now = System.currentTimeMillis();
        setMinsCD_remaining(getMinsCD_remaining() - (MinsCD_now - getMinsCD_lastUpdate()));
        getMinsCD_Timer().stop(); // Stop the timer
}

    @Override
    public void actionPerformed(ActionEvent e){
        this.updateDisplay();
    }

        private void updateDisplay(){
        setMinsCD_now(System.currentTimeMillis()); // current time in ms
        setMinsCD_elapsed(getMinsCD_now() - getMinsCD_lastUpdate()); // ms elapsed since last update
        setMinsCD_remaining(getMinsCD_remaining() - getMinsCD_elapsed()); // adjust remaining time
        setMinsCD_lastUpdate(getMinsCD_now()); // remember this update time


        // Convert remaining milliseconds to mm:ss format and display
        if (getMinsCD_remaining() < 0) setMinsCD_remaining(0);
        int hours = (int)(getMinsCD_remaining()/3600000);
        int minutes = (int)((getMinsCD_remaining() - (hours * 3600000))/60000);
        int seconds = (int)((getMinsCD_remaining() - (hours * 3600000) - (minutes * 60000))/1000);
        getMinsCD_CounterLable().setText(getFormat().format(hours) + ":" + getFormat().format(minutes) + ":" + getFormat().format(seconds));
        Summary_MinsCD_CounterLable.setText(MinsCD_CounterLable.getText());
        // If we've completed the countdown beep and display new page

        MinsCD_ProgressBar.setValue((int)(MinsCD_remaining / 1000));
        Summary_MinsCD_ProgressBar.setValue((int)(MinsCD_remaining / 1000));
        
        if (getMinsCD_remaining() == 0) {
        // Stop updating now.
            getMinsCD_Timer().stop();
            if(isAuto){
                JOptionPane.showMessageDialog(null, "Time is up", "Note:", 0);
                MinsCD_bORs++;
                if((MinsCD_bORs % 2) == 1)   isBreakTime = true;
                if(isBreakTime){
                    MinsCD_remaining = MinsCD_BreakMins;
                    MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));
                    Summary_MinsCD_ProgressBar.setValue((int)(MinsCD_remaining / 1000));

                    MinsCD_Timer.start();
                }else{
                    MinsCD_remaining = MinsCD_mins;
                    MinsCD_ProgressBar.setMaximum((int)(MinsCD_remaining / 1000));
                    Summary_MinsCD_ProgressBar.setValue((int)(MinsCD_remaining / 1000));

                    MinsCD_Timer.start();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Time is up", "Note:", 0);
             }
        }
    }



//       ================================ setters & getters ====================================
    /**
     * @return the MinsCD_Timer
     */
    public javax.swing.Timer getMinsCD_Timer() {
        return MinsCD_Timer;
    }

    /**
     * @param MinsCD_Timer the MinsCD_Timer to set
     */
    public void setMinsCD_Timer(javax.swing.Timer MinsCD_Timer) {
        this.MinsCD_Timer = MinsCD_Timer;
    }

    /**
     * @return the MinsCD_now
     */
    public long getMinsCD_now() {
        return MinsCD_now;
    }

    /**
     * @param MinsCD_now the MinsCD_now to set
     */
    public void setMinsCD_now(long MinsCD_now) {
        this.MinsCD_now = MinsCD_now;
    }

    /**
     * @return the MinsCD_elapsed
     */
    public long getMinsCD_elapsed() {
        return MinsCD_elapsed;
    }

    /**
     * @param MinsCD_elapsed the MinsCD_elapsed to set
     */
    public void setMinsCD_elapsed(long MinsCD_elapsed) {
        this.MinsCD_elapsed = MinsCD_elapsed;
    }

    /**
     * @return the MinsCD_remaining
     */
    public long getMinsCD_remaining() {
        return MinsCD_remaining;
    }

    /**
     * @param MinsCD_remaining the MinsCD_remaining to set
     */
    public void setMinsCD_remaining(long MinsCD_remaining) {
        this.MinsCD_remaining = MinsCD_remaining;
    }

    /**
     * @return the MinsCD_lastUpdate
     */
    public long getMinsCD_lastUpdate() {
        return MinsCD_lastUpdate;
    }

    /**
     * @param MinsCD_lastUpdate the MinsCD_lastUpdate to set
     */
    public void setMinsCD_lastUpdate(long MinsCD_lastUpdate) {
        this.MinsCD_lastUpdate = MinsCD_lastUpdate;
    }

    /**
     * @return the format
     */
    public NumberFormat getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(NumberFormat format) {
        this.format = format;
    }

    /**
     * @return the MinsCD_CounterLable
     */
    public javax.swing.JLabel getMinsCD_CounterLable() {
        return MinsCD_CounterLable;
    }

    /**
     * @param MinsCD_CounterLable the MinsCD_CounterLable to set
     */
    public void setMinsCD_CounterLable(javax.swing.JLabel MinsCD_CounterLable) {
        this.MinsCD_CounterLable = MinsCD_CounterLable;
    }

    /**
     * @return the MinsCD_BreakMins
     */
    public long getMinsCD_BreakMins() {
        return MinsCD_BreakMins;
    }

    /**
     * @param MinsCD_BreakMins the MinsCD_BreakMins to set
     */
    public void setMinsCD_BreakMins(long MinsCD_BreakMins) {
        this.MinsCD_BreakMins = MinsCD_BreakMins;
    }

    /**
     * @return the MinsCD_mins
     */
    public long getMinsCD_mins() {
        return MinsCD_mins;
    }

    /**
     * @param MinsCD_mins the MinsCD_mins to set
     */
    public void setMinsCD_mins(long MinsCD_mins) {
        this.MinsCD_mins = MinsCD_mins;
    }



}
