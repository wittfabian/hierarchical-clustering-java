package process;

import algorithm.Cluster;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteLabel {
    
    private List<Cluster> solution;
    private final List<Integer[]> datapoints;
    private final Integer count_solution_clusters;
    
    public WriteLabel(Cluster cluster, Integer count){
        count_solution_clusters = count;
        solution = cluster.getChildren();
        solution = getSolutionClusters(solution);
        datapoints = new ArrayList<>();
        getDatapoints();
        
    }
    
    private List<Cluster> getSolutionClusters(List<Cluster> children){
        if( count_solution_clusters == children.size() ){
            return children;
        }
        else{
            Double maxdist = 0.0;
            Cluster next = null;
            for( Cluster child : children ){
                if( maxdist < child.getDistanceValue()){
                    maxdist = child.getDistanceValue();
                    next = child;
                }
            }
            children.remove(next);
            for( Cluster child : next.getChildren() ){
                children.add(child);
            }
            return getSolutionClusters(children);
        }
    }
    
    private void getDatapoints(){
        Integer i = 1;
        for( Cluster sol : solution ){
            getLeafs(sol,i);
            i++;
        }
        Collections.sort(datapoints, new Comparator<Integer[]>(){
            @Override
            public int compare(Integer[]  data1, Integer[]  data2)
            {
                return  data1[0].compareTo(data2[0]);
            }
        });
    }
    
    private void getLeafs(Cluster cl,Integer cl_number){
        if( cl.isLeaf() ){
            Integer[] data = new Integer[2];
            data[0] = Integer.parseInt(cl.getName());
            data[1] = cl_number;
            datapoints.add( data );
        }
        else{
            for( Cluster tmp : cl.getChildren() ){
                getLeafs(tmp,cl_number);
            }
        }
    }
    
    public void printToConsole(){
        for(Integer[] data : datapoints ){
                //System.out.println(data[0]+","+data[1]);
                System.out.println(data[1]);
            } 
    }
    
    public void createFile(String name){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(name+".txt", "UTF-8");
            for(Integer[] data : datapoints ){
                //writer.println(data[0]+","+data[1]);
                writer.println(data[1]);
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteLabel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WriteLabel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
}
