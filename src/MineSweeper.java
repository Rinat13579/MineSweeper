import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

//Все методы доступны из класса JFrame
public class MineSweeper extends JFrame {

    private Game game;

    //Основная панель и метка
    private JPanel panel;
    private JLabel label;

    //Игровые константы
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new MineSweeper();
    }

    //конструктор, инициализирует класс для игры
    private MineSweeper(){
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel(getMessage());
        Font font = new Font("Tahoma",Font.BOLD,18);
        label.setFont(font);
        add(label, BorderLayout.SOUTH);
    }

    //инициализация панели
    private void initPanel() {
        //Панель, задаем размер, и добавляем ее
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCoords()){
                    g.drawImage((Image)game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }
            }
        };

        //обработка кнопок мыши
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                switch (e.getButton()){
                    case MouseEvent.BUTTON1: game.pressLeftButton(coord);break;
                    case MouseEvent.BUTTON3: game.pressRightButton(coord);break;
                    case  MouseEvent.BUTTON2: game.start();break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE));
        add(panel);
    }

    //инициализация фрейма
    private void initFrame() {
        //Завершение программы по нажатию крестика
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper");
        //Нельзя изменить окно
        setResizable(false);
        //Применение изменения размера панели
        pack();
        setVisible(true);
        //установка запуска окна посередине
        setLocationRelativeTo(null);
    }

    //
    private void setImages(){
        for (Box box : Box.values()){
            box.image = getImage(box.name().toLowerCase());
        }
        setIconImage(getImage("icon"));
    }

    //
    private Image getImage(String name){
        String filename = "img/"+name+".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    //Выводит сообщения о состоянии игры
    private String getMessage(){
        switch (game.getState()){
            case BOMBED: return "Ba-Da-Bum! You Lose!";
            case WINNER: return "Congratulations! All bombs have been marked!";
            case PLAYED:
            default    : if (game.getTotalFlaged()==0){
                return "Welcome!";
            }
            else{
                return "Think twice! Flagged " + game.getTotalFlaged()+ " of " + game.getTotalBombs() +"!";
            }
        }
    }
}
