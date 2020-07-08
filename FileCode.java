import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.util.*;
import java.io.*;

public class FileCode {
	//this is used for if you wants to implement with TreeMap
	//sort tree map by its values in descending order
	/*public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
    Comparator<K> valueComparator = new Comparator<K>() {
      public int compare(K k1, K k2) {
        int compare = map.get(k2).compareTo(map.get(k1));
        if (compare == 0) 
          return 1;
        else 
          return compare;
      }
    };
 
    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
    sortedByValues.putAll(map);
    return sortedByValues;
  }*/
public static void main(String args[]) throws IOException
{
 boolean searchAgain=true;
 while(searchAgain) {
 try
 {
	 
	 String path="C:\\Users\\user\\eclipse-workspace\\FileSearch\\input_files\\";
	 File file=new File(path);
	 String filenames[]=file.list();//list of all file names in folder
	//for(String names:filenames)System.out.println(names); //to see all file names
	 
	 
	 //get the keyword
	 System.out.println("*****************Welcome to File Searching System*****************");
	 System.out.println("Please Enter The Keyword That You Want To Search In All Files:");
	 Scanner sc=new Scanner(System.in);
	 String keyword=sc.next();
	 int countKeyword;
	 
	 //making array of files which have keyword and array of keyword count
	 ArrayList<String> nameArr=new ArrayList<String>();
	 ArrayList<Integer> countArr=new ArrayList<Integer>();

	 //TreeMap<String, Integer> tree_map = new TreeMap<String, Integer>();


for(String docs:filenames)
 {
 countKeyword=0;
 //for the pdf files
 if(docs.endsWith(".pdf")) {
 PDDocument document = PDDocument.load(new File(path+docs));// here file1.pdf is the name of pdf file which we want to read....
 document.getClass();

 if (!document.isEncrypted())
 {
 PDFTextStripperByArea stripper =new PDFTextStripperByArea();
 stripper.setSortByPosition(true);
 PDFTextStripper Tstripper = new PDFTextStripper();
 String str =Tstripper.getText(document);
 Scanner scn = null;
 scn = new Scanner(str);
 String line="";
 while (scn.hasNextLine())
 {
	 line = scn.nextLine();
	 if(line.toLowerCase().contains(keyword.toLowerCase()))
		 countKeyword++;
	 
	 //System.out.println(line+" : yes");
	 //else System.out.println(line+" : no");
 }
 }
 if(countKeyword>0)
 {
 countArr.add(countKeyword);
 nameArr.add(docs);

 //tree_map.put(docs,countKeyword);
 }
 document.close();
 }
 else if(docs.endsWith(".txt"))//for .txt files
 {
 //System.out.println("**************\n\n\n\n got txt flie \n\n\n\n**************");
 try {
 File myObj = new File(path+docs);
 Scanner myReader = new Scanner(myObj);
 while (myReader.hasNextLine()) {
 String data =myReader.nextLine();

 if(data.toLowerCase().contains(keyword.toLowerCase()))
	 countKeyword++;
 //System.out.println(data+" : yes");
 //else System.out.println(data+" : no");
 }
if(countKeyword>0)
 {
 countArr.add(countKeyword);
 nameArr.add(docs);
 //tree_map.put(docs,countKeyword);
 }
 myReader.close();
 } 
 catch (FileNotFoundException e) {
 e.printStackTrace();
 }
 catch(IOException e1)
 {
 e1.printStackTrace();
 }
 }
else if(docs.endsWith(".docx"))
 {
 try
 {
 FileInputStream fis =new FileInputStream(path+docs);
 XWPFDocument docx=new XWPFDocument(fis);
 String linesPara="";
 List<XWPFParagraph>paragraphList=docx.getParagraphs();
 for(XWPFParagraph paragraph:paragraphList)
 {

 linesPara=paragraph.getText()+"";
 String[]allLines=linesPara.split("(?<=\\G.{"+110+"})");
 for(String lines: allLines)

if(lines.toLowerCase().contains(keyword.toLowerCase()))
	countKeyword++;
 
 //System.out.println(lines+" : yes");
 //else System.out.println(lines+" : no");
 }
if(countKeyword>0)
 {
 countArr.add(countKeyword);
 nameArr.add(docs);
 //tree_map.put(docs,countKeyword);
 }
 }
catch(FileNotFoundException e)
 {
 e.printStackTrace();
 }
 catch(IOException e1)
 {
 e1.printStackTrace();
 }
 }
 else if(docs.endsWith(".doc"))
 {
 try
{
 FileInputStream fis =new FileInputStream(path+docs);
 HWPFDocument doc =new HWPFDocument(fis);

 WordExtractor extractor =new WordExtractor(doc);
 String[]fileData=extractor.getParagraphText();

for(String lines: fileData)
 {
 String[]allLines=lines.split("(?<=\\G.{"+100+"})");
 for(String linesPara:allLines)
	 if(linesPara.toLowerCase().contains(keyword.toLowerCase()))
		 countKeyword++;
 
 //System.out.println(linesPara+" : yes");
 //else System.out.println(linesPara+" : no");
 }
if(countKeyword>0)
 {
 countArr.add(countKeyword);
 nameArr.add(docs);
 //tree_map.put(docs,countKeyword);
 }
 }
catch(FileNotFoundException e)
 {
 e.printStackTrace();
 }
catch(IOException e1)
 {
 e1.printStackTrace();
 }
 }
 }
//this the starting point of printing
 if(countArr.size()==0) {
	 System.out.println("Sorry We Didn't Found Any File who has the word "+keyword+".");
 	}
 else
{
for(int j=0;j<countArr.size()-1;j++)
 {
 for(int k=j+1;k<countArr.size();k++)
 {
	 String tempName="";
	 int tempInt;
	 if(countArr.get(j)<countArr.get(k))
	 	{
		 tempInt=countArr.get(j);
		 countArr.set(j,countArr.get(k));
		 countArr.set(k,tempInt);

		 tempName=nameArr.get(j);
		 nameArr.set(j, nameArr.get(k));
		 nameArr.set(k, tempName);
	 	}
 }
 }
 System.out.println("\n\nAfter Ordering:");
 System.out.println("---------------------------------");
 System.out.println("| file Name 	Priority Order");
 System.out.println("---------------------------------");

for(int q=0;q<countArr.size();q++)
 {
 System.out.printf("| %-20s %d |\n",nameArr.get(q),countArr.get(q));
 }

 System.out.println("---------------------------------");
 //this the ending point of printing
 /*
 //start point of printing tree map
 
//Calling the method sortByvalues
 
 Map sortedMap = sortByValues(tree_map);
 
 // Get a set of the entries on the sorted map
 Set set = sortedMap.entrySet();

 // Get an iterator
 Iterator i = set.iterator();

 // Display elements
 while(i.hasNext()) {
   Map.Entry me = (Map.Entry)i.next();
   System.out.printf("| %-20s %d |\n",me.getKey(),me.getValue());
   
 
	}
 
 //end point of printing tree map
 */
 boolean YesNo=true;
 while(YesNo) {
 System.out.println("\nDo You Want To Search More Words?\nPlease Type Yes or No");
 String moreWord=sc.next();
 if(moreWord.equalsIgnoreCase("yes"))
 {
 searchAgain=true;YesNo=false;
 }else if(moreWord.equalsIgnoreCase("no"))
 {
 System.out.println("Okay, See You Soon!");
 searchAgain=false;YesNo=false;
 }else
{
 System.out.println("Please Write Yes or No only!");
 YesNo=true;
 }}
}
 }
 catch (Exception e)
 {
 e.printStackTrace();
 }
 }


}
}