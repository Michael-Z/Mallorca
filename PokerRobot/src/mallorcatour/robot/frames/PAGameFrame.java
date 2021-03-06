/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MFrame.java
 *
 * Created on 28.09.2012, 15:53:07
 */
package mallorcatour.robot.frames;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import mallorcatour.core.game.LimitType;
import mallorcatour.robot.hardwaremanager.KeyboardHookManager;
import mallorcatour.robot.pa.AdviceWritingPARobotListener;
import mallorcatour.robot.pa.ContinuedPARobotListener;
import mallorcatour.robot.pa.PAGameRobot;
import mallorcatour.robot.pa.PAGameRobot.GameType;
import br.com.wagnerpaz.javahook.NativeKeyboardEvent;
import br.com.wagnerpaz.javahook.NativeKeyboardListener;

/**
 *
 * @author Andrew
 */
public class PAGameFrame extends javax.swing.JFrame {

    private PAGameRobot robot;

    /** Creates new form MFrame */
    public PAGameFrame() {
        initComponents();
        KeyboardHookManager.addListener(new NativeKeyboardListener() {

            public boolean keyPressed(NativeKeyboardEvent nke) {
                if (nke.getKeyCode() == KeyEvent.VK_F9) {
                    robot.startGame();
                    return false;
                } else if (nke.getKeyCode() == KeyEvent.VK_F10) {
                    robot.pauseGame();
                    return false;
                } else if (nke.getKeyCode() == KeyEvent.VK_F11) {
                    robot.resumeGame();
                    return false;
                }
                return true;
            }

            public boolean keyReleased(NativeKeyboardEvent nke) {
                if (nke.getKeyCode() == KeyEvent.VK_F9) {
                    return false;
                } else if (nke.getKeyCode() == KeyEvent.VK_F10) {
                    return false;
                } else if (nke.getKeyCode() == KeyEvent.VK_F11) {
                    return false;
                }
                return true;
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        createRobotButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton4.setText("Start game");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Pause game");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Resume game");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setText("Play many games");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        createRobotButton.setText("Create robot...");
        createRobotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRobotButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .add(createRobotButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton6))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(createRobotButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 30, Short.MAX_VALUE)
                .add(jButton4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton8)
                .add(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        robot.startGame();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        robot.pauseGame();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        robot.resumeGame();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        playGames(20000);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void createRobotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRobotButtonActionPerformed
        PopupMenu popupMenu = new PopupMenu();
        MenuItem noLimitItem = new MenuItem("No limit sng bot");
        noLimitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                robot = new PAGameRobot(LimitType.NO_LIMIT, PAGameRobot.GameType.SNG);
                robot.setListener(new AdviceWritingPARobotListener());
            }
        });
        MenuItem fixedLimitItem = new MenuItem("Fixed limit cash bot");
        fixedLimitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                robot = new PAGameRobot(LimitType.FIXED_LIMIT, PAGameRobot.GameType.CASH);
                robot.setListener(new AdviceWritingPARobotListener());
            }
        });

        popupMenu.add(fixedLimitItem);
        popupMenu.add(noLimitItem);
        add(popupMenu);
        popupMenu.show(this, createRobotButton.getX(), createRobotButton.getY());
    }//GEN-LAST:event_createRobotButtonActionPerformed

    private void playGames(int count) {
        robot.setListener(new ContinuedPARobotListener(robot, count));
        robot.startGame();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PAGameFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createRobotButton;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    // End of variables declaration//GEN-END:variables
}
