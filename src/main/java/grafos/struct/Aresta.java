package grafos.struct;

public class Aresta {
  public Vertice origem;  
  public Vertice destino;

  Aresta(Vertice origem, Vertice destino){
    setDestino(destino);
    setOrigem(origem);
  }

  public Vertice getOrigem() {
    return origem;
  }
  public Vertice getDestino() {
    return destino;
  }
  public void setOrigem(Vertice origem) {
    this.origem = origem;
  }
  public void setDestino(Vertice destino) {
    this.destino = destino;
  }
}
