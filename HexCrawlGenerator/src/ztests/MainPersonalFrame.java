package ztests;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainPersonalFrame {
   public static void main(String[] args) {
      MyFrame frame = new MyFrame();
      frame.setVisible(true);
   }
}

class MyFrame extends JFrame {

   private static final long serialVersionUID = 1L;
   private Container contentPane;
   public static final int startWidth = 1280;
   public static final int startHeight = 800;

   // !! This should be private and not static
   private MyPanel earthPanel;

   // !! this should be a field
   private JScrollPane scroll;

   public MyFrame() {
      initComponents();
   }

   public void initComponents() {
      contentPane = getContentPane();
      this.setTitle("TLE Graphic Propagator");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // !! avoid setting sizes if possible
      this.setSize(startWidth, startHeight);
      this.setLocation(0, 0);
      this.setLayout(new BorderLayout());
      earthPanel = new MyPanel(startWidth, startHeight);

      // !! again, scroll is now a field
      scroll = new JScrollPane(earthPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      MySlider slider = new MySlider(this);
      contentPane.add(scroll, BorderLayout.CENTER);
      contentPane.add(slider, BorderLayout.SOUTH);
   }

   // !! to avoid having classes directly manipulate other class's fields
   public void setEarthPanelSize(Dimension size) {
      earthPanel.setPreferredSize(size);
   }

   // !! allow other classes the ability to revalidate/repaint viewport
   public void revalidateViewport() {
      scroll.getViewport().revalidate();
      scroll.getViewport().repaint();
   }
}

class MyPanel extends JPanel {

   private static final long serialVersionUID = 1L;
   private BufferedImage img;
   private int width, height;

   // !! changes so that I can run your program
   // !! with an internet image
   // !! private String path = "images/earth1280x800.jpg";
   private String urlPath = "http://image.desk7.net/"
         + "Space%20Wallpapers/1422_1280x800.jpg";

   // !! private static final int UPDATE_RATE = 10; // !! never used

   public MyPanel(int larghezzaFrame, int altezzaFrame) {
      setOpaque(false); // !! this should be here
      width = larghezzaFrame;
      height = altezzaFrame;

      this.setPreferredSize(new Dimension(width, height));

      img = loadImage();
   }

   public BufferedImage loadImage() {
      BufferedImage bimg = null;
      BufferedImage ret = null;
      try {
         URL imgUrl = new URL(urlPath);
         // !! bimg = ImageIO.read(new File(path)); // !!
         bimg = ImageIO.read(imgUrl); // !! use image available to all
         // !! } catch (Exception e) {
         // e.printStackTrace();
      } catch (IOException e) {
         // !! use more specific exception
         e.printStackTrace();
      }
      ret = new BufferedImage(bimg.getWidth(), bimg.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = ret.createGraphics();
      g.drawImage(bimg, 0, 0, null);
      g.dispose();

      return ret;
   }

   @Override
   protected void paintComponent(Graphics g) {
      // !! this shouldn't be in paintComponent:
      // !! setOpaque(false); 

      Graphics2D g2d = (Graphics2D) g;
      AffineTransform at = new AffineTransform();
      at.scale(MySlider.SCALE, MySlider.SCALE);
      g2d.transform(at);
//      g2d.scale(MySlider.SCALE, MySlider.SCALE);
      g2d.drawImage(img, 0, 0, null);
      super.paintComponent(g2d);
   }

}

class MySlider extends JSlider implements ChangeListener {

   private static final long serialVersionUID = 1L;
   public static double SCALE = 1;
   private MyFrame myFrame;

   public MySlider(MyFrame myFrame) {
      super(JSlider.HORIZONTAL, 100, 400, 100);
      this.myFrame = myFrame;
      setMajorTickSpacing(50);
      setMinorTickSpacing(10);
      setPaintTicks(true);
      setPaintLabels(true);
      addChangeListener(this);
   }

   @Override
   public void stateChanged(ChangeEvent arg0) {
      int value = ((JSlider) arg0.getSource()).getValue();
      SCALE = value / 100.0;
      int w = (int) (MyFrame.startWidth * MySlider.SCALE);
      int h = (int) (MyFrame.startHeight * MySlider.SCALE);
      Dimension size = new Dimension(w, h);
      myFrame.setEarthPanelSize(size); // !!
      myFrame.revalidateViewport(); // !!
      // !! MyFrame.earthPanel.repaint(); // No, don't do this
   }
}