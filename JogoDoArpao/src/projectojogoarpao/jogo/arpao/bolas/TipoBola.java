package projectojogoarpao.jogo.arpao.bolas;

public enum TipoBola {

    grande, media, pequena, micro;

    public TipoBola anterior() {
        switch (this) {
            case grande:
                return media;
            case media:
                return pequena;
            case pequena:
                return micro;
        }
        return null;
    }

    public static TipoBola get(int tipo) {
        switch (tipo) {
            case 4:
                return grande;
            case 3:
                return media;
            case 2:
                return pequena;
            case 1:
                return micro;
        }
        return null;
    }

    public int getTipoOrd() {
        switch (this) {
            case grande:
                return 4;
            case media:
                return 3;
            case pequena:
                return 2;
            case micro:
                return 1;
        }
        return 0;
    }
}
