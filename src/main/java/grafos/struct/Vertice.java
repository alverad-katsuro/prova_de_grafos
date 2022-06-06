package grafos.struct;

import java.util.ArrayList;

public class Vertice {
  protected float x = 0f;
  protected float y = 0f;
  protected String nome;
  protected ArrayList<Aresta> arestas = new ArrayList<Aresta>();


  public Vertice(String nome){
    setNome(nome);
    setX(0);
    setY(0);
  }

  public Vertice(Float x, Float y){
    this.arestas = new ArrayList<Aresta>();
    setX(x);
    setY(y);
  }

  protected void createAresta(Vertice destino){
    Aresta aresta = new Aresta(this, destino);
    addAresta(aresta);
  }
  
  @Override
  public String toString() {
    return getNome();
  }
  
  private void addAresta(Aresta aresta){
    this.arestas.add(aresta);
  }

  public float getX() {
    return x;
  }
  public float getY() {
    return y;
  }
  public String getNome() {
    return nome;
  }
  public void setX(float x) {
    this.x = x;
  }
  public void setY(float y) {
    this.y = y;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
}
