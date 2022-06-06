package grafos.struct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.unknownreality.dataframe.DataFrame;
import de.unknownreality.dataframe.DataRow;
import grafos.exception.VerticeExistenteException;
import grafos.exception.VerticeInexistenteException;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;


public class Grafo {
  private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
  public Boolean digrafo = false;

  DataFrame dataframe = DataFrame.create().addStringColumn("origem")
                                          .addStringColumn("destino")
                                          .addStringColumn("label");

  public static void main(String[] args) {
    Grafo grafo = new Grafo();
    grafo.createAresta("R0", "R1", "5");
    grafo.getDataframe().print();
    try {
      grafo.createImg();
    } catch (VerticeInexistenteException e) {
      e.printStackTrace();
    }
  }

  public Boolean createAresta(String origem_nome, String destino_nome){
    Vertice origem = searchVertice(origem_nome);
    Vertice destino = searchVertice(destino_nome);
    if (origem != null && destino != null){
      origem.createAresta(destino);
      getDataframe().append(origem_nome, destino_nome, null);
      return true;
    } else {
      return false;
    }
  }

  public Boolean createAresta(String origem_nome, String destino_nome, String label){
    Vertice origem = searchVertice(origem_nome);
    Vertice destino = searchVertice(destino_nome);
    if (origem != null && destino != null){
      origem.createAresta(destino);
      addVertice(origem);
      addVertice(destino);
      getDataframe().append(origem_nome, destino_nome, label);
      return true;
    } else {
      return false;
    }
  }

  private Vertice searchVertice(String nome){
    Vertice vertice;
    DataFrame data = getDataframe().select(String.format("(origem == '%s' || destino == '%s')", nome, nome));
    if (data.isEmpty()){
      vertice = new Vertice(nome);      
      return vertice;
    } else {
      for (Vertice verti : getVertices()) {
        if (verti.toString() == nome){
          vertice = verti;
          return vertice;
        }
      }
    }
    return null;
  }
  

  public Boolean createAresta(Vertice origem, Vertice destino){
    DataFrame data = getDataframe().select(String.format("(origem == '%s' && destino == '%s')", origem.toString(), destino.toString()));
    if (data.isEmpty()){
      origem.createAresta(destino);
      getDataframe().append(origem.toString(), destino.toString());
      return true;
    } else {
      return false;
    }
  }

  public Boolean createAresta(Vertice origem, Vertice destino, String label){
    DataFrame data = getDataframe().select(String.format("(origem == '%s' && destino == '%s')", origem.toString(), destino.toString()));
    if (data.isEmpty()){
      origem.createAresta(destino);
      getDataframe().append(origem.toString(), destino.toString(), label);
      return true;
    } else if (data.getRow(0).getString("label") != label) {
      data = getDataframe().select(String.format("!(origem == '%s' && destino == '%s')", origem.toString(), destino.toString()));
      data.append(origem.toString(), destino.toString(), label);
      setDataframe(data);
      System.out.println("Label update!");
      return true;
    } else {
      System.out.println("Aresta já existente com a msm Label");
      return false;
    }
  }

  public Vertice createVertice(String nome){
    for (Vertice vertice : getVertices()){
      if (vertice.getNome() == nome) {
        try {
          throw new VerticeExistenteException();
        } catch (VerticeExistenteException e) {
          e.printStackTrace();
        }
      }
    }
    Vertice vertice = new Vertice(nome);
    addVertice(vertice);
    getDataframe().append(vertice.getNome(), null, null);
    return vertice;
  }

  public Boolean deleteVertice(String nome){
    for (Vertice vertice : getVertices()){
      if (vertice.getNome() == nome) {
        Vertice achado = vertice;
        getVertices().remove(achado);
        setDataframe(getDataframe().select(String.format("(origem != '%s' || destino != '%s')", vertice.toString(), vertice.toString())));
        return true;
      }
    }
    System.out.println("Vertice não encontrado");
    return false;
  }


  /*
  public void createImg(){
    Node
    main = node("main").with(Label.of("AAAAA"), Color.rgb("1020d0").font()),
    init = node(Label.markdown("**_init_**")),
    execute = node("execute"),
    compare = node("compare").with(Shape.RECTANGLE, Style.FILLED, Color.hsv(.7, .3, 1.0)),
    mkString = node("mkString").with(Label.lines(Justification.LEFT, "make", "a", "multi-line")),
    printf = node("printf");
    
    for (DataRow row : getDataframe()){
      node
      main.link(
        to(node("parse").link(execute)).with(LinkAttr.weight(8)),
        to(init).with(Style.DOTTED),
        node("cleanup"),
        to(printf).with(Style.BOLD, Label.of("100 times"), Color.RED)),
        );
      };
    }
    */

  public void createImg() throws VerticeInexistenteException{
    if (!getVertices().isEmpty()) {  
      Graph g;
      if (getDigrafo()) {
        g = graph("example2").directed();
      } else {
        g = graph("example2");
      }
      for (DataRow coluna : getDataframe()) {
        if (coluna.getString("origem") != "NA"){
          String origem = coluna.getString("origem");
          if (coluna.getString("destino") != "NA"){
            String destino = coluna.getString("destino");
            if (coluna.getString("label") != "NA"){
              String label = coluna.getString("label");
              node(origem);
              g = g.with(node(origem).with(Color.BLACK, Shape.CIRCLE).link(node(destino)).with(Label.of(label)));
            }
            g = g.with(node(origem).with(Color.BLACK, Shape.CIRCLE).link(node(destino)));
          }
          g = g.with(node(origem));
        }
      };
      try {
        Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("teste.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new VerticeInexistenteException();
    }
  }

  public void printDataFrame(){
    getDataframe().print();
  }

  private DataFrame getDataframe() {
    return dataframe;
  }

  private void setDataframe(DataFrame dataframe) {
    this.dataframe = dataframe;
  }

  private void addVertice(Vertice vertice){
    getVertices().add(vertice);
  } 

  private ArrayList<Vertice> getVertices() {
    return vertices;
  }

  public void setDigrafo(Boolean digrafo) {
    this.digrafo = digrafo;
  }

  public Boolean getDigrafo() {
    return digrafo;
  }
}
