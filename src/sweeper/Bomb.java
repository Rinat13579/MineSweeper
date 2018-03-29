package sweeper;

//нижний уровень. здесь хранится, то что скрыто в начале игры
public class Bomb {
    //матрица с бомбами
    private Matrix bombMap;
    //общее число бомб
    private int totalBombs;

    Bomb(int totalBombs){
        this.totalBombs=totalBombs;
        //ограничение максимального количества бомб
        fixBombsCount();
    }

    //создать карту и нужное количество бомб
    void start(){
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i<totalBombs;i++){
            placeBomb();
        }

    }

    Box get(Coord coord){
        return bombMap.get(coord);
    }

    int getTotalBombs(){
        return totalBombs;
    }

    //ограничение максимального количества бомб
    private void fixBombsCount(){
        int maxBombs = Ranges.getSquare()/2;
        if(totalBombs>maxBombs){
            totalBombs=maxBombs;
        }
    }

    //Для размещения бомбы, Дважды в одну воронку нельзя
    private void placeBomb(){
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord)){
                continue;
            }
            bombMap.set(coord, Box.BOMB);
            //увеличение на единицу вокруг
            incNumbersAroundBomb(coord);
            break;
        }
    }

    //после размещения бомбы, увеличение на еденицу вокруг. для отображения правильных цифр
    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoorsAround(coord)){
            if (Box.BOMB != bombMap.get(around)){
                bombMap.set(around, bombMap.get(around).nextNumberBox());
            }
        }
    }
}
