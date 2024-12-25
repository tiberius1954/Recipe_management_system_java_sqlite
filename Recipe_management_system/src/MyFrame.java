import javax.swing.*;
import java.awt.Graphics;
import javax.swing.*;

public class MyFrame extends JFrame{  
    private MyPanel panel;
    
    public MyFrame(){
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        panel = new MyPanel(0,0);    
        this.add(panel);    
        this.setVisible(true);
    } 
  
    public MyPanel getPanel(){
        return panel;
    }
    public class MyPanel extends JPanel{       
        private int x;
        private int y;   
        public MyPanel(int x, int y){
            this.x = x;
            this.y = y;
        }
        /*the method that deals with the graphics 
            this method is called when the component is first loaded, 
             when the component is resized and when the repaint() method is 
            called for this component
        */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);     
            x++;
            y++;    
            g.fillRect(x, y, 50, 50);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyFrame frame = new MyFrame();        
        while(true){
            frame.getPanel().repaint();                 
            Thread.sleep(12);
        }
    }
}