package layout;

import java.awt.GridBagConstraints;

public enum Alinhamento {

    centro,norte, sul, este, oeste, nordeste, noroeste, sudeste, sudoeste;

    public int getGBC() {
        switch (this) {
            case centro:
                return GridBagConstraints.CENTER;
            case norte:
                return GridBagConstraints.NORTH;
            case sul:
                return GridBagConstraints.SOUTH;
            case este:
                return GridBagConstraints.EAST;
            case oeste:
                return GridBagConstraints.WEST;
            case nordeste:
                return GridBagConstraints.NORTHEAST;
            case noroeste:
                return GridBagConstraints.NORTHWEST;
            case sudeste:
                return GridBagConstraints.SOUTHEAST;
            case sudoeste:
                return GridBagConstraints.SOUTHWEST;
        }
        throw new RuntimeException("Valor do Preenchimento inválido");
    }
}
