package test;
//package test;
//
//import java.io.IOException;
//
//import graph.NavGraph;
//
//public class Main {
//
//	public static void main(String[] args) throws IOException {
//		// Test
//		NavGraph myGraph = new NavGraph();
//		myGraph.initWithFile("data/Output.txt");
//		for(int i=0;i<myGraph.getxMax();i++) {
//			for(int j =0; j<myGraph.getyMax();j++) {
//				System.out.print(myGraph.getNod(i, j).getCategory());
//			}
//			System.out.println();
//		}
//	}
//
//}
import java.awt.*;  
import javax.swing.*;  
public class Gui9 extends JFrame{  
    //定义组件  
    JSplitPane jsp;  
    JList jlist;  
    JLabel jl;  
    //构造方法  
    public Gui9(){  
        //创建组件  
        String[] words = {"boy", "girl", "bird"};  
        jlist = new JList(words);  
        jl = new JLabel(new ImageIcon("images/image01.jpg"));  
        //拆分窗格  
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jlist, jl);  
        //可以变化  
        jsp.setOneTouchExpandable(true);  
          
        //添加组件  
        add(jsp);  
          
        //设置窗体  
        setTitle("分割");  
        setSize(400, 300);  
        setLocation(500, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setVisible(true);  
    }  
      
    public static void main(String[] args) {  
        Gui9 gui1 = new Gui9();  
    }  
} 