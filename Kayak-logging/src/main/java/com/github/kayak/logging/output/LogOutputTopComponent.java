/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kayak.logging.output;

import com.github.kayak.core.Bus;
import com.github.kayak.core.Frame;
import com.github.kayak.core.FrameListener;
import com.github.kayak.core.LogFile;
import com.github.kayak.core.Subscription;
import com.github.kayak.logging.input.BusDropTargetAdapter;
import com.github.kayak.logging.options.Options;
import com.github.kayak.ui.ModuleLifecycleManager;
import java.awt.dnd.DropTarget;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbPreferences;

@ConvertAsProperties(dtd = "-//com.github.kayak.ui.logfiles//LogOutput//EN",
autostore = false)
@TopComponent.Description(preferredID = "LogOutputTopComponent",
iconBase="org/freedesktop/tango/16x16/actions/go-next.png",
persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionID(category = "Log files", id = "com.github.kayak.logging.LogOutputTopComponent")
@ActionReference(path = "Menu/Log files", position = 20)
@TopComponent.OpenActionRegistration(displayName = "#CTL_LogOutputAction",
preferredID = "LogOutputTopComponent")
public final class LogOutputTopComponent extends TopComponent implements ExplorerManager.Provider, BusDropTargetAdapter.BusDropReceiver  {

    private static final Logger logger = Logger.getLogger(LogOutputTopComponent.class.getCanonicalName());

    private ExplorerManager manager;
    private BusListModel model = new BusListModel();
    private boolean recording = false;
    private BufferedWriter out;
    private ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();

    private class BusListModel extends AbstractListModel {

        private ArrayList<Bus> busses = new ArrayList<Bus>();

        @Override
        public int getSize() {
            return busses.size();
        }

        @Override
        public Object getElementAt(int index) {
            return busses.get(index);
        }

        public void addBus(Bus bus) {
            busses.add(bus);
            fireIntervalAdded(this, busses.indexOf(bus), busses.indexOf(bus));
        }

        public void removeBus(int i) {
            Bus bus = busses.get(i);
            fireIntervalRemoved(this, i, i);
            busses.remove(bus);
        }
    };

    private FrameListener receiver = new FrameListener() {

        @Override
        public void newFrame(Frame frame) {
            if(recording) {
                try {
                    out.write(frame.toLogFileNotation());
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "could not write frame to file");
                }
            }
        }
    };

    public LogOutputTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(LogOutputTopComponent.class, "CTL_LogOutputTopComponent"));
        setToolTipText(NbBundle.getMessage(LogOutputTopComponent.class, "HINT_LogOutputTopComponent"));
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);

        DropTarget dt = new DropTarget(jList1, new BusDropTargetAdapter(this, 0));
        jList1.setDropTarget(dt);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        FileObject logFolder = FileUtil.toFileObject(new File(Options.getLogFilesFolder()));
        jTextField1.setText(logFolder.getPath() + "/" + sdf.format(cal.getTime()) + ".log.gz");
        jTextField2.setText("NO_PLATFORM");
        jTextField3.setText("No description");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(300, 336));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jLabel2.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jTextField3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jLabel3.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jTextField3.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jLabel4.text")); // NOI18N

        jTextField3.setText(org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jTextField3.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jPanel1.border.title"))); // NOI18N

        jList1.setModel(model);
        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jPanel2.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(LogOutputTopComponent.class, "LogOutputTopComponent.jButton4.text")); // NOI18N
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser chooser;
        File f = new File(jTextField1.getText());
        if(f.exists()) {
            chooser = new JFileChooser();
            chooser.setSelectedFile(f);
        } else {
            String homeFolder = System.getProperty("user.home");
            String logDir = NbPreferences.forModule(ModuleLifecycleManager.class).get("Log file directory", homeFolder + "/kayak/log/");
            chooser = new JFileChooser(logDir);
        }

        if(chooser.showDialog(this, "Choose") == JFileChooser.APPROVE_OPTION) {
            jTextField1.setText(chooser.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        model.removeBus(jList1.getSelectedIndex());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File file = new File(jTextField1.getText());

        String platform = jTextField2.getText();
        String description = jTextField3.getText();

        if(file.exists() || file.isDirectory()) {
            logger.log(Level.WARNING, "Can not open log file");
            return;
        }
        if(!LogFile.platformPattern.matcher(platform).matches()) {
            logger.log(Level.WARNING, "Bad platform name");
            return;
        }
        if(!LogFile.descriptionPattern.matcher(description).matches()) {
            logger.log(Level.WARNING, "Bad description");
            return;
        }

        try {
            OutputStreamWriter osw;
            if(file.getAbsolutePath().endsWith(".gz")) {
                FileOutputStream fos = new FileOutputStream(file);
                GZIPOutputStream zipstream = new GZIPOutputStream(fos);
                osw = new OutputStreamWriter(zipstream);
            } else {
                FileOutputStream fos = new FileOutputStream(file);
                osw = new OutputStreamWriter(fos);
            }
            out = new BufferedWriter(osw);
            out.write("PLATFORM " + platform + "\n");
            out.write("DESCRIPTION \"" + description + "\"\n");

            String[] busses = new String[model.getSize()];
            for(int i=0;i<model.getSize();i++) {
                busses[i] = ((Bus) model.getElementAt(i)).getName();
            }
            for(String name : busses) {
                out.write("DEVICE_ALIAS " + name + " " + name + "\n");
            }

            jList1.setEnabled(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(true);
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            jTextField3.setEnabled(false);
            recording = true;

            for(int i=0;i<model.getSize();i++) {
                Bus bus = ((Bus) model.getElementAt(i));
                Subscription s = new Subscription(receiver, bus);
                s.setSubscribeAll(true);
                subscriptions.add(s);
            }

        } catch (IOException ex) {
            logger.log(Level.WARNING, "could not start logging", ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        recording = false;
        jList1.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(false);
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(true);
        jTextField3.setEnabled(true);

        for(Subscription s : subscriptions) {
            s.Terminate();
        }
        subscriptions.clear();

        try {
            out.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not close log file");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    @Override
    public void receive(Bus b, int number) {
        model.addBus(b);
    }
}
