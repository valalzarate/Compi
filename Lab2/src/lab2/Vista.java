/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jbo
 */
public class Vista extends javax.swing.JFrame {

    /**
     * Creates new form Vista
     */
    String Ruta;
    static ArrayList<String> cabezote = new ArrayList<String>();
    static ArrayList<String> cabezote2 = new ArrayList<String>();
    ArrayList<String> cuerpo = new ArrayList<String>();
    static ArrayList<String> Siguiente = new ArrayList<String>();
    DefaultTableModel pk;
    DefaultTableModel pk1;
    HashMap<String, Set<String>> producciones = new HashMap<String, Set<String>>();
    HashMap<String, Set<String>> primero = new HashMap<String, Set<String>>();
    HashMap<String, Set<String>> siguiente = new HashMap<String, Set<String>>();

    public Vista() {
        initComponents();
    }

    public static boolean esMinuscula(String s) {
        // Regresa el resultado de comparar la original con sun versión minúscula
        return s.equals(s.toLowerCase());
    }

    public static boolean Recursividad(String cabezote, String[] cuerpo) {
        for (int i = 0; i < cuerpo.length; i++) {
            if (cuerpo[i].substring(0, 1).equals(cabezote)) {
                return true;
            }
        }
        return false;

    }

    private boolean sonIguales(HashMap<String, Set<String>> first, HashMap<String, Set<String>> newfirst) {
        Set<String> keys = this.primero.keySet();
        for (String key : keys) {
            System.out.println(this.primero.get(key) + " " + newfirst.get(key));
            if (!this.primero.get(key).equals(newfirst.get(key))) {
                return false;
            }
        }
        return true;
    }

    private boolean tieneEpsilon(Set<String> primeroPro) {
        return primeroPro.contains("&");
    }

    private void ComputeFirst() {
        Set<String> keys = producciones.keySet();
        for (String key : keys) {
            for (String produc : producciones.get(key)) {
                if (esMinuscula(produc.substring(0, 1))) {
                    primero.get(key).add(produc.charAt(0) + "");
                }
            }
        }
        HashMap<String, Set<String>> newfirst = new HashMap<String, Set<String>>();
        int invariable = 0;
        do {
            for (String key : keys) {
                newfirst = (HashMap<String, Set<String>>) primero.clone();
                for (String produc : producciones.get(key)) {
                    Set<String> temporalSet = new LinkedHashSet<String>();
                    if (!esMinuscula(produc.substring(0, 1))) {
                        int i = 0;
                        while (i < produc.length() && (!esMinuscula(produc.substring(i, i + 1)))) {
                            Set<String> primeroPro = newfirst.get(produc.charAt(i) + "");
                            temporalSet.addAll(primeroPro);
                            if (!primeroPro.contains("&")) {
                                break;
                            }
                            i++;
                        }
                        if (i < produc.length()) {
                            temporalSet.remove("&");
                            if (esMinuscula(produc.substring(i, i + 1))) {
                                temporalSet.add(produc.charAt(i) + "");
                            }
                        }
                    }
                    newfirst.get(key).addAll(temporalSet);
//                    combine(newfirst.get(key), temporalSet);
                }
            }
            if (!sonIguales(primero, newfirst)) {
                invariable = 0;
            } else {
                invariable++;
            }
        } while (invariable < 3);
        primero = newfirst;
    }

    private void computeFollow() {
        // Add $ to FOLLOW(S), where S is the start symbol
        siguiente.get(cabezote.get(0)).add("$");
        regla2();
        regla3();
    }

    private void regla3() {
        Set<String> prokeys = producciones.keySet();
        for (String prokey : prokeys) {
            for (String production : producciones.get(prokey)) {
                int i = production.length() - 1;
                while (i >= 0) {
                    if (!esMinuscula(production.substring(i, i + 1))) {
                        siguiente.get(production.substring(i, i + 1)).addAll(siguiente.get(prokey));
                        if (tieneEpsilon(primero.get(production.substring(i,i+1)))) {
                            i--;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void regla2() {
        Set<String> prokeys = producciones.keySet();
        for (String prokey : prokeys) {
            for (String production : producciones.get(prokey)) {

                int i = 0;
                while (i < production.length() - 1) {
                    if (!esMinuscula(production.substring(i, i + 1))) {
                        int j = i + 1;
                        while (j < production.length()) {
                            Set<String> temp = new LinkedHashSet<String>();
                            if (!esMinuscula(production.substring(j, j + 1))) {
                                temp.addAll(primero.get(production.charAt(j) + ""));
                                if (temp.contains("&")) {
                                    temp.remove("&");
                                    siguiente.get(production.substring(i, i + 1)).addAll(temp);
                                    j++;
                                } else {
                                    siguiente.get(production.substring(i, i + 1)).addAll(temp);
                                    break;
                                }
                            } else {
                                temp.add(production.charAt(j) + "");
                                siguiente.get(production.substring(i, i + 1)).addAll(temp);
                                break;
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JRuta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPrimero = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaSiguiente = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Leer archivo:");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setFocusable(false);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Gramatica sin recursividad y/o factorizacion:");

        jLabel3.setText("Primero:");

        TablaPrimero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TablaPrimero);

        TablaSiguiente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(TablaSiguiente);

        jLabel4.setText("Siguiente: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(232, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap(158, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String asignarNuevoNTerminal() {
        for (char i = 'A'; i <= 'Z'; i++) {
            String noTerminal = Character.toString(i);
            if (!cabezote.contains(noTerminal)) {
                return noTerminal;
            }
        }
        return "A";
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChosser = new JFileChooser();
        int option = fileChosser.showOpenDialog(this);
        int h;

        // aqui busco el archivo
        if (option == JFileChooser.APPROVE_OPTION) {
            Ruta = fileChosser.getSelectedFile().toString();
            System.out.println("File ruta: " + fileChosser.getSelectedFile().getName() + "File name:" + fileChosser.getSelectedFile().toString());
            JRuta.setText(Ruta);
            File archivo = new File(Ruta);
            FileReader fr;
            BufferedReader br;

            // leo el archivo
            try {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                String[] linea2;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                    linea2 = linea.split("->");
                    System.out.println("cabezote " + linea2[0] + " cuerpo " + linea2[1]);

                    // los leo organizandoles de forma que quede una solo particion para cada nodo no terminal
                    if (cabezote.contains(linea2[0])) {
                        for (int i = 0; i < cabezote.size(); i++) {
                            if (cabezote.get(i).equals(linea2[0])) {
                                cuerpo.set(i, cuerpo.get(i) + " " + linea2[1]);
                                break;
                            }
                        }
                    } else {
                        cabezote.add(linea2[0]);
                        cuerpo.add(linea2[1]);
                    }

                }
                h = cabezote.size();
                System.out.println(h);

                // recorro todas las producciones para eliminar una posible recursividad a la izquierda y una factorizacion
                for (int i = 0; i < h; i++) {
                    //System.out.println("Cabezote: " +cabezote.get(i) + "  Cuerpo: " +cuerpo.get(i));
                    System.out.println(cuerpo.get(i));
//                        System.out.println(prueba);
                    linea2 = cuerpo.get(i).split(" ");
//                        System.out.println(linea2.length);
//                        for (String string : linea2) {
//                            System.out.println(string + " una parte");
//                        }

                    // Si tiene recursividad se la quito en este if
                    if (Recursividad(cabezote.get(i), linea2)) {
                        String primo = asignarNuevoNTerminal();
                        cabezote.add(primo);
                        String nueva = "";
                        String vieja = "";
                        for (int j = 0; j < linea2.length; j++) {
                            if (linea2[j].contains(cabezote.get(i))) {
                                linea2[j] = linea2[j].replace(cabezote.get(i), "");
                                System.out.println(linea2[j] + " linea que voy a cambiar");
                                nueva = nueva + linea2[j] + primo + " ";
                            } else {
                                vieja = vieja + linea2[j] + primo + " ";
                            }
                        }
                        vieja = vieja.replace("&", "");
                        cuerpo.add(nueva + "&");
                        cuerpo.set(i, vieja.substring(0, vieja.length() - 1));
                    }

                    // Factorizacion 
                    String antiguo = "", nuevo2 = "";
                    boolean estault = true, hayfact = false;
                    String primo = asignarNuevoNTerminal();
                    for (int j = 0; j < linea2.length - 1; j++) {
                        //for (int k = 0; k < linea2[j].length(); k++) {{
                        boolean esta = false;
                        for (int k = linea2[j].length() - 1; k > 0; k--) {
                            for (int l = j + 1; l < linea2.length; l++) {
                                if (linea2[l].length() >= k) {
                                    if (linea2[l].substring(0, k).equals(linea2[j].substring(0, k)) && l == linea2.length - 1) {
                                        if (!hayfact) {
                                            if (linea2[j].substring(k).equals("")) {
                                                nuevo2 = nuevo2 + "& ";
                                            } else {
                                                nuevo2 = nuevo2 + linea2[j].substring(k) + " ";
                                            }
                                        }
                                        if (linea2[l].substring(k).equals("")) {
                                            nuevo2 = nuevo2 + "& ";
                                        } else {
                                            nuevo2 = nuevo2 + linea2[l].substring(k) + " ";
                                        }
                                        esta = true;
                                        estault = false;
                                        hayfact = true;
                                        linea2[l] = "";
                                    } else if (linea2[l].substring(0, k).equals(linea2[j].substring(0, k))) {
                                        if (!hayfact) {
                                            if (linea2[j].substring(k).equals("")) {
                                                nuevo2 = nuevo2 + "& ";
                                            } else {
                                                nuevo2 = nuevo2 + linea2[j].substring(k) + " ";
                                            }
                                        }
                                        if (linea2[l].substring(k).equals("")) {
                                            nuevo2 = nuevo2 + "& ";
                                        } else {
                                            nuevo2 = nuevo2 + linea2[l].substring(k) + " ";
                                        }
                                        esta = true;
                                        hayfact = true;
                                        linea2[l] = "";
                                    }

                                }
                            }
                            if (esta) {

                                antiguo = antiguo + linea2[j].substring(0, k) + primo + " ";
                                break;
                            }
                        }
                        if (!esta && !linea2[j].equals("")) {
                            antiguo = antiguo + linea2[j] + " ";
                        }
                    }
                    if (hayfact) {
                        if (estault) {
                            System.out.println("si");
                            antiguo = antiguo + linea2[linea2.length - 1] + " ";
                        }
                        cuerpo.set(i, antiguo.substring(0, antiguo.length() - 1));
                        cabezote.add(primo);
                        cuerpo.add(nuevo2.substring(0, nuevo2.length() - 1));
                    }

                }

                // imprimo a ver como queda
                for (int i = 0; i < cabezote.size(); i++) {
                    System.out.println(cabezote.get(i));
                    Set<String> conjunto = new LinkedHashSet<String>();
                    String cuerpos[] = cuerpo.get(i).split(" ");
                    for (String cuerpo : cuerpos) {
                        conjunto.add(cuerpo);
                    }
                    producciones.put(cabezote.get(i), conjunto);
                }
//                Collections.sort(cabezote);
//                Collections.reverse(cabezote);
//              Aqui los ordeno pa que quede bonito
//                for (int i = 0; i < cabezote.size(); i++) {
//                    System.out.println(cabezote.get(i));
//                    String[] cuerpo = cabezote.get(i).split("->");
//                    String[] cuerpos = cuerpo[1].split(" ");
//                    for (int j = 0; j < cuerpos.length; j++) {
//                        jTextArea1.append(cuerpo[0] + "->" + cuerpos[j]);
//                        jTextArea1.append(System.getProperty("line.separator"));
//                    }
//
//                }
                producciones.forEach((k, v) -> {
                    for (String string : v) {
                        jTextArea1.append(k + "->" + string);
                        jTextArea1.append(System.getProperty("line.separator"));
                    }
                });
                Set<String> prokeys = producciones.keySet();
                for (String prokey : prokeys) {
                    primero.put(prokey, new LinkedHashSet<String>());
                    siguiente.put(prokey, new LinkedHashSet<>());
                }
                ComputeFirst();
                computeFollow();
//
//                System.out.println("aquiiiiiiiiiiii empieza");
//
//                for (int i = 0; i < cabezote.size(); i++) {
//                    String primero = "";
//                    String[] cuerpo = cabezote.get(i).split("->");
//                    String[] cuerpos = cuerpo[1].split(" ");
//                    for (int j = 0; j < cuerpos.length; j++) {
//                        System.out.println("aqui verifico " + cabezote2.get(i) + " -> " + cuerpos[j]);
//                        String po = Primero(cuerpos[j]);
//                        for (int k = 0; k < po.length(); k++) {
//                            if (!primero.contains(po.substring(k, k + 1))) {
//                                primero = primero + po.substring(k, k + 1);
//                            }
//                        }
//                    }
//                    Primero.add(primero);
//                }
                System.out.println("Primerooooooooooooooooooooooooooo");
                pk = new DefaultTableModel();
                String hp[] = {"Cabezote", "Primero"};
                pk.setColumnIdentifiers(hp);
                TablaPrimero.setModel(pk);
                primero.forEach((k, v) -> {
                    pk.addRow(new Object[]{k, v});
                });
                pk1= new DefaultTableModel();
                String hp2[] = {"Cabezote","Siguiente"};
                pk1.setColumnIdentifiers(hp2);
                TablaSiguiente.setModel(pk1);
                siguiente.forEach((k, v) -> {
                    pk1.addRow(new Object[]{k, v});
                });
//                for (int i = 0; i < Primero.size(); i++) {
//                    pk.addRow(new Object[]{cabezote2.get(i), Primero.get(i)});
//                }
//                for (String string : Primero) {
//                    System.out.println(string);
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JRuta;
    private javax.swing.JTable TablaPrimero;
    private javax.swing.JTable TablaSiguiente;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
