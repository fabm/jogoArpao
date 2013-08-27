package testes;

import java.util.ArrayList;
import java.util.LinkedList;

import projectojogoarpao.invPrimarios.Inteiro;
import projectojogoarpao.jogo.ControlavelPorTeclado;
import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.arpao.Ampulheta;
import projectojogoarpao.jogo.arpao.Fundo;
import projectojogoarpao.jogo.arpao.JogoArpao;
import projectojogoarpao.jogo.arpao.Obstaculo;
import projectojogoarpao.jogo.arpao.TXTInicioNivel;
import projectojogoarpao.jogo.arpao.bolas.BolaQJ;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;
import projectojogoarpao.jogo.arpao.estrelas.Estrela;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;

public class JogoArpaoTestes extends JogoArpao {

	public JogoArpaoTestes() {
		super();
		System.out.println("Em modo de teste");
	}

	LinkedList<Estrela> estrelas;

	public boolean carregarNivel() {
		estrelas = new LinkedList<>();
		tempoNivel.setValor(0);
		LinkedList<ControlavelPorTeclado> lControlaveisPorTeclado = new LinkedList<ControlavelPorTeclado>();
		LinkedList<Pintavel> lPintaveis = new LinkedList<Pintavel>();
		
		emMov.setValor(false);

		fundo = new Fundo("fundo-colorido-para-fotos-3.jpg");

		mg.setX(300);

		mg.setY(enquadramento.height - 34);

		personagem.iniciar();
		arpao.iniciar();

		Estrela estrela = new Estrela(80);
		estrela.setEmMov(emMov);
		estrela.setX(200);
		estrela.setY(300);
		estrela.setEnquadramento(enquadramento);
		estrelas.add(estrela);

		estrela = new Estrela(80);
		estrela.setEmMov(emMov);
		estrela.setX(100);
		estrela.setY(100);
		estrela.setEnquadramento(enquadramento);
		
		estrelas.add(estrela);
		bolas.clear();
		final Ampulheta ampulheta = new Ampulheta();
		
		// BolaMov bola = Bola.criaBola(dBola.tipo).criaBolaMovivel();
		BolaQJ bola = new BolaQJ(TipoBola.media);
		bola.setEmMov(emMov);
		bola.setEnquadramento(enquadramento);
		bola.setX(100);
		bola.setY(500);
		bola.setVx(1);
		bola.setVy(10);
		bolas.add(bola);

		lPintaveis.add(fundo);
		lPintaveis.addAll(obstaculos);
		lPintaveis.add(arpao);
		lPintaveis.add(personagem);
		lPintaveis.addAll(bolas);
		lPintaveis.add(txtVidas);
		//lPintaveis.add(txtTempoNivel);
		lPintaveis.add(txtPontos);
		lPintaveis.add(ampulheta);
		lPintaveis.addAll(estrelas);

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
				ampulheta.iniciar(60);
				@SuppressWarnings("unchecked")
				LinkedList<Pintavel> lPintaveis = (LinkedList<Pintavel>) pintaveis
						.clone();
				lPintaveis.remove(indicadorNivel);
				pintaveis = lPintaveis;
				iniciarTemporizadorNivel(60);
			}
		});

		controlaveisPorTeclado = lControlaveisPorTeclado;
		pintaveis = lPintaveis;
		
		temporizador.start();
		tempoNivel.setValor(60);
		return true;
	}

	@Override
	public int getFPS() {
		return 60;
	}

	@Override
	public void preProcessarJogo() {
		super.preProcessarJogo();

		for (Obstaculo obstaculo : obstaculos) {
			for (Estrela estrela : estrelas) {
				GestorIntercepcoes.atualizaColisao(estrela, obstaculo);
			}
			if (arpao.activo()) {
				GestorIntercepcoes.atualizaColisao(arpao, obstaculo);
				
				//ignorar colisao na base do arpao em contacto com um obctaculo
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

	@Override
	public boolean processarJogo() {

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

		return super.processarJogo();
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
}
