package ztests;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * TestPaintComponentZooming
 * 
 * @author davidf
 */
public class TestPaintComponentZooming {
    private JFrame f;
    private JPanel panel;
    private double zoom = 1.0;

    public static void main(String[] args) {
        TestPaintComponentZooming t = new TestPaintComponentZooming();
        t.init();
    }

    public TestPaintComponentZooming() {
    }

    public void init() {
        JButton b = new JButton();
        b.setBackground(Color.red);
        b.setBorder(new LineBorder(Color.black, 2));
        b.setPreferredSize(new Dimension(600, 10));
        panel = new _JPanel();
        panel.add(b);
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel, "Center");
        f.add(getCheckBoxPanel(), "South");
        f.setLocation(200, 200);
        f.setSize(400, 400);
        f.validate();
        f.setVisible(true);
    }

    private JPanel getCheckBoxPanel() {
        JButton zoomIn = new JButton("Zoom in");
        zoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomAndRepaint(0.1);
            }
        });
        JButton zoomOut = new JButton("Zoom out");
        zoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomAndRepaint(-0.1);
            }
        });
        JPanel panel2 = new JPanel();
        panel2.add(zoomIn);
        panel2.add(zoomOut);
        return panel2;
    }

    /**
     * zoomAndRepaint
     */
    protected void zoomAndRepaint(double d) {
        zoom += d;
        f.repaint();
    }

    private class _JPanel extends JPanel {

        public _JPanel() {
            super(null);
        }

        /**
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            g.setFont(new Font("Arial", Font.PLAIN, (int) (zoom * 10 + 2)));
            for (int x=0; x < 100; x++) {
                for (int y=0; y < 100; y++) {
                    g.drawString("Hello " + x + "," + y, (int) (x * 60 * zoom), (int) (y * 10 * zoom));                    
                }
            }
        }
    }
}