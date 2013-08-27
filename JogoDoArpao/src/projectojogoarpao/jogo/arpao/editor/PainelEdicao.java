package projectojogoarpao.jogo.arpao.editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;
import javax.swing.border.EtchedBorder;

import projectojogoarpao.jogo.arpao.bolas.BolaQE;
import projectojogoarpao.jogo.arpao.bolas.JBola;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;
import projectojogoarpao.jogo.arpao.estrelas.JEstrela;
import projectojogoarpao.jogo.fisica.Circulo;

public class PainelEdicao extends JPanel implements AccoesQE, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PainelEdicao() {
		super(new GridBagLayout());
		iniciarPainel();
	}

	private JBola arrJB[] = new JBola[4];
	private JEstrela arrJE[] = new JEstrela[4];
	private JLabel jlStatus;
	private ButtonGroup accoesBG;
	private JRadioButton jrbInsercao = new JRadioButton("Inserção");
	private JRadioButton jrbSeleccao = new JRadioButton("Selecção");
	private JComboBox<?> jCBFundo = new JComboBox<String>();
	private JButton jbCarregar = new JButton("Carregar");
	private JButton jbSalvar = new JButton("Salvar");
	private JTextField jtfNivel = new JTextField(4);
	private JTextField jtfVX = new JTextField(4);
	private JTextField jtfVY = new JTextField(4);
	private JButton jbRemoveBola = new JButton("Remover bola");
	private QuadroEdicao qe;
	private TipoAccao ultimaAccao;
	private JButton jBImprimir = new JButton("Imprimir");
	private PrinterJob pj;

	private void iniciarPainel() {
		qe = new QuadroEdicao(new Dimension(800, 600));

		jlStatus = new JLabel("Status");
		jbRemoveBola.setEnabled(false);

		jlStatus.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		accoesBG = new ButtonGroup();

		jrbInsercao.addActionListener(this);
		jrbSeleccao.addActionListener(this);
		jbRemoveBola.addActionListener(this);
		jCBFundo.addActionListener(this);
		jbCarregar.addActionListener(this);
		jbSalvar.addActionListener(this);
		jBImprimir.addActionListener(this);

		jtfNivel.setText("1");
		jtfVX.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				int vx = Integer.parseInt(jtfVX.getText());
				Circulo sel = qe.getSeleccionada();
				if (sel instanceof BolaQE) {
					BolaQE bolaQE = (BolaQE) sel;
					bolaQE.setVx(vx);
				}
			}
		});

		jtfVY.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				int vy = Integer.parseInt(jtfVY.getText());
				Circulo sel = qe.getSeleccionada();
				if (sel instanceof BolaQE) {
					BolaQE bolaQE = (BolaQE) sel;
					bolaQE.setVy(vy);
				}
			}
		});

		accoesBG.add(jrbSeleccao);
		accoesBG.add(jrbInsercao);
		jrbSeleccao.setSelected(true);

		for (int i = 0; i < 4; i++) {
			arrJB[i] = new JBola(TipoBola.get(i + 1));
			arrJE[i] = new JEstrela(20 * (i + 1));
		}

		qe.setSelectorBola(this);

		criaComponentes();

		qe.carregaNivel(1);
		actionPerformed(new ActionEvent(jrbSeleccao, 0, null));
		setAutorizacaoRemocao(false);
	}

	private void criaComponentes() {
		JLabel jlNivel = new JLabel("Nivel");
		JLabel jlVX = new JLabel("Velocidade X");
		JLabel jlVY = new JLabel("Velocidade Y");

		GridBagConstraints c = new GridBagConstraints();

		c.ipadx = 10;
		c.weightx = 1;
		c.weighty = 1;

		c.gridx = 1;
		c.gridy = 0;

		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		add(jlNivel, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		add(jtfNivel, c);

		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridwidth = 2;

		for (JBola jBola : arrJB) {
			c.gridy++;
			add(jBola, c);
		}

		c.gridx = 3;
		c.gridy = 0;
		for (JEstrela jEstrela : arrJE) {
			c.gridy++;
			add(jEstrela, c);
		}

		c.gridwidth = 4;
		c.gridx = 1;
		c.gridy++;
		add(criaPainelFundo(), c);

		c.gridy++;
		add(criaPainelTipoAccao(), c);

		c.gridy++;
		add(jbRemoveBola, c);

		c.gridy++;
		c.gridwidth = 1;
		add(jlVX, c);

		c.gridx = 2;
		jtfVX.setText("vx");
		add(jtfVX, c);

		c.gridx = 3;
		jtfVY.setText("vy");
		add(jlVY, c);

		c.gridx = 4;
		add(jtfVY, c);

		c.gridx = 1;
		c.gridy++;
		c.gridwidth = 2;
		add(jbCarregar, c);

		c.gridx = 3;
		add(jbSalvar, c);

		c.gridx = 1;
		c.gridy++;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.CENTER;
		add(jBImprimir, c);

		c.gridwidth = 1;
		c.gridheight = c.gridy + 1;
		c.gridy = 0;
		c.gridx = 0;
		add(qe, c);

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
			if (arrJB[i].isFocusOwner()) {
				return arrJB[i].getTipo();
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
				&& (ultimaAccao == null || !ultimaAccao
						.equals(TipoAccao.insercao))) {
			setUltimaAccao();
			for (JBola jBola : arrJB) {
				jBola.setEnabled(true);
				jBola.repaint();
			}
			for (JEstrela jEstrela : arrJE) {
				jEstrela.setEnabled(true);
				jEstrela.repaint();
			}
			qe.alternaAccao(TipoAccao.insercao);
			return;
		}
		if (e.getSource() == jrbSeleccao
				&& (ultimaAccao == null || !ultimaAccao
						.equals(TipoAccao.seleccao))) {
			setUltimaAccao();
			for (JBola jBola : arrJB) {
				jBola.setEnabled(false);
				jBola.repaint();
			}

			for (JEstrela jEstrela : arrJE) {
				jEstrela.setEnabled(false);
				jEstrela.repaint();
			}

			qe.alternaAccao(TipoAccao.seleccao);
			return;
		}
		if (e.getSource() == jbRemoveBola) {
			qe.removeSeleccionado();
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
		if (e.getSource() == jBImprimir) {
			pj = PrinterJob.getPrinterJob();
			pj.setPrintable(qe);
			try {
				if (pj.printDialog()) {
					pj.print();
				}
			} catch (PrinterException e1) {
				System.err.println("Erro ao tentar imprimir\n"+e1.getMessage());
			}
		}
	}

	public void setAutorizacaoRemocao(boolean autorizacao) {
		jbRemoveBola.setEnabled(autorizacao);
		jtfVX.setEnabled(autorizacao);
		jtfVY.setEnabled(autorizacao);
		if (autorizacao) {
			Circulo cs = qe.getSeleccionada();
			if (cs instanceof BolaQE) {
				BolaQE bolaQE = (BolaQE) cs;
				jtfVX.setText(String.valueOf(bolaQE.getVx()));
				jtfVY.setText(String.valueOf(bolaQE.getVy()));
			}
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

	@Override
	public int getTamanho() {
		for (int i = 0; i < 4; i++) {
			if (arrJE[i].isFocusOwner()) {
				return arrJE[i].getDiametro();
			}
		}
		return 0;
	}

}
