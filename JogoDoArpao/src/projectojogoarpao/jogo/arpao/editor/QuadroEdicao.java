package projectojogoarpao.jogo.arpao.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.arpao.Fundo;
import projectojogoarpao.jogo.arpao.bd.BDTniveis;
import projectojogoarpao.jogo.arpao.bd.DadosBola;
import projectojogoarpao.jogo.arpao.bd.DadosNivel;
import projectojogoarpao.jogo.arpao.bolas.BolaQE;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;
import projectojogoarpao.jogo.fisica.ColisaoCirculo;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;

public class QuadroEdicao extends JComponent implements
        MouseMotionListener, MouseListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arrastavel aArrastar = null;
    private BolaQE seleccionada = null;
    private TipoAccao accaoActual;
    private AccoesQE accoesQE;
    private Dimension enquadramento;
    private LinkedList<BolaQE> bolas;
    private LinkedList<Pintavel> pintaveis;
    private PersonagemQE personagem;
    private Fundo fundo;

    public QuadroEdicao(Dimension enquadramento) {
        addMouseMotionListener(QuadroEdicao.this);
        addMouseListener(QuadroEdicao.this);
        this.enquadramento = enquadramento;
        setPreferredSize(enquadramento);
        bolas = new LinkedList<BolaQE>();
        pintaveis = new LinkedList<Pintavel>();
    }

    public void salvaNivel(int parseInt) {
        DadosNivel dadosNivel = new DadosNivel();
        Iterator<BolaQE> iteradorBolas;
        BDTniveis bDTniveis;
        dadosNivel.num = accoesQE.getNumNivel();
        dadosNivel.fundo = accoesQE.getFundo();
        dadosNivel.xPersonagem = personagem.getX();
        iteradorBolas = bolas.iterator();
        while (iteradorBolas.hasNext()) {
            BolaQE bolaQE = iteradorBolas.next();
            DadosBola dadosBola = new DadosBola();
            dadosBola.tipo = bolaQE.getTipoBola().getTipoOrd();
            dadosBola.vx = bolaQE.getVx();
            dadosBola.vy = bolaQE.getVy();
            dadosBola.x = bolaQE.getX();
            dadosBola.y = bolaQE.getY();
            dadosNivel.bolas.add(dadosBola);
        }
        bDTniveis = new BDTniveis();
        bDTniveis.criaNivel(dadosNivel);
    }
    /**
     * Iterador reverso da lista pintaveis
     * @return 
     */
    private Iterator<Pintavel> getIRPintaveis() {
        return pintaveis.descendingIterator();
    }

    public void alternaAccao(TipoAccao tipoAccao) {
        accaoActual = tipoAccao;
    }

    public void setSelectorBola(AccoesQE accoesQE) {
        this.accoesQE = accoesQE;
    }

    public void addBola(TipoBola tipoBola, int x, int y) {
        BolaQE bolaArr = new BolaQE(tipoBola);
        int raio = bolaArr.getDiametro() / 2;
        bolaArr.setEnquadramento(enquadramento);
        bolaArr.setX(x - raio);
        bolaArr.setY(y - raio);
        bolas.add(bolaArr);
        pintaveis.add(bolaArr);
        repaint();
    }

    public void carregaNivel(int nivel) {
        BDTniveis bDTniveis = new BDTniveis();
        carregaNivel(bDTniveis.getNivel(nivel));
    }

    private void carregaNivel(DadosNivel nivel) {
        bolas.clear();
        pintaveis.clear();
        for (DadosBola dadosBola : nivel.bolas) {
            BolaQE bolaEdicao = new BolaQE(
                    TipoBola.get(dadosBola.tipo));
            bolaEdicao.setEnquadramento(enquadramento);
            bolaEdicao.setX(dadosBola.x);
            bolaEdicao.setY(dadosBola.y);
            bolaEdicao.setVx(dadosBola.vx);
            bolaEdicao.setVy(dadosBola.vy);
            bolas.add(bolaEdicao);
        }
        fundo = new Fundo(nivel.fundo);
        personagem = new PersonagemQE();
        personagem.setEnquadramento(enquadramento);
        personagem.setX(nivel.xPersonagem);
        pintaveis.add(fundo);
        pintaveis.add(personagem);
        pintaveis.addAll(bolas);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d;

        super.paint(g);
        g2d = (Graphics2D) g;
        for (Pintavel pintavel : pintaveis) {
            if (accaoActual.equals(TipoAccao.seleccao) && seleccionada == pintavel) {
                BolaQE bqe = seleccionada;
                int x = bqe.getX();
                int y = bqe.getY();
                int d = bqe.getDiametro();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Ellipse2D e = new Ellipse2D.Float(x, y, d, d);
                BasicStroke stroke = new BasicStroke(20);
                g2d.setColor(Color.blue);
                g2d.setStroke(stroke);
                g2d.draw(e);
            }
            pintavel.update(g2d, QuadroEdicao.this);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (aArrastar != null) {
            aArrastar.arrasta(e.getX(), e.getY());
            repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        Iterator<Pintavel> irp = getIRPintaveis();
        BolaQE seleccaoAnterior = seleccionada;

        while (irp.hasNext()) {
            Pintavel pintavel = irp.next();
            if (pintavel instanceof BolaQE) {
                BolaQE bqe = (BolaQE) pintavel;
                if (GestorIntercepcoes.pontoDentro(bqe,
                		e.getX(), e.getY())) {
                    seleccionada = bqe;
                    if (seleccionada != seleccaoAnterior) {
                        repaint();
                        accoesQE.setAutorizacaoRemocao(true);
                    }
                    return;
                }
            }
        }
        seleccionada = null;
        if (seleccionada != seleccaoAnterior) {
            repaint();
            accoesQE.setAutorizacaoRemocao(false);
        }
    }

    public void mousePressed(MouseEvent e) {
            Iterator<Pintavel> irp = getIRPintaveis();
            Arrastavel arrastavel;

            while (irp.hasNext()) {
                Pintavel pintavel = irp.next();
                if (pintavel instanceof Arrastavel) {
                    arrastavel = (Arrastavel) pintavel;
                    if (arrastavel.inicioDoArrastamento(e.getX(), e.getY())) {
                        aArrastar = arrastavel;
                        return;
                    }
                }
            }
            aArrastar = null;
    }

    public void mouseReleased(MouseEvent e) {
        if (accoesQE == null || !accaoActual.equals(TipoAccao.insercao)) {
            return;
        }
        TipoBola tipoBola = accoesQE.getTipoBola();
        if (tipoBola != null) {
            addBola(tipoBola, e.getX(), e.getY());
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void removeBolaSeleccionada() {
        BolaQE aRemover = seleccionada;
        pintaveis.remove(aRemover);
        bolas.remove(aRemover);
        repaint();
    }

    public BolaQE getSeleccionada() {
        return seleccionada;
    }

    public void mudaFundo(String fundo) {
        this.fundo.mudaFundo(fundo);
        repaint();
    }
}
