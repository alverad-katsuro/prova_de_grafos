package grafos.gui;
import javax.swing.*;

import grafos.struct.Print;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Teste {
  public static void main(String[] args){
    JFrame janela = new JFrame("Título da Janela");
    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JButton botao = new JButton("Me Aperta!");
    final JTextField tf=new JTextField("Testeeeee 0");  
    JLabel label = new JLabel(tf.getText());
    botao.addActionListener(new ActionListener(){
      public Print print = new Print();
      @Override
      public void actionPerformed(ActionEvent e) {
        //tf.setText(getPrint().teste_fun());
      }
      public Print getPrint() {
        return print;
      }
    });
    JPanel panel = new JPanel();
    panel.add(botao);
    panel.add(tf);
    janela.getContentPane().add(panel);
    janela.pack();
    janela.setVisible(true);
  }
}
