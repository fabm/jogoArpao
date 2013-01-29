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
import projectojogoarpao.jogo.arpao.bd.DadosNivel;
import projectojogoarpao.jogo.arpao.bolas.BolaQJ;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;
import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;
import testes.Debugger;

/**
 * 
 * @author francisco
 */
public class JogoArpao implements ProcessadorJogo, ControlavelPorTeclado {

	private LinkedList<Pintavel> pintaveis = null;
	private LinkedList<ControlavelPorTeclado> controlaveisPorTeclado = null;
	private Arpao arpao;
	private Fundo fundo;
	private Personagem personagem;
	private Dimension enquadramento;
	private TXTPaint txtVidas;
	private TXTPaint txtTempoNivel;
	private TXTPaint txtPontos;
	private Inteiro tempoNivel;
	private TXTInicioNivel indicadorNivel;
	private Booleano emMov;
	private Inteiro vidas;
	private int numNivel;
	private LinkedList<BolaQJ> bolas;
	private Booleano continuarAnimacao;
	private Timer temporizador = null;
	private int pontos;
	private TXTPaint pausa = null;
	private final MovimentoGravitico mg;
	private final LinkedList<Obstaculo> obstaculos;
	private final Estrela estrela;

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

		estrela = new Estrela();
		
		personagem = new Personagem();
		personagem.setMg(mg);
		personagem.setMovivel(emMov);

		arpao = new Arpao(emMov);
		arpao.setMG(mg);
		
		

		bolas = new LinkedList<BolaQJ>();

		obstaculos = new LinkedList<Obstaculo>();

		obstaculos.add(new Obstaculo(200, 20, 100, 400));

		txtVidas = new TXTPaint(criaFonte(Font.PLAIN, 18),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Vidas " + vidas.getValor();
					}
				});
		txtVidas.setX(10);
		txtVidas.setY(20);
		txtVidas.setVisivel(true);

		txtTempoNivel = new TXTPaint(criaFonte(Font.PLAIN, 15),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Tempo:" + tempoNivel.getValor();
					}
				});
		txtTempoNivel.setX(700);
		txtTempoNivel.setY(20);
		txtTempoNivel.setVisivel(true);

		txtPontos = new TXTPaint(criaFonte(Font.PLAIN, 15),
				new CadeiaCaracteres() {
					public String getCadeia() {
						return "Pontos:" + pontos;
					}
				});
		txtPontos.setX(700);
		txtPontos.setY(40);
		txtPontos.setVisivel(true);
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
		mg.setY(enquadramento.height - 34);

		personagem.iniciar();
		arpao.iniciar();

		estrela.setEmMov(emMov);
		estrela.setEnquadramento(enquadramento);
		bolas.clear();
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

		lPintaveis.add(fundo);
		lPintaveis.addAll(obstaculos);
		lPintaveis.add(arpao);
		lPintaveis.add(personagem);
		lPintaveis.addAll(bolas);
		lPintaveis.add(txtVidas);
		lPintaveis.add(txtTempoNivel);
		lPintaveis.add(txtPontos);
		lPintaveis.add(estrela);

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
				iniciarTemporizadorNivel();
			}
		});

		controlaveisPorTeclado = lControlaveisPorTeclado;
		pintaveis = lPintaveis;
		temporizador.start();
		return true;
	}

	private void iniciarTemporizadorNivel() {
		tempoNivel.setValor(60);

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
		estrela.getColisao().reeniciar();
		return true;

	}

	private void rebentaBola(BolaQJ bola) {
		bolas.remove(bola);
		pintaveis.remove(bola);
		if (!adicBolasRebentamento(bola) && bolas.isEmpty()) {
			fimNivel();
		}
	}

	private void temporizar(final Inteiro segundos, final Runnable evento) {
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

	private void fimNivel() {
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

	private boolean adicBolasRebentamento(BolaQJ bola) {
		TipoBola tipoAnterior = bola.getTipoBola().anterior();
		BolaQJ bolaAnterior;
		if (tipoAnterior == null) {
			return false;
		}
		bolaAnterior = new BolaQJ(tipoAnterior);
		BolaQJ bolaCriada = new BolaQJ(tipoAnterior);
		bolaCriada.setX(bola.getX());
		bolaCriada.setY(bola.getY());
		bolaCriada.setVy(-bolaAnterior.getVyAR());
		bolaCriada.setEnquadramento(enquadramento);
		bolaCriada.setVx(-1);
		bolaCriada.setEmMov(emMov);

		bolas.add(bolaCriada);
		pintaveis.add(bolaCriada);
		// bola direita
		bolaCriada = new BolaQJ(tipoAnterior);
		bolaCriada.setX(bola.getX());
		bolaCriada.setY(bola.getY());
		bolaCriada.setVy(-bolaAnterior.getVyAR());
		bolaCriada.setEnquadramento(enquadramento);
		bolaCriada.setVx(1);
		bolaCriada.setEmMov(emMov);

		bolas.add(bolaCriada);
		pintaveis.add(bolaCriada);
		return true;
	}

	private void morre() {
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
				while (nome == null || nome.isEmpty()) {
					nome = JOptionPane.showInputDialog("Introduz o nome");
				}
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
			if (personagem.getColisao().base) {
				personagem.mg.setY(obstaculo.getY()-personagem.getAltura());
				personagem.mg.setVy(0);
				Debugger.get().printf("%d \n",personagem.mg.getY());
			}
			if (personagem.getColisao().topo) {
				personagem.mg.setY(obstaculo.getY()+obstaculo.getAltura()); 
			}
			if (personagem.getColisao().esquerda) {
				personagem.mg.setX(obstaculo.getX()+obstaculo.getLargura()); 
			}
			if (personagem.getColisao().direita) {
				personagem.mg.setX(obstaculo.getX()-personagem.getLargura()); 
			}
			personagem.getColisao().reeniciar();
			
			for (BolaQJ bola: bolas) {
				GestorIntercepcoes.atualizaColisao(bola, obstaculo);
				Debugger.get().setDentro(true);
				if (bola.getColisao().topo) {
					bola.setVy(-25);
					bola.setY(obstaculo.getY()-bola.getDiametro());
					bola.getColisao().reeniciar();
				}
				if (bola.getColisao().base) {
					bola.setVy(-bola.getVy());
					bola.setY(obstaculo.getY()+obstaculo.getAltura());
					bola.getColisao().reeniciar();
				}
			}
		}

		criaLimitesDeColisao(personagem);
		for (BolaQJ bola : bolas) {
			criaLimitesDeColisao(bola);
		}
		criaLimitesDeColisao(estrela);
	}
}
