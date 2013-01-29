package projectojogoarpao.jogo.arpao.editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import layout.Alinhamento;
import layout.CelulaPainel;
import layout.GrupoCelulas;
import projectojogoarpao.jogo.arpao.bolas.BolaQE;
import projectojogoarpao.jogo.arpao.bolas.JBola;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;

public class PainelEdicao extends JPanel implements AccoesQE, ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PainelEdicao() {
        super(new GridBagLayout());
        iniciarPainel();
    }
    private JBola arr[] = new JBola[4];
    private JLabel jlStatus;
    private ButtonGroup accoesBG;
    private JRadioButton jrbInsercao = new JRadioButton("Inserção");
    private JRadioButton jrbSeleccao = new JRadioButton("Selecção");
    private JComboBox jCBFundo = new JComboBox();
    private JButton jbCarregar = new JButton("Carregar");
    private JButton jbSalvar = new JButton("Salvar");
    private JTextField jtfNivel = new JTextField(4);
    private JTextField jtfVX = new JTextField(4);
    private JTextField jtfVY = new JTextField(4);
    private JButton jbRemoveBola = new JButton("Remover bola");
    private QuadroEdicao qe;
    private TipoAccao ultimaAccao;

    private void iniciarPainel() {
        qe = new QuadroEdicao(new Dimension(800, 600));

        jlStatus = new JLabel("Status");
        jbRemoveBola.setEnabled(false);

        LinkedList<JComponent> componentes = new LinkedList<JComponent>();

        jlStatus.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        accoesBG = new ButtonGroup();

        jrbInsercao.addActionListener(this);
        jrbSeleccao.addActionListener(this);
        jbRemoveBola.addActionListener(this);
        jCBFundo.addActionListener(this);
        jbCarregar.addActionListener(this);
        jbSalvar.addActionListener(this);

        jtfVX.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                int vx = Integer.parseInt(jtfVX.getText());
                qe.getSeleccionada().setVx(vx);
            }
        });

        jtfVY.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                int vy = Integer.parseInt(jtfVY.getText());
                qe.getSeleccionada().setVy(vy);
            }
        });

        accoesBG.add(jrbSeleccao);
        accoesBG.add(jrbInsercao);
        jrbSeleccao.setSelected(true);

        for (int i = 0; i < 4; i++) {
            arr[i] = new JBola(TipoBola.get(i + 1));
        }

        qe.setSelectorBola(this);


        //criaLayout1(componentes);
        criaLayout(componentes);

        qe.carregaNivel(1);
        actionPerformed(new ActionEvent(jrbSeleccao, 0, null));
        setAutorizacaoRemocao(false);

    }

    private void criaLayout(LinkedList<JComponent> componentes) {
        CelulaPainel celula;
        JLabel jlNivel = new JLabel("Nivel");
        JLabel jlVX = new JLabel("Velocidade X");
        JLabel jlVY = new JLabel("Velocidade Y");
        GrupoCelulas grupoCelulas1 = new GrupoCelulas();
        GrupoCelulas grupoCelulas2 = new GrupoCelulas();
        GrupoCelulas grupoCelulas3 = new GrupoCelulas();
        CelulaPainel celQE;
        CelulaPainel celjlNivel;
        CelulaPainel celjtfNivel;
        CelulaPainel celJPainelFundo;
        CelulaPainel celJPainelTipoAccao;
        JPanel painelFundo = criaPainelFundo();

        celQE = grupoCelulas1.adComponente(qe);

        celjlNivel = grupoCelulas2.adComponente(jlNivel,2);
        celjlNivel.setAlinhamento(Alinhamento.este);
        celjlNivel.setPesoX(1);
        celjlNivel.getMargem().setDireita(10);

        celjtfNivel = grupoCelulas2.adComponenteNL(jtfNivel,2);
        celjtfNivel.setAlinhamento(Alinhamento.oeste);
        celjtfNivel.setPesoX(1);
        celjtfNivel.getMargem().setEsquerda(10);
        
        for (JBola jBola : arr) {
            CelulaPainel celBola = grupoCelulas2.adComponenteNL(jBola);
            celBola.setLargura(4);
            celBola.setPesoY(1);
            celBola.setAlinhamento(Alinhamento.centro);
        }

        celulaPesos11(grupoCelulas3.adComponente(jlVX));
        celulaPesos11(grupoCelulas3.adComponente(jtfVX));
        celulaPesos11(grupoCelulas3.adComponente(jlVY));
        celulaPesos11(grupoCelulas3.adComponenteNL(jtfVY));
        
        celula = grupoCelulas3.adComponente(jbCarregar,2);
        celula.setAltura(1);
        celula.setAlinhamento(Alinhamento.centro);
        celulaPesos11(celula);
        
        
        celula = grupoCelulas3.adComponente(jbSalvar,2);
        celula.setAltura(1);
        celula.setAlinhamento(Alinhamento.centro);
        celulaPesos11(celula);
        
       
        
        int colunasGrupo3 = grupoCelulas3.getColunas();
        
        celJPainelFundo = grupoCelulas2.adComponenteNL(painelFundo);
        celJPainelFundo.setLargura(colunasGrupo3);

        celJPainelTipoAccao = grupoCelulas2.adComponenteNL(criaPainelTipoAccao());
        celJPainelTipoAccao.setAltura(1);
        celJPainelTipoAccao.setLargura(colunasGrupo3);
        
        CelulaPainel celJBRemover = grupoCelulas2.adComponente(jbRemoveBola);
        celJBRemover.setAltura(1);
        celJBRemover.setLargura(colunasGrupo3);
        
        
        

//        componentes.add(painelFundo);//8
//        componentes.add(criaPainelTipoAccao());//9
//        componentes.add(jbRemoveBola);//10
//        componentes.add(jlVX);//11
//        componentes.add(jtfVX);//12
//        componentes.add(jlVY);//13
//        componentes.add(jtfVY);//14
//        componentes.add(jbCarregar);//15
//        componentes.add(jbSalvar);//16
//        componentes.add(jlStatus);//17


        celQE.setAltura(grupoCelulas2.getLinhas()+grupoCelulas3.getLinhas());
        grupoCelulas2.deslocaColunas(grupoCelulas1.getColunas());
        
        grupoCelulas3.deslocaLinhas(grupoCelulas2.getLinhas());
        grupoCelulas3.deslocaColunas(grupoCelulas1.getColunas());

        
        grupoCelulas1.adAoPainel(this);
        grupoCelulas2.adAoPainel(this);
        grupoCelulas3.adAoPainel(this);
    }

    private void celulaPesos11(CelulaPainel celula){
        celula.setAltura(1);
        celula.setPesoX(1);
        celula.setPesoY(1);
    }
    
    private void setUltimaAccao() {
        if (jrbInsercao.isSelected()) {
            ultimaAccao = TipoAccao.insercao;
            return;
        }
        if (jrbSeleccao.isSelected()) {
            ultimaAccao = TipoAccao.seleccao;
            return;
        }
    }

    private JPanel criaPainelFundo() {
        JPanel painelFundo = null;
        String[] ficheirosFundo = getFicheirosFundo();

        jCBFundo.setModel(new DefaultComboBoxModel(ficheirosFundo));
        painelFundo = new JPanel();
        painelFundo.setBorder(BorderFactory.createTitledBorder("Fundo"));
        painelFundo.add(jCBFundo);

        return painelFundo;
    }

    private JPanel criaPainelTipoAccao() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        painel.setBorder(BorderFactory.createTitledBorder("Tipo de acção"));
        painel.add(jrbSeleccao);
        gbc.gridx++;
        painel.add(jrbInsercao);
        return painel;
    }

    public TipoBola getTipoBola() {
        for (int i = 0; i < 4; i++) {
            if (arr[i].isFocusOwner()) {
                return arr[i].getTipo();
            }
        }
        return null;
    }

    private String[] getFicheirosFundo() {
        File dirFicheiros = new File("imagens/fundos");
        File[] ficheiros = dirFicheiros.listFiles();
        int tam = ficheiros.length;

        String[] as = new String[tam];
        for (int i = 0; i < tam; i++) {
            as[i] = ficheiros[i].getName();
        }
        return as;
    }

    public TipoAccao getAccaoActual() {
        if (jrbSeleccao.isSelected()) {
            return TipoAccao.seleccao;
        }
        if (jrbInsercao.isSelected()) {
            return TipoAccao.insercao;
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jrbInsercao
                && (ultimaAccao == null
                || !ultimaAccao.equals(TipoAccao.insercao))) {
            setUltimaAccao();
            for (JBola jBola : arr) {
                jBola.setEnabled(true);
                jBola.repaint();
            }
            qe.alternaAccao(TipoAccao.insercao);
            return;
        }
        if (e.getSource() == jrbSeleccao
                && (ultimaAccao == null
                || !ultimaAccao.equals(TipoAccao.seleccao))) {
            setUltimaAccao();
            for (JBola jBola : arr) {
                jBola.setEnabled(false);
                jBola.repaint();
            }
            qe.alternaAccao(TipoAccao.seleccao);
            return;
        }
        if (e.getSource() == jbRemoveBola) {
            qe.removeBolaSeleccionada();
            return;
        }
        if (e.getSource() == jCBFundo) {
            qe.mudaFundo(jCBFundo.getSelectedItem().toString());
            return;
        }
        if (e.getSource() == jbCarregar) {
            qe.carregaNivel(Integer.parseInt(jtfNivel.getText()));
            return;
        }
        if (e.getSource() == jbSalvar) {
            qe.salvaNivel(Integer.parseInt(jtfNivel.getText()));
        }
    }

    public void setAutorizacaoRemocao(boolean autorizacao) {
        jbRemoveBola.setEnabled(autorizacao);
        jtfVX.setEnabled(autorizacao);
        jtfVY.setEnabled(autorizacao);
        if (autorizacao) {
            BolaQE bs = qe.getSeleccionada();
            jtfVX.setText(String.valueOf(bs.getVx()));
            jtfVY.setText(String.valueOf(bs.getVy()));
        } else {
            jtfVX.setText("");
            jtfVY.setText("");
        }
    }

    public int getNumNivel() {
        return Integer.parseInt(jtfNivel.getText());
    }

    public String getFundo() {
        return jCBFundo.getSelectedItem().toString();
    }

   }
