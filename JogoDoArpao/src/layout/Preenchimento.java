package layout;

import java.awt.GridBagConstraints;

public enum Preenchimento  {

    horizontal, vertical, nenhum;

    public int getGBC() {
        switch (this) {
            case horizontal:
                return GridBagConstraints.HORIZONTAL;
            case vertical:
                return GridBagConstraints.VERTICAL;
            case nenhum:
                return GridBagConstraints.NONE;
        }
        throw new RuntimeException("Valor do Preenchimento inválido");
    }
}
