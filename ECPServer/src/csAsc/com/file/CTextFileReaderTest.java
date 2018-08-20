/**
 * 
 */
package csAsc.com.file;

import java.util.Iterator;
import java.util.List;

/**
 * @author Hogan
 *
 */
public class CTextFileReaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> res=CTextFileReader.readTxtFileToListString("E:\\资料\\运单号.txt");
		 for(Iterator<String> it2 = res.iterator();it2.hasNext();){
             System.out.println(it2.next());
        }
		
	}

}
