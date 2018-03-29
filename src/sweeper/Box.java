package sweeper;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image;

    //получение следующего значения
    Box nextNumberBox(){
        return Box.values()[this.ordinal()+1];
    }

    //получение номера картинки
    int getNumber(){
        int nr = ordinal();
        if (nr>=Box.NUM1.ordinal() && nr <=Box.NUM8.ordinal()){
            return nr;
        }
        return -1;
    }
}
