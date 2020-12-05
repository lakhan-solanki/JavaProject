import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.util.*;
import java.io.*;

class Files implements Runnable,Comparable<Files>{
	int keyFrequency;//keyword frequency
	String file_name;
	String file_type;
	int file_size;
	Files(int kf,String fn,String ft){
		this.keyFrequency=kf;
		file_name=fn;
		file_type=ft;
	}
	public Files() {
	}
	
	@Override
	public int compareTo(Files o) {
		int res=o.keyFrequency-this.keyFrequency;
		if(res==0)
			res=this.file_name.compareTo(o.file_name);
		return res;
	}
	@Override
	public void run() {
		
		
	}
	
}
public class FileCode {
	
	static void printAllFiles(ArrayList<Files> ff)
	{
		 System.out.println("---------------------------------");
		 System.out.println("| file Name 	Priority Order  |");
		 System.out.println("---------------------------------");
		for(Files tempfile: ff)
		{
			System.out.printf("| %-20s %5d    |\n",tempfile.file_name,tempfile.keyFrequency);
		}
		System.out.println("---------------------------------");
	}
	public static int pdfFile(String keyword,String path,String docs) throws IOException
	{
		int countKeyword=0;
		PDDocument document = PDDocument.load(new File(path+docs));// here file1.pdf is the name of pdf file which we want to read....
		 document.getClass();
	
		 if (! document.isEncrypted())
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
			 }
		 }
		 document.close();
		 return countKeyword;
	}
	public static int txtFile(String keyword,String path,String docs) throws IOException
	{
		int countKeyword=0;
		File myObj = new File(path+docs);
		 Scanner myReader = new Scanner(myObj);
		 while (myReader.hasNextLine()) {
		 String data =myReader.nextLine();

		 if(data.toLowerCase().contains(keyword.toLowerCase()))
			 countKeyword++;
		 }
		 myReader.close();
		 return countKeyword;
	}
	
	public static int docFile(String keyword,String path,String docs) throws IOException
	{
		int countKeyword=0;
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
		 }
		return countKeyword;
	}
	public static int docxFile(String keyword,String path,String docs) throws IOException
	{
		int countKeyword=0;
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
		 }
		return countKeyword;
	}
	public static void main(String[] args)throws IOException {
		
		boolean searchAgain=true;
		Scanner sc=new Scanner(System.in);
		String path="C:\\Users\\user\\eclipse-workspace\\FileSearch\\input_files\\";
		File file=new File(path);
		String filenames[]=file.list();//list of all file names in folder
		System.out.println("*****************Welcome to File Searching System*****************");
		while(searchAgain)
		{
			try {
				
				 System.out.println("Please Enter The Keyword That You Want To Search In All Files:");
				 
				 String keyword=sc.next();
				 int countKeyword;
				 ArrayList<Files> fileCont=new ArrayList<>();
				 //Files ff[]=new Files[TotalFiles];
				 
				 for(String docs:filenames)
				 {
					 if(docs.endsWith(".pdf"))
					 {
				 			countKeyword=pdfFile(keyword,path,docs);
				 			if(countKeyword>0)
				 				fileCont.add(new Files(countKeyword,docs,"pdf"));
					 }
						
					 else if(docs.endsWith(".txt"))
					 {
				 			countKeyword=txtFile(keyword,path,docs);
				 			if(countKeyword>0)
				 				fileCont.add(new Files(countKeyword,docs,"txt"));
					 }else if(docs.endsWith(".doc"))
					 {
				 			countKeyword=docFile(keyword,path,docs);
				 			if(countKeyword>0)
				 				fileCont.add(new Files(countKeyword,docs,"doc"));
					 }else if(docs.endsWith(".docx"))
					 {
				 			countKeyword=docxFile(keyword,path,docs);
				 			if(countKeyword>0)
				 				fileCont.add(new Files(countKeyword,docs,"docx"));
					 }
					
				 }
				 
				 Collections.sort(fileCont);
				 if(fileCont.isEmpty())
					 System.out.println("Sorry we didn't find any file containing - '"+keyword+"'");
				 else
				 printAllFiles(fileCont);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				
				System.out.println("Do you wants to search Another word?\nType Yes or No");
				boolean yesno=true;
				while(yesno) 
				{
					String again=sc.next();
					if(again.equalsIgnoreCase("no"))
						{searchAgain=false;yesno=false;}
					else if(again.equalsIgnoreCase("yes"))
						{searchAgain=true;yesno=false;}
					else 
						System.out.println("Please type only yes or no");	 
						
				}
				
			}
			
		}
		System.out.println("Thankyou - Visit again !!");

	}

}
