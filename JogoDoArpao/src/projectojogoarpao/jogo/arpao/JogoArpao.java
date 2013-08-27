/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import projectojogoarpao.invPrimarios.Booleano;
import projectojogoarpao.invPrimarios.CadeiaCaracteres;
import projectojogoarpao.invPrimarios.Inteiro;
import projectojogoarpao.jogo.ControlavelPorTeclado;
import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.ProcessadorJogo;
import projectojogoarpao.jogo.TXTPaint;
import projectojogoarpao.jogo.TXTPaintComSobra;
import projectojogoarpao.jogo.arpao.bd.BDTniveis;
import projectojogoarpao.jogo.arpao.bd.BDTpontuacoes;
import projectojogoarpao.jogo.arpao.bd.DadosBola;
import projectojogoarpao.jogo.arpao.bd.DadosEstrela;
import projectojogoarpao.jogo.arpao.bd.DadosNivel;
import projectojogoarpao.jogo.arpao.bolas.BolaQJ;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;
import projectojogoarpao.jogo.arpao.estrelas.Estrela;
import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;

/**
 * 
 * @author francisco
 */
public class JogoArpao implements ProcessadorJogo, ControlavelPorTeclado {

	protected LinkedList<Pintavel> pintaveis = null;
	protected LinkedList<ControlavelPorTeclado> controlaveisPorTeclado = null;
	protected Arpao arpao;
	protected Fundo fundo;
	protected Personagem personagem;
	protected Dimension enquadramento;
	protected TXTPaint txtVidas;
	protected TXTPaint txtPontos;
	protected Inteiro tempoNivel;
	protected TXTInicioNivel indicadorNivel;
	protected Booleano emMov;
	protected Inteiro vidas;
	protected int numNivel;
	protected LinkedList<BolaQJ> bolas;
	protected LinkedList<Estrela> estrelas;
	protected Booleano continuarAnimacao;
	protected Timer temporizador = null;
	protected int pontos;
	protected TXTPaint pausa = null;
	protected final MovimentoGravitico mg;
	protected final LinkedList<Obstaculo> obstaculos;
	protected Ampulheta ampulheta;

	public JogoArpao() {
		continuarAnimacao = new Booleano(true);
		emMov = new Booleano(false);
		enquadramento = new Dimension(800, 600);
		mg = new MovimentoGravitico();
		mg.setXmin(0);
		mg.setYmax(0);
		mg.setXmax(enquadramento.width);
		mg.setYmax(enquadramento.height);
		vidas = new Inteiro(3);
		tempoNivel = new Inteiro(0);
		pontos = 0;

		ampulheta = new Ampulheta();

		personagem = new Personagem();
		personagem.setMg(mg);
		personagem.setMovivel(emMov);

		arpao = new Arpao(emMov);
		arpao.setMG(mg);

		bolas = new LinkedList<BolaQJ>();
		estrelas = new LinkedList<>();

		obstaculos = new LinkedList<Obstaculo>();

		obstaculos.add(Obstaculo.padrao());

		txtVidas = new TXTPaint(criaFonte(Font.PLAIN, 18),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Vidas " + vidas.getValor();
					}
				});
		txtVidas.setX(10);
		txtVidas.setY(30);
		txtVidas.setVisivel(true);

		txtPontos = new TXTPaint(criaFonte(Font.PLAIN, 15),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Pontos:" + pontos;
					}
				});
		txtPontos.setX(700);
		txtPontos.setY(30);
		txtPontos.setVisivel(true);
	}

	public int getFPS() {
		return 60;
	}

	public void defEnqPintaveis() {
		for (Pintavel pintavel : pintaveis) {
			pintavel.setEnquadramento(enquadramento);
		}
	}

	public static Font criaFonte(int tipo, int tamanho) {
		return new Font("Comic Sans MS", tipo, tamanho);
	}

	public Colisao criaLimitesDeColisao(Personagem p) {
		return criaLimitesDeColisao(p.getX(), p.getY(), p.getAltura(),
				p.getLargura(), p.getColisao());
	}

	public Colisao criaLimitesDeColisao(Obstaculo o) {
		return criaLimitesDeColisao(o.getX(), o.getY(), o.getLargura(),
				o.getAltura(), o.getColisao());
	}

	public Colisao criaLimitesDeColisao(Circulo c) {
		return criaLimitesDeColisao(c.getX(), c.getY(), c.getDiametro(),
				c.getDiametro(), c.getColisao());
	}

	public Colisao criaLimitesDeColisao(int x, int y, int altura, int largura,
			Colisao colisao) {
		if (x <= 0) {
			x = 0;
			colisao.esquerda = true;
		}
		if (x >= 800 - largura) {
			x = 800 - largura;
			colisao.direita = true;
		}
		if (y <= 0) {
			y = 0;
			colisao.topo = true;
		}
		if (y >= 600 - altura) {
			y = 600 - altura;
			colisao.base = true;
		}
		return colisao;
	}

	public boolean carregarNivel() {
		tempoNivel.setValor(0);
		LinkedList<ControlavelPorTeclado> lControlaveisPorTeclado = new LinkedList<ControlavelPorTeclado>();
		LinkedList<Pintavel> lPintaveis = new LinkedList<Pintavel>();

		emMov.setValor(false);
		BDTniveis bdtNiveis = new BDTniveis();
		DadosNivel dadosNivel = bdtNiveis.getNivel(numNivel);
		bdtNiveis.fecha();

		if (dadosNivel == null) {
			return false;
		}
		dadosNivel.num = numNivel;

		fundo = new Fundo(dadosNivel.fundo);

		mg.setX(dadosNivel.xPersonagem);

		mg.setX(dadosNivel.xPersonagem);
		mg.setY(dadosNivel.yPersonagem);

		personagem.iniciar();
		arpao.iniciar();

		bolas.clear();
		estrelas.clear();
		for (DadosBola dBola : dadosNivel.bolas) {
			// BolaMov bola = Bola.criaBola(dBola.tipo).criaBolaMovivel();
			BolaQJ bola = new BolaQJ(TipoBola.get(dBola.tipo));
			bola.setEmMov(emMov);
			bola.setEnquadramento(enquadramento);
			bola.setX(dBola.x);
			bola.setY(dBola.y);
			bola.setVx(dBola.vx);
			bola.setVy(dBola.vy);
			bolas.add(bola);
		}

		for (DadosEstrela dEstrela : dadosNivel.estrelas) {
			// BolaMov bola = Bola.criaBola(dBola.tipo).criaBolaMovivel();
			Estrela estrela = new Estrela(dEstrela.tamanho);
			estrela.setEmMov(emMov);
			estrela.setEnquadramento(enquadramento);
			estrela.setX(dEstrela.x);
			estrela.setY(dEstrela.y);
			estrelas.add(estrela);
		}

		lPintaveis.add(fundo);
		lPintaveis.addAll(obstaculos);
		lPintaveis.add(arpao);
		lPintaveis.add(personagem);
		lPintaveis.addAll(bolas);
		lPintaveis.addAll(estrelas);
		lPintaveis.add(txtVidas);
		lPintaveis.add(ampulheta);
		lPintaveis.add(txtPontos);

		for (Pintavel pintavel : lPintaveis) {
			pintavel.setEnquadramento(enquadramento);
		}

		lControlaveisPorTeclado.add(personagem);
		lControlaveisPorTeclado.add(arpao);
		lControlaveisPorTeclado.add(this);

		indicadorNivel = new TXTInicioNivel(numNivel);
		indicadorNivel.setEnquadramento(enquadramento);
		lPintaveis.add(indicadorNivel);
		temporizar(new Inteiro(3), new Runnable() {
			public void run() {
				emMov.setValor(true);
				@SuppressWarnings("unchecked")
				LinkedList<Pintavel> lPintaveis = (LinkedList<Pintavel>) pintaveis
						.clone();
				lPintaveis.remove(indicadorNivel);
				pintaveis = lPintaveis;
				iniciarTemporizadorNivel(60);
				ampulheta.iniciar(60);
			}
		});
		controlaveisPorTeclado = lControlaveisPorTeclado;
		pintaveis = lPintaveis;
		temporizador.start();
		return true;
	}

	protected void iniciarTemporizadorNivel(int tempo) {
		tempoNivel.setValor(tempo);

		temporizar(tempoNivel, new Runnable() {
			public void run() {
				tempoEsgotado();
			}
		});
	}

	public void iniciarJogo() {
		numNivel = 1;
		pontos = 0;
		vidas.setValor(3);
		continuarAnimacao.setValor(true);

		if (temporizador != null) {
			temporizador.stop();
		}
		carregarNivel();
	}

	public boolean processarJogo() {
		if (!continuarAnimacao.activo()) {
			return false;
		}

		/**
		 * Colisões
		 */

		for (Estrela estrela : estrelas) {
			estrela.getColisao().reeniciar();
		}

		GestorIntercepcoes<Estrela> giEstrelas = new GestorIntercepcoes<Estrela>(
				estrelas);

		if (arpao.activo() && giEstrelas.interceptadoComCirculos(arpao)) {
			arpao.iniciar();
			rebentaEstrela((Estrela) giEstrelas.circuloInterceptado);
			pontos += 2;
		}

		if (!personagem.ressucitar()
				&& giEstrelas.interceptadoComCirculos(personagem)) {
			morre();
			if (vidas.getValor() > 0) {
				rebentaEstrela((Estrela) giEstrelas.circuloInterceptado);
			}
		}
		for (BolaQJ bola : bolas) {
			bola.getColisao().reeniciar();
		}
		GestorIntercepcoes<BolaQJ> gi = new GestorIntercepcoes<BolaQJ>(bolas);

		gi.setRectangulos(obstaculos);
		if (!personagem.ressucitar() && gi.interceptadoComCirculos(personagem)) {
			morre();
			if (vidas.getValor() > 0) {
				rebentaBola((BolaQJ) gi.circuloInterceptado);
			}
		}

		if (arpao.activo() && gi.interceptadoComCirculos(arpao)) {
			arpao.iniciar();
			rebentaBola((BolaQJ) gi.circuloInterceptado);
			pontos += 2;
		}

		// reeniciar colisoes

		personagem.getColisao().reeniciar();
		for (Obstaculo obstaculo : obstaculos) {
			obstaculo.getColisao().reeniciar();
		}
		for (BolaQJ bola : bolas) {
			bola.getColisao().reeniciar();
		}
		return true;

	}

	private void rebentaEstrela(Estrela estrela) {

		ArrayList<Estrela> estrelasARemover = null;
		ArrayList<Estrela> estrelasAAdicionar = null;

		if (estrelasARemover == null) {
			estrelasARemover = new ArrayList<>();
		}
		estrelasARemover.add(estrela);
		int diametroAtual = estrela.getDiametro();
		if (estrela.getDiametro() > 20) {
			if (estrelasAAdicionar == null) {
				estrelasAAdicionar = new ArrayList<>();
			}
			estrelasAAdicionar
					.add(estrela.criaMenor(diametroAtual - 20, false));
			estrelasAAdicionar.add(estrela.criaMenor(diametroAtual - 20, true));
		}

		if (estrelasARemover != null) {
			for (Estrela estrelaARemover : estrelasARemover) {
				estrelas.remove(estrelaARemover);
				pintaveis.remove(estrelaARemover);

			}
		}
		if (estrelasAAdicionar != null) {
			for (Estrela estrelaAAdicionar : estrelasAAdicionar) {
				estrelas.add(estrelaAAdicionar);
				pintaveis.add(estrelaAAdicionar);
			}
		}
		if (estrelas.isEmpty() && bolas.isEmpty())
			fimNivel();
	}

	protected void rebentaBola(BolaQJ bola) {
		bolas.remove(bola);
		pintaveis.remove(bola);
		adicBolasRebentamento(bola);
		if (bolas.isEmpty() && estrelas.isEmpty()) {
			fimNivel();
		}
	}

	protected void temporizar(final Inteiro segundos, final Runnable evento) {
		temporizador = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				segundos.inc(-1);
				if (segundos.getValor() == 0) {
					temporizador.stop();
					temporizador = null;
					evento.run();
				}
			}
		});
		temporizador.start();
	}

	private void tempoEsgotado() {
		vidas.inc(-1);
		if (vidas.getValor() == 0) {
			fimDoJogo();
			return;
		}

		TXTPaint tempoEsgotado = new TXTPaint(criaFonte(Font.PLAIN, 40),
				"Tempo esgotado");
		tempoEsgotado.setX(250);
		tempoEsgotado.setY(300);

		tempoEsgotado.setVisivel(true);

		pintaveis.clear();
		pintaveis.add(fundo);
		pintaveis.add(tempoEsgotado);

		temporizar(new Inteiro(3), new Runnable() {
			public void run() {
				carregarNivel();
			}
		});
	}

	protected void fimNivel() {
		pontos += tempoNivel.getValor() * 10;
		tempoNivel.setValor(0);
		if (temporizador != null) {
			temporizador.stop();
			temporizador = null;
		}
		TXTPaint txtFimNivel = new TXTPaint(criaFonte(Font.BOLD, 40),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Nivel " + numNivel + " completo!";
					}
				});
		txtFimNivel.setVisivel(true);
		pintaveis.clear();
		pintaveis.add(fundo);
		pintaveis.add(txtFimNivel);
		numNivel++;
		if (!carregarNivel()) {
			fimDoJogo();
		}
	}

	protected void adicBolasRebentamento(BolaQJ bola) {
		BolaQJ nova = bola.criaBolaAbaixo(false);
		if (nova == null) {
			return;
		}
		bolas.add(nova);
		pintaveis.add(nova);
		nova = bola.criaBolaAbaixo(true);
		bolas.add(nova);
		pintaveis.add(nova);
	}

	protected void morre() {
		personagem.atingido();
		vidas.inc(-1);
		if (vidas.getValor() == 0) {
			fimDoJogo();
		}
	}

	private void fimDoJogo() {
		if (temporizador != null) {
			temporizador.stop();
			temporizador = null;
		}

		TXTPaintComSobra txtFimJogo = new TXTPaintComSobra(criaFonte(Font.BOLD,
				40), "Fim do jogo");
		LinkedList<Pintavel> lPintaveis = new LinkedList<Pintavel>();
		lPintaveis.add(fundo);
		lPintaveis.add(txtFimJogo);
		txtFimJogo.setVisivel(true);
		temporizar(new Inteiro(3), new Runnable() {
			public void run() {
				continuarAnimacao.setValor(false);
				String nome = null;
				nome = JOptionPane.showInputDialog("Introduz o nome");
				if (nome != null) {
					BDTpontuacoes tPontuacoes = new BDTpontuacoes();
					try {
						tPontuacoes.inserirUtilizador(nome, pontos);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Erro SQL", JOptionPane.ERROR_MESSAGE);
					} finally {
						tPontuacoes.fecha();
					}
				}
			}
		});
		pintaveis = lPintaveis;
	}

	public Collection<Pintavel> getColPintaveis() {
		return pintaveis;
	}

	public Collection<ControlavelPorTeclado> getColContTeclado() {
		return controlaveisPorTeclado;
	}

	public void teclaPressionada(KeyEvent ke) {
		if (pausa == null) {
			pausa = new TXTPaint(criaFonte(Font.PLAIN, 40), "Pausado!");
			pausa.setX(300);
			pausa.setY(300);
			pausa.setVisivel(false);
			pintaveis.add(pausa);
		}
		if (ke.getKeyCode() == KeyEvent.VK_P) {
			if (emMov.activo()) {
				temporizador.stop();
				emMov.setValor(false);
				pausa.setVisivel(true);
			} else {
				temporizador.start();
				emMov.setValor(true);
				pausa.setVisivel(false);
			}
		}
	}

	public void teclaLargada(KeyEvent ke) {
	}

	@Override
	public void preProcessarJogo() {
		/**
		 * Limites
		 */

		for (Obstaculo obstaculo : obstaculos) {
			GestorIntercepcoes.atualizaColisao(personagem, obstaculo);
			if (personagem.getColisao().base && personagem.mg.getVy() >= 0) {
				personagem.mg.setY(obstaculo.getY() - personagem.getAltura());
				personagem.mg.setVy(0);
				personagem.setEmSalto(false);
			}
			if (personagem.getColisao().topo) {
				personagem.mg.setY(obstaculo.getY() + obstaculo.getAltura());
				personagem.mg.setVy(1);
			}
			if (personagem.getColisao().esquerda) {
				personagem.mg.setX(obstaculo.getX() + obstaculo.getLargura());
			}
			if (personagem.getColisao().direita) {
				personagem.mg.setX(obstaculo.getX() - personagem.getLargura());
			}
			personagem.getColisao().reeniciar();

			for (BolaQJ bola : bolas) {
				GestorIntercepcoes.atualizaColisao(bola, obstaculo);
				if (bola.getColisao().topo) {
					bola.setVy(-bola.getVy());
					bola.setY(obstaculo.getY() + obstaculo.getAltura());
					bola.getColisao().reeniciar();
				}
				if (bola.getColisao().base) {
					bola.setVy(-bola.getVy());
					bola.setY(obstaculo.getY() - bola.getDiametro());
					bola.getColisao().reeniciar();
				}
			}
		}

		criaLimitesDeColisao(personagem);

		for (BolaQJ bola : bolas) {
			criaLimitesDeColisao(bola);
			if (bola.getColisao().base) {
				bola.decvyAC();
			}
		}

		for (Obstaculo obstaculo : obstaculos) {
			for (Estrela estrela : estrelas) {
				GestorIntercepcoes.atualizaColisao(estrela, obstaculo);
			}
			if (arpao.activo()) {
				GestorIntercepcoes.atualizaColisao(arpao, obstaculo);

				// ignorar colisao na base do arpao em contacto com um obctaculo
				arpao.getColisao().base = false;
				if (arpao.getColisao().tem()) {
					arpao.iniciar();
					arpao.getColisao().reeniciar();
				}
			}
		}

		for (Estrela estrela : estrelas) {
			criaLimitesDeColisao(estrela);
		}
	}

}
