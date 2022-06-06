package grafos.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas implements Runnable{
  private int width;
  private int height;
  private String title;
  public JFrame frame = new JFrame(title);

  public Window(int width, int height, String title){
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    setWidth((int)screenSize.getWidth());
    setHeight((int)screenSize.getHeight());
    getFrame().setTitle(title);
    getFrame().setSize(getWidth() / 2, getHeight() / 2);
    getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getFrame().setResizable(true);
    getFrame().setLocationRelativeTo(null);
    getFrame().setVisible(true);


    JLabel grafo_img = new JLabel();
    getFrame().getContentPane().add(grafo_img, BorderLayout.NORTH);


  }

  @Override
  public void run() {

  }

  public void setFrame(JFrame frame) {
    this.frame = frame;
  }

  public JFrame getFrame() {
    return frame;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getTitle() {
    return title;
  }

  
}
