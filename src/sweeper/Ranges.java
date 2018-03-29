package sweeper;

import java.util.ArrayList;
import java.util.Random;

//хранятся статичные методы
public class Ranges {
    //размеры экрана
    static private Coord size;
    //список всех координат
    private static ArrayList<Coord> allCoords;
    //для случайного расположения бомб
    static private Random random = new Random();

    //установка размеров экрана
    static void setSize(Coord size) {
        Ranges.size = size;
        allCoords = new ArrayList<Coord>();
        for (int x=0; x<size.x; x++){
            for (int y=0; y<size.y; y++){
                allCoords.add(new Coord(x,y));
            }
        }
    }

    //получение размера экрана
    static public Coord getSize() {
        return size;
    }

    //получение всех координат
    static public ArrayList<Coord> getAllCoords(){
        return allCoords;
    }

    //проверка нахождения внутри поля
    static boolean inRange(Coord coord){
        return coord.x >=0 && coord.x < size.x && coord.y >=0 && coord.y < size.y;
    }

    //нахождение случайной координаты
    static Coord getRandomCoord(){
        return new Coord(random.nextInt(size.x),random.nextInt(size.y));
    }

    //проход всех клеток вокруг этой клетки
    static ArrayList<Coord> getCoorsAround(Coord coord){
        Coord around;
        ArrayList<Coord> list = new ArrayList<Coord>();
        for (int x = coord.x-1; x<=coord.x+1;x++){
            for (int y = coord.y-1; y<=coord.y+1;y++){
                if (inRange(around=new Coord(x,y))){
                    if (!around.equals(coord)){
                        list.add(around);
                    }
                }
            }
        }
        return list;
    }

    //вычисление площади,для вычисления начального количества закрытых клеток
    static int getSquare() {
        return size.x*size.y;
    }
}
