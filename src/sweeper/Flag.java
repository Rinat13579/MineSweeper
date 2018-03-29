package sweeper;

//верхний уровень
class Flag {

    //матрица с верхним уровнем. информация открыта клетка или нет. стоит флаг или нет
    private Matrix flagMap;
    //общее количество флагов
    private int totalFlaged;
    //общее количество закрытых клеток
    private int totalClosed;

    //для запуска игры
    void start(){
        flagMap = new Matrix(Box.CLOSED);
        totalFlaged=0;
        totalClosed=Ranges.getSquare();
    }

    Box get(Coord coord){
        return flagMap.get(coord);
    }

    int getTotalFlaged() {
        return totalFlaged;
    }

    int getTotalClosed() {
        return totalClosed;
    }

    //установить значение открытый
    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        totalClosed--;
    }

    //установить флаг
    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
        totalFlaged++;
    }

    //убрать флаг
    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
        totalFlaged--;
    }

    //переключить значение флага
    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord);break;
            case CLOSED: setFlagedToBox(coord);break;
        }
    }

    //установить флаги на оставщиеся пустые клетки после заверщения игры
    void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords()){
            if (Box.CLOSED == flagMap.get(coord)){
                setFlagedToBox(coord);
            }
        }
    }

    //установить помбу после подрыва
    void setBombedToBox(Coord coord) {
        flagMap.set(coord,Box.BOMBED);
    }

    //открыть закрытые клетки для отображения бомб, если проигрыш
    void setOpenedToClosedBox(Coord coord) {
        if(Box.CLOSED == flagMap.get(coord)){
            flagMap.set(coord, Box.OPENED);
        }
    }

    //установить иконку безбомбы, в случае если мы проиграли и установили неверный флаг
    void setNobombToFlagedBox(Coord coord) {
        if (Box.FLAGED == flagMap.get(coord)){
            flagMap.set(coord, Box.NOBOMB);
        }
    }

    //получить количество флагов вокруг клетки, для открытия автоматически
    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoorsAround(coord)){
            if (Box.FLAGED == flagMap.get(around)){
                count++;
            }
        }
        return count;
    }
}
