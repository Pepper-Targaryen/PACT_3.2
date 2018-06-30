import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class StructureInitialization {
	
	NavGraph ng;
	
	
	public final void initFromTextGraph(String fileName) {
    	InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream("GraphSave.txt");
            objIs = new ObjectInputStream(fileIs);
            NavGraph ng = (NavGraph) objIs.readObject();
            this.ng=ng;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){
                 
            }
        }
            	
    }
    
    public final void saveToTextGraph(String fileName) {
    	OutputStream ops = null;
        ObjectOutputStream objOps = null;
        try {
            ops = new FileOutputStream("GraphSave.txt");
            objOps = new ObjectOutputStream(ops);
            objOps.writeObject(this.ng);
            objOps.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(objOps != null) objOps.close();
            } catch (Exception ex){
                 
            }
        }
    }

}
