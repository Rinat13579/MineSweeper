package sweeper;

public class Coord {
    public int x;
    public int y;

    //структура для хранения координат
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //переопределенный equals для сравнения координат друг с другом
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord)){
            return super.equals(obj);
        }
        Coord to = (Coord)obj;
        return to.x==x && to.y==y;
    }
}
