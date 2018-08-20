package csAsc.com.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

public class XMLParser {
	// private static Map xmlmap = new HashMap();
	// 存储xml元素信息的容器
	private static ArrayList<Node> elemList = new ArrayList<Node>();

	public static void main(String args[]) throws DocumentException {
		XMLParser parser = new XMLParser();
		String path = "place.xml";
		// 读取XML文件
		SAXReader reader = new SAXReader();
		Document doc = reader.read(path);
		// 获取XML根元素
		Element root = doc.getRootElement();
		parser.getElementList(root);
		StringBuffer x = parser.getListString(elemList);
		//System.out.println(root.attribute("id").getValue());
		System.out.println("-----------解析结果------------\n" + x);
	}
	
	public static StringBuffer getData(String filepath) throws DocumentException{
		XMLParser parser = new XMLParser();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(filepath);
		Element root = doc.getRootElement();
		parser.getElementList(root);
		StringBuffer x = parser.getListString(elemList);
		return x;
	}

	/**
	 * 获取节点所有属性值 <功能详细描述>
	 * 
	 * @param element
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String getNoteAttribute(Element element) {
		String xattribute = "[";
		//String[3] atts=
		DefaultAttribute e = null;
		List list = element.attributes();
		for (int i = 0; i < list.size(); i++) {
			e = (DefaultAttribute) list.get(i);
			if (i == list.size() - 1) {
				xattribute += "'"+e.getText()+"'";
			}else{
				xattribute += "'"+e.getText() + "',";
			}
			
		}
		xattribute += "]";
		return xattribute;
	}

	/**
	 * 递归遍历方法 <功能详细描述>
	 * 
	 * @param element
	 * @see [类、类#方法、类#成员]
	 */
	public void getElementList(Element element) {
		List elements = element.elements();
		// 没有子元素
		if (elements.isEmpty()) {
			String xpath = element.getPath();
			String value = element.getTextTrim();
			if(!element.attribute("id").getValue().equals("0")){
				elemList.add(new Node(getNoteAttribute(element), xpath, value));
			}
			
		} else {
			// 有子元素
			String xpath = element.getPath();
			String value = element.getTextTrim();
			if(!element.attribute("id").getValue().equals("0")){
				elemList.add(new Node(getNoteAttribute(element), xpath, value));
			}
			//elemList.add(new Leaf(getNoteAttribute(element), xpath, value));
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem);
			}
		}
	}

	public StringBuffer getListString(List elemList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Iterator it = elemList.iterator(); it.hasNext();) {
			Node node = (Node) it.next();
			//sb.append("xpath: " + leaf.getXpath()).append(", value: ").append(leaf.getValue());
			if (!"".equals(node.getXattribute())) {
				sb.append(node.getXattribute());
			}
			sb.append(",");
		}
		sb.append("]");
		return sb;
	}
}
