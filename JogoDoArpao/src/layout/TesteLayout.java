package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TesteLayout {

    public static JPanel criaPainel(){
        return new TesteLayout().criador();
    }
    
    private JLabel criaJLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setAlignmentX(0.5f);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentY(0.5f);
        return label;
    }

    public JPanel criador() {
        JPanel painel = new JPanel(new GridBagLayout());
        GrupoCelulas grupo1;
        GrupoCelulas grupo2;
        GrupoCelulas grupo3;
        JLabel label1 = criaJLabel("1");
        JLabel label2 = criaJLabel("2");
        JLabel label3 = criaJLabel("3");
        JLabel label4 = criaJLabel("4");
        JLabel label5 = criaJLabel("5");


        label1.setPreferredSize(new Dimension(300, 300));
        //label2.setPreferredSize(new Dimension(20, 50));
        //label3.setPreferredSize(new Dimension(20, 80));
        //label4.setPreferredSize(new Dimension(50, 70));
        //label5.setPreferredSize(new Dimension(20, 20));
        grupo1 = new GrupoCelulas();
        grupo2 = new GrupoCelulas();
        grupo3 = new GrupoCelulas();
        
        CelulaPainel cel1;
        CelulaPainel cel2;
        CelulaPainel cel3;
        CelulaPainel cel4;
        CelulaPainel cel5;
        
        cel1 = grupo1.adComponente(label1);
        cel2 = grupo2.adComponenteNL(label2);
        cel3 = grupo2.adComponente(label3);
        cel4 = grupo2.adComponente(label4);
        cel5 = grupo3.adComponente(label5);
        
        cel1.setAltura(grupo2.getLinhas());
        cel1.setAlinhamento(Alinhamento.oeste);
        //cel1.setPesoX(1);

        cel2.setLargura(2);
        cel2.setPreenchimento(Preenchimento.horizontal);
        cel2.setPesoX(1);
        
        cel3.setPesoX(1);
        
        cel4.setPesoX(1);
        
        cel5.setPreenchimento(Preenchimento.horizontal);
        cel5.setLargura(grupo1.getColunas()+grupo2.getColunas());
        cel5.setAlinhamento(Alinhamento.sul);
        cel5.setPesoY(1);
        
        
        grupo2.deslocaColunas(grupo1.getColunas());
        grupo3.deslocaLinhas(2);
        
        /*4 debug
        String l[] = new String[]{"linha", "coluna", "altura", "largura"},
                f[] = new String[4];
        for (int i = 0; i < 4; i++) {
            f[i] = "%" + l[i].length() + "d";
        }
        System.out.print("|");
        for (int i = 0; i < 4; i++) {
            System.out.printf("%s|", l[i]);
        }

        System.out.println();
        for (CelulaPainel celulaPainel : grupo2.celulas) {
            System.out.print("|");
            System.out.printf(f[0] + "|", celulaPainel.getGbc().gridy);
            System.out.printf(f[1] + "|", celulaPainel.getGbc().gridx);
            System.out.printf(f[2] + "|", celulaPainel.getGbc().gridheight);
            System.out.printf(f[3] + "|", celulaPainel.getGbc().gridwidth);
            System.out.println();
        }
         * 
         */
        grupo1.adAoPainel(painel);
        grupo2.adAoPainel(painel);
        grupo3.adAoPainel(painel);
        return painel;
    }
}
