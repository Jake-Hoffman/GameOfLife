import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class DisplayCells extends JComponent implements KeyListener, MouseListener{

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double height = screenSize.getHeight();
    private double width = screenSize.getWidth();

    private Controller controller;
    private CellSheet sheet;

    private CellModel[][] cells;

    private boolean clickDisable = false;
    private boolean clickedLeft = false;
    private boolean clickedRight = false;
    private boolean gameLoopEnabled = false;
    private boolean hasBegun = false;

    double ratio;

    public void setupCells(CellModel[][] cells){
        this.cells = cells;
        ratio = ((cells.length/height > cells[0].length/width)? height/cells.length : width/cells[0].length) * .9;
        this.setSize(new Dimension((int)(ratio * cells[0].length),(int)(ratio * cells.length)));
        this.setBounds(0, 0, (int)(ratio * cells[0].length),(int)(ratio * cells.length));
    }

    private void setCells(CellModel[][] cells){
        this.cells = cells;
    }

    private void paintCells(Graphics g){

        if (gameLoopEnabled){
            gameLoop();
        }

        if(clickedLeft){

            Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, this);
            int x = (int)((p.getX())/ratio);
            int y = (int)((p.getY())/ratio);
            cells[y][x].revive();
            sheet.setCells(cells);

        } else if (clickedRight) {
            
            Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, this);
            int x = (int)((p.getX())/ratio);
            int y = (int)((p.getY())/ratio);
            cells[y][x].kill();
            sheet.setCells(cells);
        }

        for (int yy = 0; yy < cells.length; yy++) {
            for (int xx = 0; xx < cells[yy].length; xx++) {
                if(cells[yy][xx].checkStatus()){
                    g.setColor(Color.WHITE);
                    g.drawRect((int)(ratio * xx), (int)(ratio * yy), (int)(ratio), (int)(ratio));
                    g.fillRect((int)(ratio * xx), (int)(ratio * yy), (int)(ratio), (int)(ratio));
                }
            }
        }

        Point p = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(p, this);

        int x = (int)((p.getX())/ratio);
        int y = (int)((p.getY())/ratio);

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect((int)(ratio * x), (int)(ratio * y), (int)(ratio), (int)(ratio));
        g.fillRect((int)(ratio * x), (int)(ratio * y), (int)(ratio), (int)(ratio));
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameLoopEnabled = true;
            clickDisable = true;
            hasBegun = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            cells = new CellSheet().getCells();

            clickDisable = false;
            gameLoopEnabled = false;
            hasBegun = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && hasBegun) {
            gameLoopEnabled = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && hasBegun) {
            gameLoopEnabled = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

    @Override
    public void mousePressed(MouseEvent arg0) {

        if (!clickDisable) {
            if (arg0.getButton() == 1) {
                clickedLeft = true;
            } else {
                clickedRight = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        clickedRight = false;
        clickedLeft = false;
    }

    public void gameLoop(){
            cells = sheet.getCells();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            sheet.gameTick();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setSheet(CellSheet sheet){
        this.sheet = sheet;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int)width, (int)height);
        paintCells(g);
        repaint();
    }

    public Dimension getPreferredSize() {

        try {
            return new Dimension((int)(ratio * cells[0].length),(int)(ratio * cells.length));
        } catch (NullPointerException e) {
            return new Dimension(1000,1000);
        }
    }

    public void createAndShowGUI(){

        JFrame f = new JFrame("Game of Life");

        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(this);
        f.pack();
        f.setVisible(true);
        f.addKeyListener(this);
        f.addMouseListener(this);
        f.setFocusable(true);

    }
}
