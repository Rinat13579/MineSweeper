package sweeper;

//контроллер
public class Game {
    //нижний уровень
    private Bomb bomb;
    //верхний уровень
    private Flag flag;
    //статус игры
    private GameState state;

    //инициализация параметров
    public Game(int cols, int rows, int bombs){
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    //перезапуск уровня
    public void start(){

        bomb.start();
        flag.start();
        //установка начального состояния игры
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord){
        //если открыта берем нижний уровень и наоборот
        if (Box.OPENED == flag.get(coord)){
            return bomb.get(coord);
        }
        else {
            return flag.get(coord);
        }
    }

    //нажатие левой кнопки
    public void pressLeftButton(Coord coord){
        if (isGameOver()) return;
        openBox(coord);
        checkWinner();
    }

    //нажатие правой кнопки
    public void pressRightButton(Coord coord){
        if (isGameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    //получение состояния игры
    public GameState getState() {
        return state;
    }

    //общее количество бомб
    public int getTotalBombs(){
        return bomb.getTotalBombs();
    }

    //общее количество флагов
    public int getTotalFlaged(){
        return flag.getTotalFlaged();
    }

    //проверка завершенности игры и перезапуск игры
    private boolean isGameOver(){
        if (GameState.PLAYED != state){
            start();
            return true;
        }
        return false;
    }

    //проверка на выигрыш
    private void checkWinner() {
        if (state == GameState.PLAYED){
            if (flag.getTotalClosed()==bomb.getTotalBombs()){
                state=GameState.WINNER;
                flag.setFlagedToLastClosedBoxes();
            }
        }
    }

    //открытие клетки с перечисление состояний клеток и выбором
    private void openBox(Coord coord) {
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); break;
            case FLAGED: break;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO: openBoxesAroundZero(coord); break;
                    case BOMB: openBombs(coord); break;
                    default  : flag.setOpenedToBox(coord); break;
                }
        }
    }

    //открытие закрытых клеток вокруг числа если все бомбы вокруг него помечены
    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (Box.BOMB != bomb.get(coord)){
            if (bomb.get(coord).getNumber() == flag.getCountOfFlagedBoxesAround(coord)){
                for (Coord around : Ranges.getCoorsAround(coord)){
                    if (flag.get(around)==Box.CLOSED){
                        openBox(around);
                    }
                }
            }
        }
    }

    //открытие бомбы на которой подорвались и показ остальных бомб и неправильное расположение флагов
    private void openBombs(Coord bombedCoord) {
        flag.setBombedToBox(bombedCoord);
        for (Coord coord : Ranges.getAllCoords()){
            if (bomb.get(coord) == Box.BOMB){
                flag.setOpenedToClosedBox(coord);
            }
            else{
                flag.setNobombToFlagedBox(coord);
            }
        }
        state=GameState.BOMBED;
    }

    //рекурсивное открытие пустых клеток
    private void openBoxesAroundZero(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoorsAround(coord)){
            openBox(around);
        }
    }
}
