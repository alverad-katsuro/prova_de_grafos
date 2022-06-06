package grafos.requisitos;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import de.unknownreality.dataframe.DataFrame;
import de.unknownreality.dataframe.DataFrameColumn;
import de.unknownreality.dataframe.DataRow;
import de.unknownreality.dataframe.DataRows;
import de.unknownreality.dataframe.common.Row;
import de.unknownreality.dataframe.io.FileFormat;
import grafos.struct.Vertice;

public class Teste {

  public static void main(String[] args) {
    DataFrame dataframe = DataFrame.create().addStringColumn("origem")
                                          .addStringColumn("destino")
                                          .addStringColumn("label");

    Vertice origem = new Vertice("R0");
    Vertice origem2 = new Vertice("R1");
    Vertice origem3 = new Vertice("R2");
    dataframe.append(origem.toString(), null, null);
    dataframe.append(origem2.toString(), null, null);

    DataFrame colum = dataframe.select(String.format("(origem != '%s' || destino != '%s')", origem.toString(), origem.toString()));
    for (DataRow c : dataframe) {
      System.out.println(c.getString("destino") == "NA");
    }
    /*
    dataframe.print();
    File csv = new File("src/main/java/grafos/requisitos/te.csv");
    DataFrame dataFrame = DataFrame.fromCSV(csv, ';', true);
    DataRow a = dataFrame.selectFirst("(name == 'Alfredo')");
    a.set("age", 21);
    a.set("country", "Brasil");
    dataFrame.update(a);
    dataFrame.print();
    //dataFrame.print();
      
    dataFrame.setPrimaryKey("UID");
    dataFrame.addIndex("id_name_idx","ID","NAME");
    
    DataRow row = dataFrame.selectByPrimaryKey(1);
    System.out.println(row);

    
    DataFrame idxExample = dataFrame.selectByIndex("id_name_idx",3,"A");
    
    idxExample.print();

    idxExample.getStringColumn("NAME").map((value -> value + "_idx_example"));
    idxExample.print();

    
    dataFrame.joinInner(idxExample,"UID").print();

    */
  }
  
}

